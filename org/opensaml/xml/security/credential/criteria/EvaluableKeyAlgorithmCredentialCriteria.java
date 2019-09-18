// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.Key;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.KeyAlgorithmCriteria;
import org.slf4j.Logger;

public class EvaluableKeyAlgorithmCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private String keyAlgorithm;
    
    public EvaluableKeyAlgorithmCredentialCriteria(final KeyAlgorithmCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyAlgorithmCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.keyAlgorithm = criteria.getKeyAlgorithm();
    }
    
    public EvaluableKeyAlgorithmCredentialCriteria(final String newKeyAlgorithm) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyAlgorithmCredentialCriteria.class);
        if (DatatypeHelper.isEmpty(newKeyAlgorithm)) {
            throw new IllegalArgumentException("Key algorithm may not be null");
        }
        this.keyAlgorithm = newKeyAlgorithm;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        final Key key = this.getKey(target);
        if (key == null) {
            this.log.info("Could not evaluate criteria, credential contained no key");
            return null;
        }
        final String algorithm = DatatypeHelper.safeTrimOrNullString(key.getAlgorithm());
        if (algorithm == null) {
            this.log.info("Could not evaluate criteria, key does not specify an algorithm via getAlgorithm()");
            return null;
        }
        final Boolean result = this.keyAlgorithm.equals(algorithm);
        return result;
    }
    
    private Key getKey(final Credential credential) {
        if (credential.getPublicKey() != null) {
            return credential.getPublicKey();
        }
        if (credential.getSecretKey() != null) {
            return credential.getSecretKey();
        }
        if (credential.getPrivateKey() != null) {
            return credential.getPrivateKey();
        }
        return null;
    }
}
