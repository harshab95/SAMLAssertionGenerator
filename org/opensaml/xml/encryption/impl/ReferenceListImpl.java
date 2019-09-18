// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.encryption.KeyReference;
import org.opensaml.xml.encryption.DataReference;
import org.opensaml.xml.encryption.ReferenceType;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class ReferenceListImpl extends AbstractValidatingXMLObject implements ReferenceList
{
    private final IndexedXMLObjectChildrenList indexedChildren;
    
    protected ReferenceListImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.indexedChildren = new IndexedXMLObjectChildrenList(this);
    }
    
    public List<ReferenceType> getReferences() {
        return (List<ReferenceType>)this.indexedChildren;
    }
    
    public List<DataReference> getDataReferences() {
        return (List<DataReference>)this.indexedChildren.subList(DataReference.DEFAULT_ELEMENT_NAME);
    }
    
    public List<KeyReference> getKeyReferences() {
        return (List<KeyReference>)this.indexedChildren.subList(KeyReference.DEFAULT_ELEMENT_NAME);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.indexedChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
