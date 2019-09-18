// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.encryption.ReferenceType;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class ReferenceTypeImpl extends AbstractValidatingXMLObject implements ReferenceType
{
    private String uri;
    private final IndexedXMLObjectChildrenList<XMLObject> xmlChildren;
    
    protected ReferenceTypeImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.xmlChildren = new IndexedXMLObjectChildrenList<XMLObject>(this);
    }
    
    public String getURI() {
        return this.uri;
    }
    
    public void setURI(final String newURI) {
        this.uri = this.prepareForAssignment(this.uri, newURI);
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return this.xmlChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.xmlChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.xmlChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
