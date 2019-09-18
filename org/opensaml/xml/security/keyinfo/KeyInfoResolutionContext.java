// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.util.LazySet;
import org.opensaml.xml.util.LazyMap;
import java.util.Collections;
import java.util.Map;
import org.opensaml.xml.security.credential.Credential;
import java.util.Collection;
import java.security.Key;
import java.util.Set;
import org.opensaml.xml.signature.KeyInfo;

public class KeyInfoResolutionContext
{
    private KeyInfo keyInfo;
    private Set<String> keyNames;
    private Key key;
    private Collection<Credential> resolvedCredentials;
    private final Map<String, Object> properties;
    
    public KeyInfoResolutionContext(final Collection<Credential> credentials) {
        this.resolvedCredentials = Collections.unmodifiableCollection((Collection<? extends Credential>)credentials);
        this.properties = new LazyMap<String, Object>();
        this.keyNames = new LazySet<String>();
    }
    
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }
    
    public void setKeyInfo(final KeyInfo newKeyInfo) {
        this.keyInfo = newKeyInfo;
    }
    
    public Set<String> getKeyNames() {
        return this.keyNames;
    }
    
    public Key getKey() {
        return this.key;
    }
    
    public void setKey(final Key newKey) {
        this.key = newKey;
    }
    
    public Collection<Credential> getResolvedCredentials() {
        return this.resolvedCredentials;
    }
    
    public Map<String, Object> getProperties() {
        return this.properties;
    }
}
