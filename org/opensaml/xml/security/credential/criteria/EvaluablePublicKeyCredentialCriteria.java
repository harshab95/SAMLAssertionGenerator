// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import org.opensaml.xml.security.credential.Credential;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.PublicKeyCriteria;
import java.security.PublicKey;
import org.slf4j.Logger;

public class EvaluablePublicKeyCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private PublicKey publicKey;
    
    public EvaluablePublicKeyCredentialCriteria(final PublicKeyCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluablePublicKeyCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.publicKey = criteria.getPublicKey();
    }
    
    public EvaluablePublicKeyCredentialCriteria(final PublicKey newPublicKey) {
        this.log = LoggerFactory.getLogger((Class)EvaluablePublicKeyCredentialCriteria.class);
        if (newPublicKey == null) {
            throw new IllegalArgumentException("Public key may not be null");
        }
        this.publicKey = newPublicKey;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        final PublicKey key = target.getPublicKey();
        if (key == null) {
            this.log.info("Credential contained no public key, does not satisfy public key criteria");
            return Boolean.FALSE;
        }
        final Boolean result = this.publicKey.equals(key);
        return result;
    }
}
