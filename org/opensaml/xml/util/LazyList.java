// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import net.jcip.annotations.NotThreadSafe;
import java.io.Serializable;
import java.util.List;

@NotThreadSafe
public class LazyList<ElementType> implements List<ElementType>, Serializable
{
    private static final long serialVersionUID = -7741904523916701817L;
    private List<ElementType> delegate;
    
    public LazyList() {
        this.delegate = Collections.emptyList();
    }
    
    public boolean add(final ElementType item) {
        if (this.delegate.isEmpty()) {
            this.delegate = Collections.singletonList(item);
            return true;
        }
        this.delegate = this.buildList();
        return this.delegate.add(item);
    }
    
    public void add(final int index, final ElementType element) {
        (this.delegate = this.buildList()).add(index, element);
    }
    
    public boolean addAll(final Collection<? extends ElementType> collection) {
        this.delegate = this.buildList();
        return this.delegate.addAll(collection);
    }
    
    public boolean addAll(final int index, final Collection<? extends ElementType> collection) {
        this.delegate = this.buildList();
        return this.delegate.addAll(index, collection);
    }
    
    public void clear() {
        this.delegate = Collections.emptyList();
    }
    
    public boolean contains(final Object element) {
        return this.delegate.contains(element);
    }
    
    public boolean containsAll(final Collection<?> collections) {
        return this.delegate.containsAll(collections);
    }
    
    public ElementType get(final int index) {
        return this.delegate.get(index);
    }
    
    public int indexOf(final Object element) {
        return this.delegate.indexOf(element);
    }
    
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    public Iterator<ElementType> iterator() {
        this.delegate = this.buildList();
        return this.delegate.iterator();
    }
    
    public int lastIndexOf(final Object element) {
        return this.delegate.lastIndexOf(element);
    }
    
    public ListIterator<ElementType> listIterator() {
        this.delegate = this.buildList();
        return this.delegate.listIterator();
    }
    
    public ListIterator<ElementType> listIterator(final int index) {
        this.delegate = this.buildList();
        return this.delegate.listIterator(index);
    }
    
    public boolean remove(final Object element) {
        this.delegate = this.buildList();
        return this.delegate.remove(element);
    }
    
    public ElementType remove(final int index) {
        this.delegate = this.buildList();
        return this.delegate.remove(index);
    }
    
    public boolean removeAll(final Collection<?> collection) {
        this.delegate = this.buildList();
        return this.delegate.removeAll(collection);
    }
    
    public boolean retainAll(final Collection<?> collection) {
        this.delegate = this.buildList();
        return this.delegate.retainAll(collection);
    }
    
    public ElementType set(final int index, final ElementType element) {
        this.delegate = this.buildList();
        return this.delegate.set(index, element);
    }
    
    public int size() {
        return this.delegate.size();
    }
    
    public List<ElementType> subList(final int fromIndex, final int toIndex) {
        this.delegate = this.buildList();
        return this.delegate.subList(fromIndex, toIndex);
    }
    
    public Object[] toArray() {
        return this.delegate.toArray();
    }
    
    public <T> T[] toArray(final T[] type) {
        return this.delegate.toArray(type);
    }
    
    protected List<ElementType> buildList() {
        if (this.delegate instanceof ArrayList) {
            return this.delegate;
        }
        return new ArrayList<ElementType>((Collection<? extends ElementType>)this.delegate);
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
        return this == obj || (obj != null && this.getClass() == obj.getClass() && this.delegate.equals(((LazyList)obj).delegate));
    }
}
