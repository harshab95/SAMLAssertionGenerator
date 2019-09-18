// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.Criteria;

public final class EntityIDCriteria implements Criteria
{
    private String entityID;
    
    public EntityIDCriteria(final String entity) {
        this.setEntityID(entity);
    }
    
    public String getEntityID() {
        return this.entityID;
    }
    
    public void setEntityID(final String entity) {
        final String trimmed = DatatypeHelper.safeTrimOrNullString(entity);
        if (trimmed == null) {
            throw new IllegalArgumentException("Entity ID criteria must be supplied");
        }
        this.entityID = trimmed;
    }
}
