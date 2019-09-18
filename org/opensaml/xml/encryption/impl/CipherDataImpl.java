// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.encryption.CipherReference;
import org.opensaml.xml.encryption.CipherValue;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class CipherDataImpl extends AbstractValidatingXMLObject implements CipherData
{
    private CipherValue cipherValue;
    private CipherReference cipherReference;
    
    protected CipherDataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public CipherValue getCipherValue() {
        return this.cipherValue;
    }
    
    public void setCipherValue(final CipherValue newCipherValue) {
        this.cipherValue = this.prepareForAssignment(this.cipherValue, newCipherValue);
    }
    
    public CipherReference getCipherReference() {
        return this.cipherReference;
    }
    
    public void setCipherReference(final CipherReference newCipherReference) {
        this.cipherReference = this.prepareForAssignment(this.cipherReference, newCipherReference);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.cipherValue != null) {
            children.add(this.cipherValue);
        }
        if (this.cipherReference != null) {
            children.add(this.cipherReference);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
