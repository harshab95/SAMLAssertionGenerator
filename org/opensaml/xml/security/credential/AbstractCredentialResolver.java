// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;

public abstract class AbstractCredentialResolver implements CredentialResolver
{
    public Credential resolveSingle(final CriteriaSet criteriaSet) throws SecurityException {
        final Iterable<Credential> creds = this.resolve(criteriaSet);
        if (creds.iterator().hasNext()) {
            return creds.iterator().next();
        }
        return null;
    }
    
    public abstract Iterable<Credential> resolve(final CriteriaSet p0) throws SecurityException;
}
