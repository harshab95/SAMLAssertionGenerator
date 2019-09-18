// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509SerialNumberBuilder extends AbstractXMLObjectBuilder<X509SerialNumber> implements XMLSignatureBuilder<X509SerialNumber>
{
    @Override
    public X509SerialNumber buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509SerialNumberImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509SerialNumber buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
    }
}
