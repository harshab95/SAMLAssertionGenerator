// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.credential.Credential;
import java.util.List;
import org.opensaml.xml.security.credential.StaticCredentialResolver;

public class StaticKeyInfoCredentialResolver extends StaticCredentialResolver implements KeyInfoCredentialResolver
{
    public StaticKeyInfoCredentialResolver(final List<Credential> credentials) {
        super(credentials);
    }
    
    public StaticKeyInfoCredentialResolver(final Credential credential) {
        super(credential);
    }
}
