// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Iterator;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.SigningUtil;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.x509.BasicX509CredentialNameEvaluator;
import org.opensaml.xml.security.x509.CertPathPKIXTrustEvaluator;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.X509CredentialNameEvaluator;
import org.opensaml.xml.security.x509.PKIXTrustEvaluator;
import org.opensaml.xml.security.x509.PKIXValidationInformationResolver;
import org.slf4j.Logger;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.security.x509.PKIXTrustEngine;
import org.opensaml.xml.security.x509.PKIXValidationInformation;
import java.util.Set;
import org.opensaml.xml.util.Pair;

public class PKIXSignatureTrustEngine extends BaseSignatureTrustEngine<Pair<Set<String>, Iterable<PKIXValidationInformation>>> implements PKIXTrustEngine<Signature>
{
    private final Logger log;
    private PKIXValidationInformationResolver pkixResolver;
    private PKIXTrustEvaluator pkixTrustEvaluator;
    private X509CredentialNameEvaluator credNameEvaluator;
    
    public PKIXSignatureTrustEngine(final PKIXValidationInformationResolver resolver, final KeyInfoCredentialResolver keyInfoResolver) {
        super(keyInfoResolver);
        this.log = LoggerFactory.getLogger((Class)PKIXSignatureTrustEngine.class);
        if (resolver == null) {
            throw new IllegalArgumentException("PKIX trust information resolver may not be null");
        }
        this.pkixResolver = resolver;
        this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
        this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
    }
    
    public PKIXSignatureTrustEngine(final PKIXValidationInformationResolver resolver, final KeyInfoCredentialResolver keyInfoResolver, final PKIXTrustEvaluator pkixEvaluator, final X509CredentialNameEvaluator nameEvaluator) {
        super(keyInfoResolver);
        this.log = LoggerFactory.getLogger((Class)PKIXSignatureTrustEngine.class);
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
    
    public PKIXTrustEvaluator getPKIXTrustEvaluator() {
        return this.pkixTrustEvaluator;
    }
    
    public X509CredentialNameEvaluator getX509CredentialNameEvaluator() {
        return this.credNameEvaluator;
    }
    
    public PKIXValidationInformationResolver getPKIXResolver() {
        return this.pkixResolver;
    }
    
    public boolean validate(final Signature signature, final CriteriaSet trustBasisCriteria) throws SecurityException {
        this.checkParams(signature, trustBasisCriteria);
        final Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair = this.resolveValidationInfo(trustBasisCriteria);
        if (this.validate(signature, validationPair)) {
            return true;
        }
        this.log.debug("PKIX validation of signature failed, unable to resolve valid and trusted signing key");
        return false;
    }
    
    public boolean validate(final byte[] signature, final byte[] content, final String algorithmURI, final CriteriaSet trustBasisCriteria, final Credential candidateCredential) throws SecurityException {
        if (candidateCredential == null || SecurityHelper.extractVerificationKey(candidateCredential) == null) {
            this.log.debug("Candidate credential was either not supplied or did not contain verification key");
            this.log.debug("PKIX trust engine requires supplied key, skipping PKIX trust evaluation");
            return false;
        }
        this.checkParamsRaw(signature, content, algorithmURI, trustBasisCriteria);
        final Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair = this.resolveValidationInfo(trustBasisCriteria);
        if (SigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
            this.log.debug("Successfully verified raw signature using supplied candidate credential");
            this.log.debug("Attempting to establish trust of supplied candidate credential");
            if (this.evaluateTrust(candidateCredential, validationPair)) {
                this.log.debug("Successfully established trust of supplied candidate credential");
                return true;
            }
            this.log.debug("Failed to establish trust of supplied candidate credential");
        }
        else {
            this.log.debug("Cryptographic verification of raw signature failed with candidate credential");
        }
        this.log.debug("PKIX validation of raw signature failed, unable to establish trust of supplied verification credential");
        return false;
    }
    
    @Override
    protected boolean evaluateTrust(final Credential untrustedCredential, final Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair) throws SecurityException {
        if (!(untrustedCredential instanceof X509Credential)) {
            this.log.debug("Can not evaluate trust of non-X509Credential");
            return false;
        }
        final X509Credential untrustedX509Credential = (X509Credential)untrustedCredential;
        final Set<String> trustedNames = validationPair.getFirst();
        final Iterable<PKIXValidationInformation> validationInfoSet = validationPair.getSecond();
        if (!this.checkNames(trustedNames, untrustedX509Credential)) {
            this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
            return false;
        }
        for (final PKIXValidationInformation validationInfo : validationInfoSet) {
            try {
                if (this.pkixTrustEvaluator.validate(validationInfo, untrustedX509Credential)) {
                    this.log.debug("Signature trust established via PKIX validation of signing credential");
                    return true;
                }
                continue;
            }
            catch (SecurityException e) {
                this.log.debug("Error performing PKIX validation on untrusted credential", (Throwable)e);
            }
        }
        this.log.debug("Signature trust could not be established via PKIX validation of signing credential");
        return false;
    }
    
    protected Pair<Set<String>, Iterable<PKIXValidationInformation>> resolveValidationInfo(final CriteriaSet trustBasisCriteria) throws SecurityException {
        Set<String> trustedNames = null;
        if (this.pkixResolver.supportsTrustedNameResolution()) {
            trustedNames = this.pkixResolver.resolveTrustedNames(trustBasisCriteria);
        }
        else {
            this.log.debug("PKIX resolver does not support resolution of trusted names, skipping name checking");
        }
        final Iterable<PKIXValidationInformation> validationInfoSet = this.pkixResolver.resolve(trustBasisCriteria);
        final Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair = new Pair<Set<String>, Iterable<PKIXValidationInformation>>(trustedNames, validationInfoSet);
        return validationPair;
    }
    
    protected boolean checkNames(final Set<String> trustedNames, final X509Credential untrustedCredential) throws SecurityException {
        if (this.credNameEvaluator == null) {
            this.log.debug("No credential name evaluator was available, skipping trusted name evaluation");
            return true;
        }
        return this.credNameEvaluator.evaluate(untrustedCredential, trustedNames);
    }
}
