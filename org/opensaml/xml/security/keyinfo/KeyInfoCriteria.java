// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.Criteria;

public final class KeyInfoCriteria implements Criteria
{
    private KeyInfo keyInfo;
    
    public KeyInfoCriteria(final KeyInfo newKeyInfo) {
        this.setKeyInfo(newKeyInfo);
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
    
    public void setKeyInfo(final KeyInfo newKeyInfo) {
        this.keyInfo = newKeyInfo;
    }
}
