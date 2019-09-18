// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionCredentialResolver extends AbstractCriteriaFilteringCredentialResolver
{
    private Collection<Credential> collection;
    
    public CollectionCredentialResolver() {
        this.collection = new ArrayList<Credential>();
    }
    
    public CollectionCredentialResolver(final Collection<Credential> credentials) {
        this.collection = credentials;
    }
    
    public Collection<Credential> getCollection() {
        return this.collection;
    }
    
    @Override
    protected Iterable<Credential> resolveFromSource(final CriteriaSet criteriaSet) throws SecurityException {
        return this.collection;
    }
}
