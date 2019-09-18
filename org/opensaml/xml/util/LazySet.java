// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.NotThreadSafe;
import java.io.Serializable;
import java.util.Set;

@NotThreadSafe
public class LazySet<ElementType> implements Set<ElementType>, Serializable
{
    private static final long serialVersionUID = -1596445680460115174L;
    private Set<ElementType> delegate;
    
    public LazySet() {
        this.delegate = Collections.emptySet();
    }
    
    public boolean add(final ElementType element) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singleton(element);
            return true;
        }
        this.delegate = this.createImplementation();
        return this.delegate.add(element);
    }
    
    public boolean addAll(final Collection<? extends ElementType> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.addAll(collection);
    }
    
    public void clear() {
        this.delegate = Collections.emptySet();
    }
    
    public boolean contains(final Object element) {
        return this.delegate.contains(element);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        return this.delegate.containsAll(collection);
    }
    
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    public Iterator<ElementType> iterator() {
        this.delegate = this.createImplementation();
        return this.delegate.iterator();
    }
    
    public boolean remove(final Object element) {
        this.delegate = this.createImplementation();
        return this.delegate.remove(element);
    }
    
    public boolean removeAll(final Collection<?> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.removeAll(collection);
    }
    
    public boolean retainAll(final Collection<?> collection) {
        this.delegate = this.createImplementation();
        return this.delegate.retainAll(collection);
    }
    
    public int size() {
        return this.delegate.size();
    }
    
    public Object[] toArray() {
        return this.delegate.toArray();
    }
    
    public <T> T[] toArray(final T[] type) {
        return this.delegate.toArray(type);
    }
    
    private Set<ElementType> createImplementation() {
        if (this.delegate instanceof HashSet) {
            return this.delegate;
        }
        return new HashSet<ElementType>((Collection<? extends ElementType>)this.delegate);
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
        return this == obj || (obj != null && this.getClass() == obj.getClass() && this.delegate.equals(((LazySet)obj).delegate));
    }
}
