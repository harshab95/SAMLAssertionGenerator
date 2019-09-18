// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509IssuerName;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509IssuerNameBuilder extends AbstractXMLObjectBuilder<X509IssuerName> implements XMLSignatureBuilder<X509IssuerName>
{
    @Override
    public X509IssuerName buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509IssuerNameImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509IssuerName buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName", "ds");
    }
}
