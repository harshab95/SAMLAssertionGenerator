// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.encryption.EncryptionProperty;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class EncryptionPropertiesImpl extends AbstractValidatingXMLObject implements EncryptionProperties
{
    private String id;
    private final XMLObjectChildrenList encryptionProperties;
    
    protected EncryptionPropertiesImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.encryptionProperties = new XMLObjectChildrenList(this);
    }
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String newID) {
        final String oldID = this.id;
        this.registerOwnID(oldID, this.id = this.prepareForAssignment(this.id, newID));
    }
    
    public List<EncryptionProperty> getEncryptionProperties() {
        return (List<EncryptionProperty>)this.encryptionProperties;
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.encryptionProperties);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
