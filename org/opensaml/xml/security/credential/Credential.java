// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;

public interface Credential
{
    String getEntityId();
    
    UsageType getUsageType();
    
    Collection<String> getKeyNames();
    
    PublicKey getPublicKey();
    
    PrivateKey getPrivateKey();
    
    SecretKey getSecretKey();
    
    CredentialContextSet getCredentalContextSet();
    
    Class<? extends Credential> getCredentialType();
}
