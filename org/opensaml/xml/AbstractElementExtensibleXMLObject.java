// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.List;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public abstract class AbstractElementExtensibleXMLObject extends AbstractValidatingXMLObject implements ElementExtensibleXMLObject
{
    private IndexedXMLObjectChildrenList<XMLObject> anyXMLObjects;
    
    public AbstractElementExtensibleXMLObject(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.anyXMLObjects = new IndexedXMLObjectChildrenList<XMLObject>(this);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList((List<? extends XMLObject>)this.anyXMLObjects);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return this.anyXMLObjects;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.anyXMLObjects.subList(typeOrName);
    }
}
