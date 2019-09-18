// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class StaticCredentialResolver extends AbstractCredentialResolver
{
    private List<Credential> creds;
    
    public StaticCredentialResolver(final List<Credential> credentials) {
        (this.creds = new ArrayList<Credential>()).addAll(credentials);
    }
    
    public StaticCredentialResolver(final Credential credential) {
        (this.creds = new ArrayList<Credential>()).add(credential);
    }
    
    @Override
    public Iterable<Credential> resolve(final CriteriaSet criteria) throws SecurityException {
        return this.creds;
    }
}
