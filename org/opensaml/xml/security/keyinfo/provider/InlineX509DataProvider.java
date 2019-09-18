// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo.provider;

import java.util.Arrays;
import org.opensaml.xml.security.x509.X509Util;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.signature.X509SKI;
import java.math.BigInteger;
import org.opensaml.xml.signature.X509IssuerSerial;
import javax.security.auth.x500.X500Principal;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.signature.X509SubjectName;
import java.util.Iterator;
import java.security.cert.CertificateException;
import java.security.cert.CRLException;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.CredentialContext;
import java.util.List;
import org.opensaml.xml.util.LazySet;
import java.security.cert.X509Certificate;
import java.security.cert.X509CRL;
import org.opensaml.xml.security.x509.BasicX509Credential;
import java.security.PublicKey;
import org.opensaml.xml.security.credential.Credential;
import java.util.Collection;
import org.opensaml.xml.security.keyinfo.KeyInfoResolutionContext;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.x509.InternalX500DNHandler;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.x509.X500DNHandler;
import org.slf4j.Logger;

public class InlineX509DataProvider extends AbstractKeyInfoProvider
{
    private final Logger log;
    private X500DNHandler x500DNHandler;
    
    public InlineX509DataProvider() {
        this.log = LoggerFactory.getLogger((Class)InlineX509DataProvider.class);
        this.x500DNHandler = new InternalX500DNHandler();
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
    
    public boolean handles(final XMLObject keyInfoChild) {
        return keyInfoChild instanceof X509Data;
    }
    
    public Collection<Credential> process(final KeyInfoCredentialResolver resolver, final XMLObject keyInfoChild, final CriteriaSet criteriaSet, final KeyInfoResolutionContext kiContext) throws SecurityException {
        if (!this.handles(keyInfoChild)) {
            return null;
        }
        final X509Data x509Data = (X509Data)keyInfoChild;
        this.log.debug("Attempting to extract credential from an X509Data");
        final List<X509Certificate> certs = this.extractCertificates(x509Data);
        if (certs.isEmpty()) {
            this.log.info("The X509Data contained no X509Certificate elements, skipping credential extraction");
            return null;
        }
        final List<X509CRL> crls = this.extractCRLs(x509Data);
        PublicKey resolvedPublicKey = null;
        if (kiContext != null && kiContext.getKey() != null && kiContext.getKey() instanceof PublicKey) {
            resolvedPublicKey = (PublicKey)kiContext.getKey();
        }
        final X509Certificate entityCert = this.findEntityCert(certs, x509Data, resolvedPublicKey);
        if (entityCert == null) {
            this.log.warn("The end-entity cert could not be identified, skipping credential extraction");
            return null;
        }
        final BasicX509Credential cred = new BasicX509Credential();
        cred.setEntityCertificate(entityCert);
        cred.setCRLs(crls);
        cred.setEntityCertificateChain(certs);
        if (kiContext != null) {
            cred.getKeyNames().addAll(kiContext.getKeyNames());
        }
        final CredentialContext credContext = this.buildCredentialContext(kiContext);
        if (credContext != null) {
            cred.getCredentalContextSet().add(credContext);
        }
        final LazySet<Credential> credentialSet = new LazySet<Credential>();
        credentialSet.add(cred);
        return credentialSet;
    }
    
    private List<X509CRL> extractCRLs(final X509Data x509Data) throws SecurityException {
        List<X509CRL> crls = null;
        try {
            crls = KeyInfoHelper.getCRLs(x509Data);
        }
        catch (CRLException e) {
            this.log.error("Error extracting CRL's from X509Data", (Throwable)e);
            throw new SecurityException("Error extracting CRL's from X509Data", e);
        }
        this.log.debug("Found {} X509CRLs", (Object)crls.size());
        return crls;
    }
    
    private List<X509Certificate> extractCertificates(final X509Data x509Data) throws SecurityException {
        List<X509Certificate> certs = null;
        try {
            certs = KeyInfoHelper.getCertificates(x509Data);
        }
        catch (CertificateException e) {
            this.log.error("Error extracting certificates from X509Data", (Throwable)e);
            throw new SecurityException("Error extracting certificates from X509Data", e);
        }
        this.log.debug("Found {} X509Certificates", (Object)certs.size());
        return certs;
    }
    
    protected X509Certificate findEntityCert(final List<X509Certificate> certs, final X509Data x509Data, final PublicKey resolvedKey) {
        if (certs == null || certs.isEmpty()) {
            return null;
        }
        if (certs.size() == 1) {
            this.log.debug("Single certificate was present, treating as end-entity certificate");
            return certs.get(0);
        }
        X509Certificate cert = null;
        cert = this.findCertFromKey(certs, resolvedKey);
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching previously resolved public key");
            return cert;
        }
        cert = this.findCertFromSubjectNames(certs, x509Data.getX509SubjectNames());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509SubjectName");
            return cert;
        }
        cert = this.findCertFromIssuerSerials(certs, x509Data.getX509IssuerSerials());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509IssuerSerial");
            return cert;
        }
        cert = this.findCertFromSubjectKeyIdentifier(certs, x509Data.getX509SKIs());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509SKI");
            return cert;
        }
        this.log.debug("Treating the first certificate in the X509Data as the end-entity certificate");
        return certs.get(0);
    }
    
    protected X509Certificate findCertFromKey(final List<X509Certificate> certs, final PublicKey key) {
        if (key != null) {
            for (final X509Certificate cert : certs) {
                if (cert.getPublicKey().equals(key)) {
                    return cert;
                }
            }
        }
        return null;
    }
    
    protected X509Certificate findCertFromSubjectNames(final List<X509Certificate> certs, final List<X509SubjectName> names) {
        for (final X509SubjectName subjectName : names) {
            if (!DatatypeHelper.isEmpty(subjectName.getValue())) {
                final X500Principal subjectX500Principal = this.x500DNHandler.parse(subjectName.getValue());
                for (final X509Certificate cert : certs) {
                    if (cert.getSubjectX500Principal().equals(subjectX500Principal)) {
                        return cert;
                    }
                }
            }
        }
        return null;
    }
    
    protected X509Certificate findCertFromIssuerSerials(final List<X509Certificate> certs, final List<X509IssuerSerial> serials) {
        for (final X509IssuerSerial issuerSerial : serials) {
            if (issuerSerial.getX509IssuerName() != null) {
                if (issuerSerial.getX509SerialNumber() == null) {
                    continue;
                }
                final String issuerNameValue = issuerSerial.getX509IssuerName().getValue();
                final BigInteger serialNumber = issuerSerial.getX509SerialNumber().getValue();
                if (DatatypeHelper.isEmpty(issuerNameValue)) {
                    continue;
                }
                final X500Principal issuerX500Principal = this.x500DNHandler.parse(issuerNameValue);
                for (final X509Certificate cert : certs) {
                    if (cert.getIssuerX500Principal().equals(issuerX500Principal) && cert.getSerialNumber().equals(serialNumber)) {
                        return cert;
                    }
                }
            }
        }
        return null;
    }
    
    protected X509Certificate findCertFromSubjectKeyIdentifier(final List<X509Certificate> certs, final List<X509SKI> skis) {
        for (final X509SKI ski : skis) {
            if (!DatatypeHelper.isEmpty(ski.getValue())) {
                final byte[] xmlValue = Base64.decode(ski.getValue());
                for (final X509Certificate cert : certs) {
                    final byte[] certValue = X509Util.getSubjectKeyIdentifier(cert);
                    if (certValue != null && Arrays.equals(xmlValue, certValue)) {
                        return cert;
                    }
                }
            }
        }
        return null;
    }
}
