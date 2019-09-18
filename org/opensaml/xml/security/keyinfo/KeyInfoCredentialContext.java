// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.credential.CredentialContext;

public class KeyInfoCredentialContext implements CredentialContext
{
    private KeyInfo keyInfo;
    
    public KeyInfoCredentialContext(final KeyInfo ki) {
        this.keyInfo = ki;
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
}
