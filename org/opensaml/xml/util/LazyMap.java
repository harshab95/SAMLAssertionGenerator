// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.Collections;
import net.jcip.annotations.NotThreadSafe;
import java.io.Serializable;
import java.util.Map;

@NotThreadSafe
public class LazyMap<KeyType, ValueType> implements Map<KeyType, ValueType>, Serializable
{
    private static final long serialVersionUID = 121425595164176639L;
    private Map<KeyType, ValueType> delegate;
    
    public LazyMap() {
        this.delegate = Collections.emptyMap();
    }
    
    public void clear() {
        this.delegate = Collections.emptyMap();
    }
    
    public boolean containsKey(final Object key) {
        return this.delegate.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        return this.delegate.containsValue(value);
    }
    
    public Set<Entry<KeyType, ValueType>> entrySet() {
        this.delegate = this.buildMap();
        return this.delegate.entrySet();
    }
    
    public ValueType get(final Object key) {
        return this.delegate.get(key);
    }
    
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    public Set<KeyType> keySet() {
        this.delegate = this.buildMap();
        return this.delegate.keySet();
    }
    
    public ValueType put(final KeyType key, final ValueType value) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singletonMap(key, value);
            return null;
        }
        this.delegate = this.buildMap();
        return this.delegate.put(key, value);
    }
    
    public void putAll(final Map<? extends KeyType, ? extends ValueType> t) {
        (this.delegate = this.buildMap()).putAll(t);
    }
    
    public ValueType remove(final Object key) {
        this.delegate = this.buildMap();
        return this.delegate.remove(key);
    }
    
    public int size() {
        return this.delegate.size();
    }
    
    public Collection<ValueType> values() {
        this.delegate = this.buildMap();
        return this.delegate.values();
    }
    
    protected Map<KeyType, ValueType> buildMap() {
        if (this.delegate instanceof HashMap) {
            return this.delegate;
        }
        return new HashMap<KeyType, ValueType>((Map<? extends KeyType, ? extends ValueType>)this.delegate);
    }
    
    @Override
    public String toString() {
        return this.delegate.toString();
    }
    
    @Override
    public int hashCode() {
        return this.delegate.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj != null && this.getClass() == obj.getClass() && this.delegate.equals(((LazyMap)obj).delegate));
    }
}
