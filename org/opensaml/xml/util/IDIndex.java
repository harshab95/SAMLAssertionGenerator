// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import org.opensaml.xml.XMLObject;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class IDIndex
{
    private XMLObject owner;
    private Map<String, XMLObject> idMappings;
    
    public IDIndex(final XMLObject newOwner) throws NullPointerException {
        if (newOwner == null) {
            throw new NullPointerException("Attribute owner XMLObject may not be null");
        }
        this.owner = newOwner;
        this.idMappings = new LazyMap<String, XMLObject>();
    }
    
    public void registerIDMapping(final String id, final XMLObject referent) {
        if (id == null) {
            return;
        }
        this.idMappings.put(id, referent);
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMapping(id, referent);
        }
    }
    
    public void registerIDMappings(final IDIndex idIndex) {
        if (idIndex == null || idIndex.isEmpty()) {
            return;
        }
        this.idMappings.putAll(idIndex.getIDMappings());
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMappings(idIndex);
        }
    }
    
    public void deregisterIDMapping(final String id) {
        if (id == null) {
            return;
        }
        this.idMappings.remove(id);
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMapping(id);
        }
    }
    
    public void deregisterIDMappings(final IDIndex idIndex) {
        if (idIndex == null || idIndex.isEmpty()) {
            return;
        }
        for (final String id : idIndex.getIDs()) {
            this.idMappings.remove(id);
        }
        if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMappings(idIndex);
        }
    }
    
    public XMLObject lookup(final String id) {
        return this.idMappings.get(id);
    }
    
    public boolean isEmpty() {
        return this.idMappings.isEmpty();
    }
    
    public Set<String> getIDs() {
        return Collections.unmodifiableSet((Set<? extends String>)this.idMappings.keySet());
    }
    
    protected Map<String, XMLObject> getIDMappings() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends XMLObject>)this.idMappings);
    }
}
