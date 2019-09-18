// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class ValueTypeIndexedMap<KeyType, ValueType> implements Map<KeyType, ValueType>
{
    private Map<Class<?>, Map<KeyType, ValueType>> index;
    private Map<KeyType, ValueType> map;
    private Set<Class> types;
    
    public ValueTypeIndexedMap() {
        this((Collection)new HashSet());
    }
    
    public ValueTypeIndexedMap(final Map<KeyType, ValueType> newMap, final Collection<Class> newTypes) {
        this.map = newMap;
        this.types = new HashSet<Class>(newTypes);
        this.index = new HashMap<Class<?>, Map<KeyType, ValueType>>();
        this.rebuildIndex();
    }
    
    public ValueTypeIndexedMap(final Collection<Class> newTypes) {
        this((Map)new HashMap(), newTypes);
    }
    
    public void clear() {
        this.map.clear();
        this.rebuildIndex();
    }
    
    public boolean containsKey(final Object key) {
        return this.map.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        return this.map.containsValue(value);
    }
    
    public Set<Entry<KeyType, ValueType>> entrySet() {
        return this.map.entrySet();
    }
    
    public ValueType get(final Object key) {
        return this.map.get(key);
    }
    
    public Set<Class> getTypes() {
        return this.types;
    }
    
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    public Set<KeyType> keySet() {
        return this.map.keySet();
    }
    
    private Boolean matchType(final Class<?> type, final Object object) {
        if (!type.isInstance(object) && (type != NullValue.class || object != null)) {
            return false;
        }
        return true;
    }
    
    public ValueType put(final KeyType key, final ValueType value) {
        final ValueType oldValue = this.map.put(key, value);
        for (Class<?> type : this.index.keySet()) {
            if (type == null) {
                type = NullValue.class;
            }
            if (this.matchType(type, value)) {
                this.index.get(type).put(key, value);
            }
            else {
                if (!this.matchType(type, oldValue)) {
                    continue;
                }
                this.index.get(type).remove(key);
            }
        }
        return oldValue;
    }
    
    public void putAll(final Map<? extends KeyType, ? extends ValueType> t) {
        for (final KeyType key : t.keySet()) {
            this.put(key, t.get(key));
        }
    }
    
    public void rebuildIndex() {
        this.index.clear();
        for (Class<?> type : this.types) {
            if (type == null) {
                type = NullValue.class;
            }
            this.index.put(type, new HashMap<KeyType, ValueType>());
            for (final KeyType key : this.map.keySet()) {
                final ValueType value = this.map.get(key);
                if (this.matchType(type, value)) {
                    this.index.get(type).put(key, value);
                }
            }
        }
    }
    
    public ValueType remove(final Object key) {
        final ValueType value = this.map.remove(key);
        for (final Class<?> type : this.index.keySet()) {
            if (type.isInstance(value)) {
                this.index.get(type).remove(key);
            }
        }
        return value;
    }
    
    public void setTypes(final Collection<Class> newTypes) {
        this.types = new HashSet<Class>(newTypes);
    }
    
    public int size() {
        return this.map.size();
    }
    
    public <SubType extends ValueType> Map<KeyType, SubType> subMap(final Class<SubType> type) {
        Class<?> key = type;
        if (key == null) {
            key = NullValue.class;
        }
        if (this.index.containsKey(key)) {
            return Collections.unmodifiableMap((Map<? extends KeyType, ? extends SubType>)this.index.get(key));
        }
        return Collections.emptyMap();
    }
    
    @Override
    public String toString() {
        return this.map.toString();
    }
    
    public Collection<ValueType> values() {
        return this.map.values();
    }
    
    private static class NullValue
    {
    }
}
