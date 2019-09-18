// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.Exponent;
import org.opensaml.xml.signature.Modulus;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class RSAKeyValueImpl extends AbstractValidatingXMLObject implements RSAKeyValue
{
    private Modulus modulus;
    private Exponent exponent;
    
    protected RSAKeyValueImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public Modulus getModulus() {
        return this.modulus;
    }
    
    public void setModulus(final Modulus newModulus) {
        this.modulus = this.prepareForAssignment(this.modulus, newModulus);
    }
    
    public Exponent getExponent() {
        return this.exponent;
    }
    
    public void setExponent(final Exponent newExponent) {
        this.exponent = this.prepareForAssignment(this.exponent, newExponent);
    }
    
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.modulus != null) {
            children.add(this.modulus);
        }
        if (this.exponent != null) {
            children.add(this.exponent);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList((List<? extends XMLObject>)children);
    }
}
