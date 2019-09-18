// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential;

import org.opensaml.xml.security.CriteriaSet;
import java.util.Map;

public class FilesystemCredentialResolver extends AbstractCriteriaFilteringCredentialResolver
{
    public FilesystemCredentialResolver(final String credentialDirectory, final Map<String, String> passwords) {
    }
    
    @Override
    protected Iterable<Credential> resolveFromSource(final CriteriaSet criteriaSet) {
        throw new UnsupportedOperationException("Functionality not yet implemented");
    }
}
