// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import java.util.Collection;
import org.opensaml.xml.security.credential.Credential;
import java.util.ArrayList;
import org.opensaml.xml.security.credential.CollectionCredentialResolver;

public class CollectionKeyInfoCredentialResolver extends CollectionCredentialResolver implements KeyInfoCredentialResolver
{
    public CollectionKeyInfoCredentialResolver() {
        this(new ArrayList<Credential>());
    }
    
    public CollectionKeyInfoCredentialResolver(final Collection<Credential> credentials) {
        super(credentials);
    }
}
