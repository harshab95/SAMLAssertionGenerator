// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Exponent;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class ExponentBuilder extends AbstractXMLObjectBuilder<Exponent> implements XMLSignatureBuilder<Exponent>
{
    @Override
    public Exponent buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new ExponentImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Exponent buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Exponent", "ds");
    }
}
