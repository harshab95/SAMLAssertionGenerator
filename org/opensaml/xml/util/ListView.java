// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import java.util.AbstractList;
import org.opensaml.xml.XMLObject;

class ListView<ElementType extends XMLObject> extends AbstractList<ElementType>
{
    private IndexedXMLObjectChildrenList<ElementType> backingList;
    private QName index;
    private List<ElementType> indexList;
    
    public ListView(final IndexedXMLObjectChildrenList<ElementType> newBackingList, final QName newIndex) {
        this.backingList = newBackingList;
        this.index = newIndex;
        this.indexList = this.backingList.get(this.index);
    }
    
    @Override
    public boolean add(final ElementType o) {
        final boolean result = this.backingList.add(o);
        this.indexList = this.backingList.get(this.index);
        return result;
    }
    
    @Override
    public void add(final int newIndex, final ElementType element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean addAll(final Collection<? extends ElementType> c) {
        final boolean result = this.backingList.addAll((Collection<?>)c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends ElementType> c) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void clear() {
        this.backingList.clear();
        this.indexList = this.backingList.get(this.index);
    }
    
    @Override
    public boolean contains(final Object element) {
        return this.indexList.contains(element);
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        return this.indexList.containsAll(c);
    }
    
    @Override
    public ElementType get(final int newIndex) {
        return this.indexList.get(newIndex);
    }
    
    @Override
    public int indexOf(final Object o) {
        return this.backingList.indexOf(o);
    }
    
    @Override
    public boolean isEmpty() {
        return this.indexList.isEmpty();
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        return this.backingList.lastIndexOf(o);
    }
    
    @Override
    public ElementType remove(final int newIndex) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean remove(final Object o) {
        final boolean result = this.backingList.remove(o);
        this.indexList = this.backingList.get(this.index);
        return result;
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        final boolean result = this.backingList.removeAll(c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        final boolean result = this.backingList.retainAll(c);
        this.indexList = this.backingList.get(this.index);
        return result;
    }
    
    @Override
    public ElementType set(final int newIndex, final ElementType element) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int size() {
        return this.indexList.size();
    }
    
    @Override
    public Object[] toArray() {
        return this.indexList.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] a) {
        return this.indexList.toArray(a);
    }
}
