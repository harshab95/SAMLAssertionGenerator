// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import java.security.KeyException;
import org.opensaml.xml.security.SecurityHelper;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import org.opensaml.xml.security.credential.BasicCredential;
import java.util.Iterator;
import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.XMLObject;
import java.security.Key;
import java.util.Set;
import java.util.HashSet;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.CriteriaSet;
import java.util.Collection;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.slf4j.Logger;
import org.opensaml.xml.security.credential.AbstractCriteriaFilteringCredentialResolver;

public class BasicProviderKeyInfoCredentialResolver extends AbstractCriteriaFilteringCredentialResolver implements KeyInfoCredentialResolver
{
    private final Logger log;
    private List<KeyInfoProvider> providers;
    
    public BasicProviderKeyInfoCredentialResolver(final List<KeyInfoProvider> keyInfoProviders) {
        this.log = LoggerFactory.getLogger((Class)BasicProviderKeyInfoCredentialResolver.class);
        (this.providers = new ArrayList<KeyInfoProvider>()).addAll(keyInfoProviders);
    }
    
    protected List<KeyInfoProvider> getProviders() {
        return this.providers;
    }
    
    @Override
    protected Iterable<Credential> resolveFromSource(final CriteriaSet criteriaSet) throws SecurityException {
        final KeyInfoCriteria kiCriteria = criteriaSet.get(KeyInfoCriteria.class);
        if (kiCriteria == null) {
            this.log.error("No KeyInfo criteria supplied, resolver could not process");
            throw new SecurityException("Credential criteria set did not contain an instance ofKeyInfoCredentialCriteria");
        }
        final KeyInfo keyInfo = kiCriteria.getKeyInfo();
        final List<Credential> credentials = new ArrayList<Credential>();
        final KeyInfoResolutionContext kiContext = new KeyInfoResolutionContext(credentials);
        if (keyInfo != null) {
            this.processKeyInfo(keyInfo, kiContext, criteriaSet, credentials);
        }
        else {
            this.log.info("KeyInfo was null, any credentials will be resolved by post-processing hooks only");
        }
        this.postProcess(kiContext, criteriaSet, credentials);
        if (credentials.isEmpty()) {
            this.log.debug("No credentials were found, calling empty credentials post-processing hook");
            this.postProcessEmptyCredentials(kiContext, criteriaSet, credentials);
        }
        this.log.debug("A total of {} credentials were resolved", (Object)credentials.size());
        return credentials;
    }
    
    private void processKeyInfo(final KeyInfo keyInfo, final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<Credential> credentials) throws SecurityException {
        this.initResolutionContext(kiContext, keyInfo, criteriaSet);
        final Key keyValueKey = kiContext.getKey();
        final HashSet<String> keyNames = new HashSet<String>();
        keyNames.addAll((Collection<?>)kiContext.getKeyNames());
        this.processKeyInfoChildren(kiContext, criteriaSet, credentials);
        if (credentials.isEmpty() && keyValueKey != null) {
            final Credential keyValueCredential = this.buildBasicCredential(keyValueKey, keyNames);
            if (keyValueCredential != null) {
                this.log.debug("No credentials were extracted by registered non-KeyValue handling providers, adding KeyValue credential to returned credential set");
                credentials.add(keyValueCredential);
            }
        }
    }
    
    protected void postProcess(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<Credential> credentials) throws SecurityException {
    }
    
    protected void postProcessEmptyCredentials(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<Credential> credentials) throws SecurityException {
    }
    
    protected void processKeyInfoChildren(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<Credential> credentials) throws SecurityException {
        for (final XMLObject keyInfoChild : kiContext.getKeyInfo().getXMLObjects()) {
            if (keyInfoChild instanceof KeyValue) {
                continue;
            }
            this.log.debug("Processing KeyInfo child with qname: {}", (Object)keyInfoChild.getElementQName());
            final Collection<Credential> childCreds = this.processKeyInfoChild(kiContext, criteriaSet, keyInfoChild);
            if (childCreds != null && !childCreds.isEmpty()) {
                credentials.addAll(childCreds);
            }
            else if (keyInfoChild instanceof KeyName) {
                this.log.debug("KeyName, with value {}, did not independently produce a credential based on any registered providers", (Object)((KeyName)keyInfoChild).getValue());
            }
            else {
                this.log.warn("No credentials could be extracted from KeyInfo child with qname {} by any registered provider", (Object)keyInfoChild.getElementQName());
            }
        }
    }
    
    protected Collection<Credential> processKeyInfoChild(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final XMLObject keyInfoChild) throws SecurityException {
        for (final KeyInfoProvider provider : this.getProviders()) {
            if (!provider.handles(keyInfoChild)) {
                this.log.debug("Provider {} doesn't handle objects of type {}, skipping", (Object)provider.getClass().getName(), (Object)keyInfoChild.getElementQName());
            }
            else {
                this.log.debug("Processing KeyInfo child {} with provider {}", (Object)keyInfoChild.getElementQName(), (Object)provider.getClass().getName());
                final Collection<Credential> creds = provider.process(this, keyInfoChild, criteriaSet, kiContext);
                if (creds != null && !creds.isEmpty()) {
                    this.log.debug("Credentials successfully extracted from child {} by provider {}", (Object)keyInfoChild.getElementQName(), (Object)provider.getClass().getName());
                    return creds;
                }
                continue;
            }
        }
        return null;
    }
    
    protected void initResolutionContext(final KeyInfoResolutionContext kiContext, final KeyInfo keyInfo, final CriteriaSet criteriaSet) throws SecurityException {
        kiContext.setKeyInfo(keyInfo);
        kiContext.getKeyNames().addAll(KeyInfoHelper.getKeyNames(keyInfo));
        this.log.debug("Found {} key names: {}", (Object)kiContext.getKeyNames().size(), (Object)kiContext.getKeyNames());
        this.resolveKeyValue(kiContext, criteriaSet, keyInfo.getKeyValues());
    }
    
    protected void resolveKeyValue(final KeyInfoResolutionContext kiContext, final CriteriaSet criteriaSet, final List<KeyValue> keyValues) throws SecurityException {
        for (final KeyValue keyValue : keyValues) {
            final Collection<Credential> creds = this.processKeyInfoChild(kiContext, criteriaSet, keyValue);
            if (creds != null) {
                for (final Credential cred : creds) {
                    final Key key = this.extractKeyValue(cred);
                    if (key != null) {
                        kiContext.setKey(key);
                        this.log.debug("Found a credential based on a KeyValue having key type: {}", (Object)key.getAlgorithm());
                    }
                }
            }
        }
    }
    
    protected Credential buildBasicCredential(final Key key, final Set<String> keyNames) throws SecurityException {
        if (key == null) {
            this.log.debug("Key supplied was null, could not build credential");
            return null;
        }
        final BasicCredential basicCred = new BasicCredential();
        basicCred.getKeyNames().addAll(keyNames);
        if (key instanceof PublicKey) {
            basicCred.setPublicKey((PublicKey)key);
        }
        else {
            if (!(key instanceof SecretKey)) {
                if (key instanceof PrivateKey) {
                    final PrivateKey privateKey = (PrivateKey)key;
                    try {
                        final PublicKey publicKey = SecurityHelper.derivePublicKey(privateKey);
                        if (publicKey != null) {
                            basicCred.setPublicKey(publicKey);
                            basicCred.setPrivateKey(privateKey);
                            return basicCred;
                        }
                        this.log.error("Failed to derive public key from private key");
                        return null;
                    }
                    catch (KeyException e) {
                        this.log.error("Could not derive public key from private key", (Throwable)e);
                        return null;
                    }
                }
                this.log.error(String.format("Key was of an unsupported type '%s'", key.getClass().getName()));
                return null;
            }
            basicCred.setSecretKey((SecretKey)key);
        }
        return basicCred;
    }
    
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
}
