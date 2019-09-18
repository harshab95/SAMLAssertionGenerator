// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import java.util.Map;
import net.jcip.annotations.NotThreadSafe;
import org.opensaml.xml.XMLObject;

@NotThreadSafe
public class IndexedXMLObjectChildrenList<ElementType extends XMLObject> extends XMLObjectChildrenList<ElementType>
{
    private Map<QName, List<ElementType>> objectIndex;
    
    public IndexedXMLObjectChildrenList(final XMLObject parent) {
        super(parent);
        this.objectIndex = new LazyMap<QName, List<ElementType>>();
    }
    
    public IndexedXMLObjectChildrenList(final XMLObject parent, final Collection<ElementType> col) {
        super(parent);
        this.objectIndex = new LazyMap<QName, List<ElementType>>();
        this.addAll((Collection<? extends ElementType>)col);
    }
    
    @Override
    public void add(final int index, final ElementType element) {
        super.add(index, element);
        this.indexElement(element);
    }
    
    @Override
    public void clear() {
        super.clear();
        this.objectIndex.clear();
    }
    
    public List<ElementType> get(final QName typeOrName) {
        return this.objectIndex.get(typeOrName);
    }
    
    protected void indexElement(final ElementType element) {
        if (element == null) {
            return;
        }
        final QName type = element.getSchemaType();
        if (type != null) {
            this.indexElement(type, element);
        }
        this.indexElement(element.getElementQName(), element);
    }
    
    protected void indexElement(final QName index, final ElementType element) {
        List<ElementType> objects = this.objectIndex.get(index);
        if (objects == null) {
            objects = new LazyList<ElementType>();
            this.objectIndex.put(index, objects);
        }
        objects.add(element);
    }
    
    @Override
    public boolean remove(final ElementType element) {
        boolean elementRemoved = false;
        elementRemoved = super.remove(element);
        if (elementRemoved) {
            this.removeElementFromIndex(element);
        }
        return elementRemoved;
    }
    
    @Override
    public ElementType remove(final int index) {
        final ElementType returnValue = super.remove(index);
        this.removeElementFromIndex(returnValue);
        return returnValue;
    }
    
    protected void removeElementFromIndex(final ElementType element) {
        if (element == null) {
            return;
        }
        final QName type = element.getSchemaType();
        if (type != null) {
            this.removeElementFromIndex(type, element);
        }
        this.removeElementFromIndex(element.getElementQName(), element);
    }
    
    protected void removeElementFromIndex(final QName index, final ElementType element) {
        final List<ElementType> objects = this.objectIndex.get(index);
        if (objects != null) {
            objects.remove(element);
        }
        if (objects.size() == 0) {
            this.objectIndex.remove(index);
        }
    }
    
    @Override
    public ElementType set(final int index, final ElementType element) {
        final ElementType returnValue = super.set(index, element);
        this.removeElementFromIndex(returnValue);
        this.indexElement(element);
        return returnValue;
    }
    
    public List<? extends ElementType> subList(final QName index) {
        if (!this.objectIndex.containsKey(index)) {
            this.objectIndex.put(index, new LazyList<ElementType>());
        }
        return (List<? extends ElementType>)new ListView<ElementType>((IndexedXMLObjectChildrenList<? extends ElementType>)this, index);
    }
}
