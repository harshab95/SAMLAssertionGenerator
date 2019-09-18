// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import org.opensaml.xml.security.Criteria;

public final class KeyLengthCriteria implements Criteria
{
    private Integer keyLength;
    
    public KeyLengthCriteria(final Integer length) {
        this.setKeyLength(length);
    }
    
    public Integer getKeyLength() {
        return this.keyLength;
    }
    
    public void setKeyLength(final Integer length) {
        if (length == null) {
            throw new IllegalArgumentException("Key length criteria value must be supplied");
        }
        this.keyLength = length;
    }
}
