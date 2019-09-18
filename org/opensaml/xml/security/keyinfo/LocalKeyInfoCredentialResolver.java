// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.criteria.PublicKeyCriteria;
import java.security.PublicKey;
import org.opensaml.xml.security.Criteria;
import org.opensaml.xml.security.criteria.KeyNameCriteria;
import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.CriteriaSet;
import java.util.List;
import org.opensaml.xml.security.credential.CredentialResolver;

public class LocalKeyInfoCredentialResolver extends BasicProviderKeyInfoCredentialResolver
{
    private CredentialResolver localCredResolver;
    
    public LocalKeyInfoCredentialResolver(final List<KeyInfoProvider> keyInfoProviders, final CredentialResolver localCredentialResolver) {
        super(keyInfoProviders);
        if (localCredentialResolver == null) {
            throw new IllegalArgumentException("Local credential resolver must be supplied");
        }
        this.localCredResolver = localCredentialResolver;
    }
    
    public CredentialResolver getLocalCredentialResolver() {
        return this.localCredResolver;
    }
    
    @Override
    protected void postProcess(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<Credential> credentials) throws SecurityException {
        final ArrayList<Credential> localCreds = new ArrayList<Credential>();
        for (final Credential cred : credentials) {
            if (this.isLocalCredential(cred)) {
                localCreds.add(cred);
            }
            else {
                if (cred.getPublicKey() == null) {
                    continue;
                }
                localCreds.addAll(this.resolveByPublicKey(cred.getPublicKey()));
            }
        }
        for (final String keyName : kiContext.getKeyNames()) {
            localCreds.addAll(this.resolveByKeyName(keyName));
        }
        credentials.clear();
        credentials.addAll(localCreds);
    }
    
    protected boolean isLocalCredential(final Credential credential) {
        return credential.getPrivateKey() != null || credential.getSecretKey() != null;
    }
    
    protected Collection<? extends Credential> resolveByKeyName(final String keyName) throws SecurityException {
        final ArrayList<Credential> localCreds = new ArrayList<Credential>();
        final CriteriaSet criteriaSet = new CriteriaSet(new KeyNameCriteria(keyName));
        for (final Credential cred : this.getLocalCredentialResolver().resolve(criteriaSet)) {
            if (this.isLocalCredential(cred)) {
                localCreds.add(cred);
            }
        }
        return localCreds;
    }
    
    protected Collection<? extends Credential> resolveByPublicKey(final PublicKey publicKey) throws SecurityException {
        final ArrayList<Credential> localCreds = new ArrayList<Credential>();
        final CriteriaSet criteriaSet = new CriteriaSet(new PublicKeyCriteria(publicKey));
        for (final Credential cred : this.getLocalCredentialResolver().resolve(criteriaSet)) {
            if (this.isLocalCredential(cred)) {
                localCreds.add(cred);
            }
        }
        return localCreds;
    }
}
