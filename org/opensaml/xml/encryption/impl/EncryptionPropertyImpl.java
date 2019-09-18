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
import org.opensaml.xml.util.AttributeMap;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xml.encryption.EncryptionProperty;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class EncryptionPropertyImpl extends AbstractValidatingXMLObject implements EncryptionProperty
{
    private String target;
    private String id;
    private final IndexedXMLObjectChildrenList unknownChildren;
    private final AttributeMap unknownAttributes;
    
    protected EncryptionPropertyImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.unknownChildren = new IndexedXMLObjectChildrenList(this);
        this.unknownAttributes = new AttributeMap(this);
    }
    
    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(final String newTarget) {
        this.target = this.prepareForAssignment(this.target, newTarget);
    }
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String newID) {
        final String oldID = this.id;
        this.registerOwnID(oldID, this.id = this.prepareForAssignment(this.id, newID));
    }
    
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }
    
    public List<XMLObject> getUnknownXMLObjects() {
        return (List<XMLObject>)this.unknownChildren;
    }
    
    public List<XMLObject> getUnknownXMLObjects(final QName typeOrName) {
        return (List<XMLObject>)this.unknownChildren.subList(typeOrName);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.unknownChildren);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
