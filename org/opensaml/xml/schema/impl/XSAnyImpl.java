// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import java.util.Collections;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.util.AttributeMap;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class XSAnyImpl extends AbstractValidatingXMLObject implements XSAny
{
    private IndexedXMLObjectChildrenList<XMLObject> unknownXMLObjects;
    private AttributeMap unknownAttributes;
    private String textContent;
    
    protected XSAnyImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownXMLObjects = new IndexedXMLObjectChildrenList<XMLObject>(this);
        this.unknownAttributes = new AttributeMap(this);
    }
    
    public String getTextContent() {
        return this.textContent;
    }
    
    public void setTextContent(final String newContent) {
        this.textContent = this.prepareForAssignment(this.textContent, newContent);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return this.unknownXMLObjects;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.unknownXMLObjects.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList((List<? extends XMLObject>)this.unknownXMLObjects);
    }
    
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }
}
