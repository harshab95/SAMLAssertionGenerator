// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.util.ClassIndexedSet;
import org.opensaml.xml.security.SigningUtil;
import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import org.opensaml.xml.security.criteria.KeyAlgorithmCriteria;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.Criteria;
import java.util.Collection;
import org.opensaml.xml.security.CriteriaSet;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.trust.ExplicitKeyTrustEvaluator;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.slf4j.Logger;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.security.trust.TrustedCredentialTrustEngine;
import org.opensaml.xml.security.credential.Credential;

public class ExplicitKeySignatureTrustEngine extends BaseSignatureTrustEngine<Iterable<Credential>> implements TrustedCredentialTrustEngine<Signature>
{
    private final Logger log;
    private CredentialResolver credentialResolver;
    private ExplicitKeyTrustEvaluator keyTrust;
    
    public ExplicitKeySignatureTrustEngine(final CredentialResolver resolver, final KeyInfoCredentialResolver keyInfoResolver) {
        super(keyInfoResolver);
        this.log = LoggerFactory.getLogger((Class)ExplicitKeySignatureTrustEngine.class);
        if (resolver == null) {
            throw new IllegalArgumentException("Credential resolver may not be null");
        }
        this.credentialResolver = resolver;
        this.keyTrust = new ExplicitKeyTrustEvaluator();
    }
    
    public CredentialResolver getCredentialResolver() {
        return this.credentialResolver;
    }
    
    public boolean validate(final Signature signature, final CriteriaSet trustBasisCriteria) throws SecurityException {
        this.checkParams(signature, trustBasisCriteria);
        final CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.addAll(trustBasisCriteria);
        if (!criteriaSet.contains(UsageCriteria.class)) {
            ((ClassIndexedSet<UsageCriteria>)criteriaSet).add(new UsageCriteria(UsageType.SIGNING));
        }
        final String jcaAlgorithm = SecurityHelper.getKeyAlgorithmFromURI(signature.getSignatureAlgorithm());
        if (!DatatypeHelper.isEmpty(jcaAlgorithm)) {
            ((ClassIndexedSet<KeyAlgorithmCriteria>)criteriaSet).add(new KeyAlgorithmCriteria(jcaAlgorithm), true);
        }
        final Iterable<Credential> trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
        if (this.validate(signature, trustedCredentials)) {
            return true;
        }
        this.log.debug("Attempting to verify signature using trusted credentials");
        for (final Credential trustedCredential : trustedCredentials) {
            if (this.verifySignature(signature, trustedCredential)) {
                this.log.debug("Successfully verified signature using resolved trusted credential");
                return true;
            }
        }
        this.log.debug("Failed to verify signature using either KeyInfo-derived or directly trusted credentials");
        return false;
    }
    
    public boolean validate(final byte[] signature, final byte[] content, final String algorithmURI, final CriteriaSet trustBasisCriteria, final Credential candidateCredential) throws SecurityException {
        this.checkParamsRaw(signature, content, algorithmURI, trustBasisCriteria);
        final CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.addAll(trustBasisCriteria);
        if (!criteriaSet.contains(UsageCriteria.class)) {
            ((ClassIndexedSet<UsageCriteria>)criteriaSet).add(new UsageCriteria(UsageType.SIGNING));
        }
        final String jcaAlgorithm = SecurityHelper.getKeyAlgorithmFromURI(algorithmURI);
        if (!DatatypeHelper.isEmpty(jcaAlgorithm)) {
            ((ClassIndexedSet<KeyAlgorithmCriteria>)criteriaSet).add(new KeyAlgorithmCriteria(jcaAlgorithm), true);
        }
        final Iterable<Credential> trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
        if (candidateCredential != null && SigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
            this.log.debug("Successfully verified signature using supplied candidate credential");
            this.log.debug("Attempting to establish trust of supplied candidate credential");
            if (this.evaluateTrust(candidateCredential, trustedCredentials)) {
                this.log.debug("Successfully established trust of supplied candidate credential");
                return true;
            }
            this.log.debug("Failed to establish trust of supplied candidate credential");
        }
        this.log.debug("Attempting to verify signature using trusted credentials");
        for (final Credential trustedCredential : trustedCredentials) {
            if (SigningUtil.verifyWithURI(trustedCredential, algorithmURI, signature, content)) {
                this.log.debug("Successfully verified signature using resolved trusted credential");
                return true;
            }
        }
        this.log.debug("Failed to verify signature using either supplied candidate credential or directly trusted credentials");
        return false;
    }
    
    @Override
    protected boolean evaluateTrust(final Credential untrustedCredential, final Iterable<Credential> trustedCredentials) throws SecurityException {
        return this.keyTrust.validate(untrustedCredential, trustedCredentials);
    }
}
