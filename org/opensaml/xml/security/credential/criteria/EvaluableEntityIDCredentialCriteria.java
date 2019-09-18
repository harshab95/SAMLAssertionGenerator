// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.slf4j.Logger;

public class EvaluableEntityIDCredentialCriteria implements EvaluableCredentialCriteria
{
    private final Logger log;
    private String entityID;
    
    public EvaluableEntityIDCredentialCriteria(final EntityIDCriteria criteria) {
        this.log = LoggerFactory.getLogger((Class)EvaluableEntityIDCredentialCriteria.class);
        if (criteria == null) {
            throw new NullPointerException("Criteria instance may not be null");
        }
        this.entityID = criteria.getEntityID();
    }
    
    public EvaluableEntityIDCredentialCriteria(final String newEntityID) {
        this.log = LoggerFactory.getLogger((Class)EvaluableEntityIDCredentialCriteria.class);
        if (DatatypeHelper.isEmpty(newEntityID)) {
            throw new IllegalArgumentException("Entity ID may not be null");
        }
        this.entityID = newEntityID;
    }
    
    public Boolean evaluate(final Credential target) {
        if (target == null) {
            this.log.error("Credential target was null");
            return null;
        }
        if (DatatypeHelper.isEmpty(target.getEntityId())) {
            this.log.info("Could not evaluate criteria, credential contained no entity ID");
            return null;
        }
        final Boolean result = this.entityID.equals(target.getEntityId());
        return result;
    }
}
