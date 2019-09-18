// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.credential.CredentialResolver;
import org.slf4j.Logger;
import org.opensaml.xml.security.credential.Credential;

public class ExplicitKeyTrustEngine implements TrustedCredentialTrustEngine<Credential>
{
    private final Logger log;
    private CredentialResolver credentialResolver;
    private ExplicitKeyTrustEvaluator trustEvaluator;
    
    public ExplicitKeyTrustEngine(final CredentialResolver resolver) {
        this.log = LoggerFactory.getLogger((Class)ExplicitKeyTrustEngine.class);
        if (resolver == null) {
            throw new IllegalArgumentException("Credential resolver may not be null");
        }
        this.credentialResolver = resolver;
        this.trustEvaluator = new ExplicitKeyTrustEvaluator();
    }
    
    public CredentialResolver getCredentialResolver() {
        return this.credentialResolver;
    }
    
    public boolean validate(final Credential untrustedCredential, final CriteriaSet trustBasisCriteria) throws SecurityException {
        this.checkParams(untrustedCredential, trustBasisCriteria);
        this.log.debug("Attempting to validate untrusted credential");
        final Iterable<Credential> trustedCredentials = this.getCredentialResolver().resolve(trustBasisCriteria);
        return this.trustEvaluator.validate(untrustedCredential, trustedCredentials);
    }
    
    protected void checkParams(final Credential untrustedCredential, final CriteriaSet trustBasisCriteria) throws SecurityException {
        if (untrustedCredential == null) {
            throw new SecurityException("Untrusted credential was null");
        }
        if (trustBasisCriteria == null) {
            throw new SecurityException("Trust basis criteria set was null");
        }
        if (trustBasisCriteria.isEmpty()) {
            throw new SecurityException("Trust basis criteria set was empty");
        }
    }
}
