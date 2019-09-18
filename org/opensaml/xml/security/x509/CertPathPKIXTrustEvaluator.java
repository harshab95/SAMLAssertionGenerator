// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.util.List;
import java.security.cert.CertStoreParameters;
import java.security.cert.CollectionCertStoreParameters;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.security.cert.CRL;
import java.util.Collection;
import java.security.cert.CertStoreException;
import java.security.cert.CRLSelector;
import java.security.cert.CertStore;
import java.security.cert.TrustAnchor;
import java.util.Set;
import java.security.cert.CertSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.PKIXBuilderParameters;
import java.security.GeneralSecurityException;
import org.opensaml.xml.security.SecurityException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.CertPathBuilder;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CertPathPKIXTrustEvaluator implements PKIXTrustEvaluator
{
    private final Logger log;
    private X500DNHandler x500DNHandler;
    private PKIXValidationOptions options;
    
    public CertPathPKIXTrustEvaluator() {
        this.log = LoggerFactory.getLogger((Class)CertPathPKIXTrustEvaluator.class);
        this.options = new PKIXValidationOptions();
        this.x500DNHandler = new InternalX500DNHandler();
    }
    
    public CertPathPKIXTrustEvaluator(final PKIXValidationOptions newOptions) {
        this.log = LoggerFactory.getLogger((Class)CertPathPKIXTrustEvaluator.class);
        if (newOptions == null) {
            throw new IllegalArgumentException("PKIXValidationOptions may not be null");
        }
        this.options = newOptions;
        this.x500DNHandler = new InternalX500DNHandler();
    }
    
    public PKIXValidationOptions getPKIXValidationOptions() {
        return this.options;
    }
    
    public void setPKIXValidationOptions(final PKIXValidationOptions newOptions) {
        if (newOptions == null) {
            throw new IllegalArgumentException("PKIXValidationOptions may not be null");
        }
        this.options = newOptions;
    }
    
    public X500DNHandler getX500DNHandler() {
        return this.x500DNHandler;
    }
    
    public void setX500DNHandler(final X500DNHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("X500DNHandler may not be null");
        }
        this.x500DNHandler = handler;
    }
    
    public boolean validate(final PKIXValidationInformation validationInfo, final X509Credential untrustedCredential) throws SecurityException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Attempting PKIX path validation on untrusted credential: {}", (Object)X509Util.getIdentifiersToken(untrustedCredential, this.x500DNHandler));
        }
        try {
            final PKIXBuilderParameters params = this.getPKIXBuilderParameters(validationInfo, untrustedCredential);
            this.log.trace("Building certificate validation path");
            final CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");
            final PKIXCertPathBuilderResult buildResult = (PKIXCertPathBuilderResult)builder.build(params);
            if (this.log.isDebugEnabled()) {
                this.logCertPathDebug(buildResult, untrustedCredential.getEntityCertificate());
                this.log.debug("PKIX validation succeeded for untrusted credential: {}", (Object)X509Util.getIdentifiersToken(untrustedCredential, this.x500DNHandler));
            }
            return true;
        }
        catch (CertPathBuilderException e) {
            if (this.log.isTraceEnabled()) {
                this.log.trace("PKIX path construction failed for untrusted credential: " + X509Util.getIdentifiersToken(untrustedCredential, this.x500DNHandler), (Throwable)e);
            }
            else {
                this.log.error("PKIX path construction failed for untrusted credential: " + X509Util.getIdentifiersToken(untrustedCredential, this.x500DNHandler) + ": " + e.getMessage());
            }
            return false;
        }
        catch (GeneralSecurityException e2) {
            this.log.error("PKIX validation failure", (Throwable)e2);
            throw new SecurityException("PKIX validation failure", e2);
        }
    }
    
    protected PKIXBuilderParameters getPKIXBuilderParameters(final PKIXValidationInformation validationInfo, final X509Credential untrustedCredential) throws GeneralSecurityException {
        final Set<TrustAnchor> trustAnchors = this.getTrustAnchors(validationInfo);
        if (trustAnchors == null || trustAnchors.isEmpty()) {
            throw new GeneralSecurityException("Unable to validate X509 certificate, no trust anchors found in the PKIX validation information");
        }
        final X509CertSelector selector = new X509CertSelector();
        selector.setCertificate(untrustedCredential.getEntityCertificate());
        this.log.trace("Adding trust anchors to PKIX validator parameters");
        final PKIXBuilderParameters params = new PKIXBuilderParameters(trustAnchors, selector);
        final Integer effectiveVerifyDepth = this.getEffectiveVerificationDepth(validationInfo);
        this.log.trace("Setting max verification depth to: {} ", (Object)effectiveVerifyDepth);
        params.setMaxPathLength(effectiveVerifyDepth);
        final CertStore certStore = this.buildCertStore(validationInfo, untrustedCredential);
        params.addCertStore(certStore);
        boolean isForceRevocationEnabled = false;
        boolean forcedRevocation = false;
        if (this.options instanceof CertPathPKIXValidationOptions) {
            final CertPathPKIXValidationOptions certpathOptions = (CertPathPKIXValidationOptions)this.options;
            isForceRevocationEnabled = certpathOptions.isForceRevocationEnabled();
            forcedRevocation = certpathOptions.isRevocationEnabled();
        }
        if (isForceRevocationEnabled) {
            this.log.trace("PKIXBuilderParameters#setRevocationEnabled is being forced to: {}", (Object)forcedRevocation);
            params.setRevocationEnabled(forcedRevocation);
        }
        else if (this.storeContainsCRLs(certStore)) {
            this.log.trace("At least one CRL was present in cert store, enabling revocation checking");
            params.setRevocationEnabled(true);
        }
        else {
            this.log.trace("No CRLs present in cert store, disabling revocation checking");
            params.setRevocationEnabled(false);
        }
        return params;
    }
    
    protected boolean storeContainsCRLs(final CertStore certStore) {
        Collection<? extends CRL> crls = null;
        try {
            crls = certStore.getCRLs(null);
        }
        catch (CertStoreException e) {
            this.log.error("Error examining cert store for CRL's, treating as if no CRL's present", (Throwable)e);
            return false;
        }
        return crls != null && !crls.isEmpty();
    }
    
    protected Integer getEffectiveVerificationDepth(final PKIXValidationInformation validationInfo) {
        Integer effectiveVerifyDepth = validationInfo.getVerificationDepth();
        if (effectiveVerifyDepth == null) {
            effectiveVerifyDepth = this.options.getDefaultVerificationDepth();
        }
        return effectiveVerifyDepth;
    }
    
    protected Set<TrustAnchor> getTrustAnchors(final PKIXValidationInformation validationInfo) {
        final Collection<X509Certificate> validationCertificates = validationInfo.getCertificates();
        this.log.trace("Constructing trust anchors for PKIX validation");
        final Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
        for (final X509Certificate cert : validationCertificates) {
            trustAnchors.add(this.buildTrustAnchor(cert));
        }
        if (this.log.isTraceEnabled()) {
            for (final TrustAnchor anchor : trustAnchors) {
                this.log.trace("TrustAnchor: {}", (Object)anchor.toString());
            }
        }
        return trustAnchors;
    }
    
    protected TrustAnchor buildTrustAnchor(final X509Certificate cert) {
        return new TrustAnchor(cert, null);
    }
    
    protected CertStore buildCertStore(final PKIXValidationInformation validationInfo, final X509Credential untrustedCredential) throws GeneralSecurityException {
        this.log.trace("Creating cert store to use during path validation");
        this.log.trace("Adding entity certificate chain to cert store");
        final List<Object> storeMaterial = new ArrayList<Object>(untrustedCredential.getEntityCertificateChain());
        if (this.log.isTraceEnabled()) {
            for (final X509Certificate cert : untrustedCredential.getEntityCertificateChain()) {
                this.log.trace(String.format("Added X509Certificate from entity cert chain to cert store with subject name '%s' issued by '%s' with serial number '%s'", this.x500DNHandler.getName(cert.getSubjectX500Principal()), this.x500DNHandler.getName(cert.getIssuerX500Principal()), cert.getSerialNumber().toString()));
            }
        }
        final Date now = new Date();
        if (validationInfo.getCRLs() != null && !validationInfo.getCRLs().isEmpty()) {
            this.log.trace("Processing CRL's from PKIX info set");
            this.addCRLsToStoreMaterial(storeMaterial, validationInfo.getCRLs(), now);
        }
        if (untrustedCredential.getCRLs() != null && !untrustedCredential.getCRLs().isEmpty() && this.options.isProcessCredentialCRLs()) {
            this.log.trace("Processing CRL's from untrusted credential");
            this.addCRLsToStoreMaterial(storeMaterial, untrustedCredential.getCRLs(), now);
        }
        return CertStore.getInstance("Collection", new CollectionCertStoreParameters(storeMaterial));
    }
    
    protected void addCRLsToStoreMaterial(final List<Object> storeMaterial, final Collection<X509CRL> crls, final Date now) {
        for (final X509CRL crl : crls) {
            final boolean isEmpty = crl.getRevokedCertificates() == null || crl.getRevokedCertificates().isEmpty();
            final boolean isExpired = crl.getNextUpdate().before(now);
            if (!isEmpty || this.options.isProcessEmptyCRLs()) {
                if (!isExpired || this.options.isProcessExpiredCRLs()) {
                    storeMaterial.add(crl);
                    if (this.log.isTraceEnabled()) {
                        this.log.trace("Added X509CRL to cert store from issuer {} dated {}", (Object)this.x500DNHandler.getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
                        if (isEmpty) {
                            this.log.trace("X509CRL added to cert store from issuer {} dated {} was empty", (Object)this.x500DNHandler.getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
                        }
                    }
                    if (!isExpired) {
                        continue;
                    }
                    this.log.warn("Using X509CRL from issuer {} with a nextUpdate in the past: {}", (Object)this.x500DNHandler.getName(crl.getIssuerX500Principal()), (Object)crl.getNextUpdate());
                }
                else {
                    if (!this.log.isTraceEnabled()) {
                        continue;
                    }
                    this.log.trace("Expired X509CRL not added to cert store, from issuer {} nextUpdate {}", (Object)this.x500DNHandler.getName(crl.getIssuerX500Principal()), (Object)crl.getNextUpdate());
                }
            }
            else {
                if (!this.log.isTraceEnabled()) {
                    continue;
                }
                this.log.trace("Empty X509CRL not added to cert store, from issuer {} dated {}", (Object)this.x500DNHandler.getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
            }
        }
    }
    
    private void logCertPathDebug(final PKIXCertPathBuilderResult buildResult, final X509Certificate targetCert) {
        this.log.debug("Built valid PKIX cert path");
        this.log.debug("Target certificate: {}", (Object)this.x500DNHandler.getName(targetCert.getSubjectX500Principal()));
        for (final Certificate cert : buildResult.getCertPath().getCertificates()) {
            this.log.debug("CertPath certificate: {}", (Object)this.x500DNHandler.getName(((X509Certificate)cert).getSubjectX500Principal()));
        }
        final TrustAnchor ta = buildResult.getTrustAnchor();
        if (ta.getTrustedCert() != null) {
            this.log.debug("TrustAnchor: {}", (Object)this.x500DNHandler.getName(ta.getTrustedCert().getSubjectX500Principal()));
        }
        else if (ta.getCA() != null) {
            this.log.debug("TrustAnchor: {}", (Object)this.x500DNHandler.getName(ta.getCA()));
        }
        else {
            this.log.debug("TrustAnchor: {}", (Object)ta.getCAName());
        }
    }
}
