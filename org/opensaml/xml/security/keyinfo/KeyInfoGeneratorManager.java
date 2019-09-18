// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import org.opensaml.xml.security.credential.Credential;
import java.util.Map;

public class KeyInfoGeneratorManager
{
    private Map<Class<? extends Credential>, KeyInfoGeneratorFactory> factories;
    
    public KeyInfoGeneratorManager() {
        this.factories = new HashMap<Class<? extends Credential>, KeyInfoGeneratorFactory>(5);
    }
    
    public void registerFactory(final KeyInfoGeneratorFactory factory) {
        this.factories.put(factory.getCredentialType(), factory);
    }
    
    public void deregisterFactory(final KeyInfoGeneratorFactory factory) {
        this.factories.remove(factory.getCredentialType());
    }
    
    public Collection<KeyInfoGeneratorFactory> getFactories() {
        return Collections.unmodifiableCollection((Collection<? extends KeyInfoGeneratorFactory>)this.factories.values());
    }
    
    public KeyInfoGeneratorFactory getFactory(final Credential credential) {
        return this.factories.get(credential.getCredentialType());
    }
}
