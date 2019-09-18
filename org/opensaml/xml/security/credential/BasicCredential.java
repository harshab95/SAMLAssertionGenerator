// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.security.PrivateKey;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.LazySet;

public class BasicCredential extends AbstractCredential
{
    public BasicCredential() {
        this.keyNames = new LazySet<String>();
        this.usageType = UsageType.UNSPECIFIED;
    }
    
    public Class<? extends Credential> getCredentialType() {
        return Credential.class;
    }
    
    public void setEntityId(final String id) {
        this.entityID = DatatypeHelper.safeTrimOrNullString(id);
    }
    
    public void setUsageType(final UsageType usage) {
        if (usage != null) {
            this.usageType = usage;
        }
        else {
            this.usageType = UsageType.UNSPECIFIED;
        }
    }
    
    public void setPublicKey(final PublicKey key) {
        this.publicKey = key;
        if (key != null) {
            this.setSecretKey(null);
        }
    }
    
    public void setSecretKey(final SecretKey key) {
        this.secretKey = key;
        if (key != null) {
            this.setPublicKey(null);
            this.setPrivateKey(null);
        }
    }
    
    public void setPrivateKey(final PrivateKey key) {
        this.privateKey = key;
        if (key != null) {
            this.setSecretKey(null);
        }
    }
}
