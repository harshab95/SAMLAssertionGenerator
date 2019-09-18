// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509DataBuilder extends AbstractXMLObjectBuilder<X509Data> implements XMLSignatureBuilder<X509Data>
{
    @Override
    public X509Data buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509DataImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509Data buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
    }
}
