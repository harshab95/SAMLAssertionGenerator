// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.credential.Credential;
import java.util.Collections;
import java.util.Set;
import org.opensaml.xml.util.LazyMap;
import java.util.Map;

public class NamedKeyInfoGeneratorManager
{
    private Map<String, KeyInfoGeneratorManager> managers;
    private KeyInfoGeneratorManager defaultManager;
    private boolean useDefaultManager;
    
    public NamedKeyInfoGeneratorManager() {
        this.managers = new LazyMap<String, KeyInfoGeneratorManager>();
        this.defaultManager = new KeyInfoGeneratorManager();
        this.useDefaultManager = true;
    }
    
    public void setUseDefaultManager(final boolean newValue) {
        this.useDefaultManager = newValue;
    }
    
    public Set<String> getManagerNames() {
        return Collections.unmodifiableSet((Set<? extends String>)this.managers.keySet());
    }
    
    public KeyInfoGeneratorManager getManager(final String name) {
        KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            manager = new KeyInfoGeneratorManager();
            this.managers.put(name, manager);
        }
        return manager;
    }
    
    public void removeManager(final String name) {
        this.managers.remove(name);
    }
    
    public void registerFactory(final String name, final KeyInfoGeneratorFactory factory) {
        final KeyInfoGeneratorManager manager = this.getManager(name);
        manager.registerFactory(factory);
    }
    
    public void deregisterFactory(final String name, final KeyInfoGeneratorFactory factory) {
        final KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            throw new IllegalArgumentException("Manager with name '" + name + "' does not exist");
        }
        manager.deregisterFactory(factory);
    }
    
    public void registerDefaultFactory(final KeyInfoGeneratorFactory factory) {
        this.defaultManager.registerFactory(factory);
    }
    
    public void deregisterDefaultFactory(final KeyInfoGeneratorFactory factory) {
        this.defaultManager.deregisterFactory(factory);
    }
    
    public KeyInfoGeneratorManager getDefaultManager() {
        return this.defaultManager;
    }
    
    public KeyInfoGeneratorFactory getFactory(final String name, final Credential credential) {
        final KeyInfoGeneratorManager manager = this.managers.get(name);
        if (manager == null) {
            throw new IllegalArgumentException("Manager with name '" + name + "' does not exist");
        }
        KeyInfoGeneratorFactory factory = manager.getFactory(credential);
        if (factory == null && this.useDefaultManager) {
            factory = this.defaultManager.getFactory(credential);
        }
        return factory;
    }
}
