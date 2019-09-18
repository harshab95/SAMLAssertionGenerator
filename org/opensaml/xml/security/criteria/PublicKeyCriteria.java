// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.criteria;

import java.security.PublicKey;
import org.opensaml.xml.security.Criteria;

public final class PublicKeyCriteria implements Criteria
{
    private PublicKey publicKey;
    
    public PublicKeyCriteria(final PublicKey pubKey) {
        this.setPublicKey(pubKey);
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    public void setPublicKey(final PublicKey key) {
        if (key == null) {
            throw new IllegalArgumentException("Public key criteria value must be supplied");
        }
        this.publicKey = key;
    }
}
