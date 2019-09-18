// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509IssuerSerial;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509IssuerSerialBuilder extends AbstractXMLObjectBuilder<X509IssuerSerial> implements XMLSignatureBuilder<X509IssuerSerial>
{
    @Override
    public X509IssuerSerial buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509IssuerSerialImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509IssuerSerial buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial", "ds");
    }
}
