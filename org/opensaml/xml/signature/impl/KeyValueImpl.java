// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class KeyValueImpl extends AbstractValidatingXMLObject implements KeyValue
{
    private DSAKeyValue dsaKeyValue;
    private RSAKeyValue rsaKeyValue;
    private XMLObject unknownXMLObject;
    
    protected KeyValueImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public DSAKeyValue getDSAKeyValue() {
        return this.dsaKeyValue;
    }
    
    public void setDSAKeyValue(final DSAKeyValue newDSAKeyValue) {
        this.dsaKeyValue = this.prepareForAssignment(this.dsaKeyValue, newDSAKeyValue);
    }
    
    public RSAKeyValue getRSAKeyValue() {
        return this.rsaKeyValue;
    }
    
    public void setRSAKeyValue(final RSAKeyValue newRSAKeyValue) {
        this.rsaKeyValue = this.prepareForAssignment(this.rsaKeyValue, newRSAKeyValue);
    }
    
    public XMLObject getUnknownXMLObject() {
        return this.unknownXMLObject;
    }
    
    public void setUnknownXMLObject(final XMLObject newXMLObject) {
        this.unknownXMLObject = this.prepareForAssignment(this.unknownXMLObject, newXMLObject);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.dsaKeyValue != null) {
            children.add(this.dsaKeyValue);
        }
        if (this.rsaKeyValue != null) {
            children.add(this.rsaKeyValue);
        }
        if (this.unknownXMLObject != null) {
            children.add(this.unknownXMLObject);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
