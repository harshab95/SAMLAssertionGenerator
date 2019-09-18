// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo.provider;

import org.opensaml.xml.security.keyinfo.KeyInfoCredentialContext;
import org.opensaml.xml.security.keyinfo.KeyInfoResolutionContext;
import java.security.Key;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoProvider;

public abstract class AbstractKeyInfoProvider implements KeyInfoProvider
{
    protected Key extractKeyValue(final Credential cred) {
        if (cred == null) {
            return null;
        }
        if (cred.getPublicKey() != null) {
            return cred.getPublicKey();
        }
        if (cred.getSecretKey() != null) {
            return cred.getSecretKey();
        }
        if (cred.getPrivateKey() != null) {
            return cred.getPrivateKey();
        }
        return null;
    }
    
    protected KeyInfoCredentialContext buildCredentialContext(final KeyInfoResolutionContext kiContext) {
        if (kiContext != null) {
            return new KeyInfoCredentialContext(kiContext.getKeyInfo());
        }
        return null;
    }
}
