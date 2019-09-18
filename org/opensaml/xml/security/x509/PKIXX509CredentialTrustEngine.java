// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.util.Iterator;
import org.opensaml.xml.security.SecurityException;
import java.util.Set;
import org.opensaml.xml.security.CriteriaSet;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class PKIXX509CredentialTrustEngine implements PKIXTrustEngine<X509Credential>
{
    private final Logger log;
    private PKIXValidationInformationResolver pkixResolver;
    private PKIXTrustEvaluator pkixTrustEvaluator;
    private X509CredentialNameEvaluator credNameEvaluator;
    
    public PKIXX509CredentialTrustEngine(final PKIXValidationInformationResolver resolver) {
        this.log = LoggerFactory.getLogger((Class)PKIXX509CredentialTrustEngine.class);
        if (resolver == null) {
            throw new IllegalArgumentException("PKIX trust information resolver may not be null");
        }
        this.pkixResolver = resolver;
        this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
        this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
    }
    
    public PKIXX509CredentialTrustEngine(final PKIXValidationInformationResolver resolver, final PKIXTrustEvaluator pkixEvaluator, final X509CredentialNameEvaluator nameEvaluator) {
        this.log = LoggerFactory.getLogger((Class)PKIXX509CredentialTrustEngine.class);
        if (resolver == null) {
            throw new IllegalArgumentException("PKIX trust information resolver may not be null");
        }
        this.pkixResolver = resolver;
        if (pkixEvaluator == null) {
            throw new IllegalArgumentException("PKIX trust evaluator may not be null");
        }
        this.pkixTrustEvaluator = pkixEvaluator;
        this.credNameEvaluator = nameEvaluator;
    }
    
    public PKIXValidationInformationResolver getPKIXResolver() {
        return this.pkixResolver;
    }
    
    public PKIXTrustEvaluator getPKIXTrustEvaluator() {
        return this.pkixTrustEvaluator;
    }
    
    public X509CredentialNameEvaluator getX509CredentialNameEvaluator() {
        return this.credNameEvaluator;
    }
    
    public boolean validate(final X509Credential untrustedCredential, final CriteriaSet trustBasisCriteria) throws SecurityException {
        this.log.debug("Attempting PKIX validation of untrusted credential");
        if (untrustedCredential == null) {
            this.log.error("X.509 credential was null, unable to perform validation");
            return false;
        }
        if (untrustedCredential.getEntityCertificate() == null) {
            this.log.error("Untrusted X.509 credential's entity certificate was null, unable to perform validation");
            return false;
        }
        Set<String> trustedNames = null;
        if (this.pkixResolver.supportsTrustedNameResolution()) {
            trustedNames = this.pkixResolver.resolveTrustedNames(trustBasisCriteria);
        }
        else {
            this.log.debug("PKIX resolver does not support resolution of trusted names, skipping name checking");
        }
        return this.validate(untrustedCredential, trustedNames, this.pkixResolver.resolve(trustBasisCriteria));
    }
    
    protected boolean validate(final X509Credential untrustedX509Credential, final Set<String> trustedNames, final Iterable<PKIXValidationInformation> validationInfoSet) throws SecurityException {
        this.log.debug("Beginning PKIX validation using trusted validation information");
        if (!this.checkNames(trustedNames, untrustedX509Credential)) {
            this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
            return false;
        }
        for (final PKIXValidationInformation validationInfo : validationInfoSet) {
            try {
                if (this.pkixTrustEvaluator.validate(validationInfo, untrustedX509Credential)) {
                    this.log.debug("Credential trust established via PKIX validation");
                    return true;
                }
                continue;
            }
            catch (SecurityException e) {
                this.log.debug("Error performing PKIX validation on untrusted credential", (Throwable)e);
            }
        }
        this.log.debug("Trust of untrusted credential could not be established via PKIX validation");
        return false;
    }
    
    protected boolean checkNames(final Set<String> trustedNames, final X509Credential untrustedCredential) throws SecurityException {
        if (this.credNameEvaluator == null) {
            this.log.debug("No credential name evaluator was available, skipping trusted name evaluation");
            return true;
        }
        return this.credNameEvaluator.evaluate(untrustedCredential, trustedNames);
    }
}
