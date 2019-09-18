// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.KeyNameCriteria;
import org.slf4j.Logger;

public class EvaluableKeyNameCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private String keyName;
    
    public EvaluableKeyNameCredentialCriteria(final KeyNameCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyNameCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.keyName = criteria.getKeyName();
    }
    
    public EvaluableKeyNameCredentialCriteria(final String newKeyName) {
        this.log = LoggerFactory.getLogger((Class)EvaluableKeyNameCredentialCriteria.class);
        if (DatatypeHelper.isEmpty(newKeyName)) {
            throw new IllegalArgumentException("Key name may not be null");
        }
        this.keyName = newKeyName;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (target.getKeyNames().isEmpty()) {
            this.log.info("Could not evaluate criteria, credential contained no key names");
            return null;
        }
        final Boolean result = target.getKeyNames().contains(this.keyName);
        return result;
    }
}
