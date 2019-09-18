// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.security.Criteria;

public final class KeyNameCriteria implements Criteria
{
    private String keyName;
    
    public KeyNameCriteria(final String name) {
        this.setKeyName(name);
    }
    
    public String getKeyName() {
        return this.keyName;
    }
    
    public void setKeyName(final String name) {
        if (DatatypeHelper.isEmpty(name)) {
            throw new IllegalArgumentException("Key name criteria value must be supplied");
        }
        this.keyName = DatatypeHelper.safeTrimOrNullString(name);
    }
}
