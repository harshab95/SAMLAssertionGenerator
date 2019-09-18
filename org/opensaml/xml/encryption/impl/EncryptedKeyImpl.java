// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.encryption.CarriedKeyName;
import org.opensaml.xml.encryption.EncryptedKey;

public class EncryptedKeyImpl extends EncryptedTypeImpl implements EncryptedKey
{
    private String recipient;
    private CarriedKeyName carriedKeyName;
    private ReferenceList referenceList;
    
    protected EncryptedKeyImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getRecipient() {
        return this.recipient;
    }
    
    public void setRecipient(final String newRecipient) {
        this.recipient = this.prepareForAssignment(this.recipient, newRecipient);
    }
    
    public ReferenceList getReferenceList() {
        return this.referenceList;
    }
    
    public void setReferenceList(final ReferenceList newReferenceList) {
        this.referenceList = this.prepareForAssignment(this.referenceList, newReferenceList);
    }
    
    public CarriedKeyName getCarriedKeyName() {
        return this.carriedKeyName;
    }
    
    public void setCarriedKeyName(final CarriedKeyName newCarriedKeyName) {
        this.carriedKeyName = this.prepareForAssignment(this.carriedKeyName, newCarriedKeyName);
    }
    
    @Override
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.referenceList != null) {
            children.add(this.referenceList);
        }
        if (this.carriedKeyName != null) {
            children.add(this.carriedKeyName);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
