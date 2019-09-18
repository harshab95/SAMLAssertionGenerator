// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509SKIBuilder extends AbstractXMLObjectBuilder<X509SKI> implements XMLSignatureBuilder<X509SKI>
{
    @Override
    public X509SKI buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509SKIImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509SKI buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SKI", "ds");
    }
}
