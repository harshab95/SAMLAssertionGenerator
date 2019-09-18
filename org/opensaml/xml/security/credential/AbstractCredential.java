// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import java.security.PrivateKey;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Collection;

public abstract class AbstractCredential implements Credential
{
    protected String entityID;
    protected UsageType usageType;
    protected Collection<String> keyNames;
    protected PublicKey publicKey;
    protected SecretKey secretKey;
    protected PrivateKey privateKey;
    protected final CredentialContextSet credentialContextSet;
    
    public AbstractCredential() {
        this.credentialContextSet = new CredentialContextSet();
    }
    
    public String getEntityId() {
        return this.entityID;
    }
    
    public UsageType getUsageType() {
        return this.usageType;
    }
    
    public Collection<String> getKeyNames() {
        return this.keyNames;
    }
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    public SecretKey getSecretKey() {
        return this.secretKey;
    }
    
    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
    
    public CredentialContextSet getCredentalContextSet() {
        return this.credentialContextSet;
    }
}
