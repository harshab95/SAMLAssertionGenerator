// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.opensaml.xml.util.DatatypeHelper;
import java.security.cert.X509Certificate;
import org.opensaml.xml.security.SecurityException;
import java.util.HashSet;
import org.slf4j.LoggerFactory;
import java.util.Set;
import org.slf4j.Logger;

public class BasicX509CredentialNameEvaluator implements X509CredentialNameEvaluator
{
    private final Logger log;
    private boolean checkSubjectAltNames;
    private boolean checkSubjectDNCommonName;
    private boolean checkSubjectDN;
    private Set<Integer> subjectAltNameTypes;
    private X500DNHandler x500DNHandler;
    
    public BasicX509CredentialNameEvaluator() {
        this.log = LoggerFactory.getLogger((Class)BasicX509CredentialNameEvaluator.class);
        this.x500DNHandler = new InternalX500DNHandler();
        this.subjectAltNameTypes = new HashSet<Integer>(5);
        this.setCheckSubjectAltNames(true);
        this.setCheckSubjectDNCommonName(true);
        this.setCheckSubjectDN(true);
        this.subjectAltNameTypes.add(X509Util.DNS_ALT_NAME);
        this.subjectAltNameTypes.add(X509Util.URI_ALT_NAME);
    }
    
    public boolean isNameCheckingActive() {
        return this.checkSubjectAltNames() || this.checkSubjectDNCommonName() || this.checkSubjectDN();
    }
    
    public Set<Integer> getSubjectAltNameTypes() {
        return this.subjectAltNameTypes;
    }
    
    public boolean checkSubjectAltNames() {
        return this.checkSubjectAltNames;
    }
    
    public void setCheckSubjectAltNames(final boolean check) {
        this.checkSubjectAltNames = check;
    }
    
    public boolean checkSubjectDNCommonName() {
        return this.checkSubjectDNCommonName;
    }
    
    public void setCheckSubjectDNCommonName(final boolean check) {
        this.checkSubjectDNCommonName = check;
    }
    
    public boolean checkSubjectDN() {
        return this.checkSubjectDN;
    }
    
    public void setCheckSubjectDN(final boolean check) {
        this.checkSubjectDN = check;
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
    
    public boolean evaluate(final X509Credential credential, final Set<String> trustedNames) throws SecurityException {
        if (!this.isNameCheckingActive()) {
            this.log.debug("No trusted name options are active, skipping name evaluation");
            return true;
        }
        if (trustedNames == null || trustedNames.isEmpty()) {
            this.log.debug("Supplied trusted names are null or empty, skipping name evaluation");
            return true;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking trusted names against credential: {}", (Object)X509Util.getIdentifiersToken(credential, this.x500DNHandler));
            this.log.debug("Trusted names being evaluated are: {}", (Object)trustedNames.toString());
        }
        return this.processNameChecks(credential, trustedNames);
    }
    
    protected boolean processNameChecks(final X509Credential credential, final Set<String> trustedNames) {
        final X509Certificate entityCertificate = credential.getEntityCertificate();
        if (this.checkSubjectAltNames() && this.processSubjectAltNames(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject alt names.", (Object)X509Util.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        if (this.checkSubjectDNCommonName() && this.processSubjectDNCommonName(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject common name.", (Object)X509Util.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        if (this.checkSubjectDN() && this.processSubjectDN(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject DN.", (Object)X509Util.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        this.log.error("Credential failed name check: " + X509Util.getIdentifiersToken(credential, this.x500DNHandler));
        return false;
    }
    
    protected boolean processSubjectDNCommonName(final X509Certificate certificate, final Set<String> trustedNames) {
        this.log.debug("Processing subject DN common name");
        final X500Principal subjectPrincipal = certificate.getSubjectX500Principal();
        final List<String> commonNames = X509Util.getCommonNames(subjectPrincipal);
        if (commonNames == null || commonNames.isEmpty()) {
            return false;
        }
        final String commonName = commonNames.get(0);
        this.log.debug("Extracted common name from certificate: {}", (Object)commonName);
        if (DatatypeHelper.isEmpty(commonName)) {
            return false;
        }
        if (trustedNames.contains(commonName)) {
            this.log.debug("Matched subject DN common name to trusted names: {}", (Object)commonName);
            return true;
        }
        return false;
    }
    
    protected boolean processSubjectDN(final X509Certificate certificate, final Set<String> trustedNames) {
        this.log.debug("Processing subject DN");
        final X500Principal subjectPrincipal = certificate.getSubjectX500Principal();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Extracted X500Principal from certificate: {}", (Object)this.x500DNHandler.getName(subjectPrincipal));
        }
        for (final String trustedName : trustedNames) {
            X500Principal trustedNamePrincipal = null;
            try {
                trustedNamePrincipal = this.x500DNHandler.parse(trustedName);
                this.log.debug("Evaluating principal successfully parsed from trusted name: {}", (Object)trustedName);
                if (subjectPrincipal.equals(trustedNamePrincipal)) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Matched subject DN to trusted names: {}", (Object)this.x500DNHandler.getName(subjectPrincipal));
                    }
                    return true;
                }
                continue;
            }
            catch (IllegalArgumentException e) {
                this.log.debug("Trusted name was not a DN or could not be parsed: {}", (Object)trustedName);
            }
        }
        return false;
    }
    
    protected boolean processSubjectAltNames(final X509Certificate certificate, final Set<String> trustedNames) {
        this.log.debug("Processing subject alt names");
        final Integer[] nameTypes = new Integer[this.subjectAltNameTypes.size()];
        this.subjectAltNameTypes.toArray(nameTypes);
        final List altNames = X509Util.getAltNames(certificate, nameTypes);
        this.log.debug("Extracted subject alt names from certificate: {}", (Object)altNames);
        for (final Object altName : altNames) {
            if (trustedNames.contains(altName)) {
                this.log.debug("Matched subject alt name to trusted names: {}", (Object)altName.toString());
                return true;
            }
        }
        return false;
    }
}
