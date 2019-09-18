// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import java.security.Key;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.KeyLengthCriteria;
import org.slf4j.Logger;

public class EvaluableKeyLengthCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private Integer keyLength;
    
    public EvaluableKeyLengthCredentialCriteria(final KeyLengthCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyLengthCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.keyLength = criteria.getKeyLength();
    }
    
    public EvaluableKeyLengthCredentialCriteria(final Integer newKeyLength) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyLengthCredentialCriteria.class);
        if (newKeyLength == null) {
            throw new IllegalArgumentException("Key length may not be null");
        }
        this.keyLength = newKeyLength;
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
        final Integer length = SecurityHelper.getKeyLength(key);
        if (length == null) {
            this.log.info("Could not evaluate criteria, can not determine length of key");
            return null;
        }
        final Boolean result = this.keyLength.equals(length);
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
