// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Collection;
import java.util.List;
import java.util.AbstractList;
import org.opensaml.xml.XMLObject;

public class XMLObjectChildrenList<ElementType extends XMLObject> extends AbstractList<ElementType>
{
    private XMLObject parent;
    private List<ElementType> elements;
    
    public XMLObjectChildrenList(final XMLObject newParent) throws NullPointerException {
        if (newParent == null) {
            throw new NullPointerException("Parent may not be null");
        }
        this.parent = newParent;
        this.elements = new LazyList<ElementType>();
    }
    
    public XMLObjectChildrenList(final XMLObject newParent, final Collection<ElementType> newElements) throws NullPointerException {
        if (newParent == null) {
            throw new NullPointerException("Parent may not be null");
        }
        this.parent = newParent;
        this.elements = new LazyList<ElementType>();
        this.addAll((Collection<? extends ElementType>)newElements);
    }
    
    @Override
    public int size() {
        return this.elements.size();
    }
    
    public boolean contains(final ElementType element) {
        return this.elements.contains(element);
    }
    
    @Override
    public ElementType get(final int index) {
        return this.elements.get(index);
    }
    
    @Override
    public ElementType set(final int index, final ElementType element) throws IllegalArgumentException {
        if (element == null) {
            return null;
        }
        this.setParent(element);
        final ElementType removedElement = this.elements.set(index, element);
        if (removedElement != null) {
            removedElement.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(removedElement.getIDIndex());
        }
        this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
        ++this.modCount;
        return removedElement;
    }
    
    @Override
    public void add(final int index, final ElementType element) throws IllegalArgumentException {
        if (element == null || this.elements.contains(element)) {
            return;
        }
        this.setParent(element);
        this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
        ++this.modCount;
        this.elements.add(index, element);
    }
    
    @Override
    public ElementType remove(final int index) {
        final ElementType element = this.elements.remove(index);
        if (element != null) {
            element.releaseParentDOM(true);
            element.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
        }
        ++this.modCount;
        return element;
    }
    
    public boolean remove(final ElementType element) {
        boolean elementRemoved = false;
        elementRemoved = this.elements.remove(element);
        if (elementRemoved && element != null) {
            element.releaseParentDOM(true);
            element.setParent(null);
            this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
        }
        return elementRemoved;
    }
    
    protected void setParent(final ElementType element) throws IllegalArgumentException {
        final XMLObject elemParent = element.getParent();
        if (elemParent != null && elemParent != this.parent) {
            throw new IllegalArgumentException(element.getElementQName() + " is already the child of another XMLObject and may not be inserted in to this list");
        }
        element.setParent(this.parent);
        element.releaseParentDOM(true);
    }
}
