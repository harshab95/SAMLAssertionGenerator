// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509CRL;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509CRLBuilder extends AbstractXMLObjectBuilder<X509CRL> implements XMLSignatureBuilder<X509CRL>
{
    @Override
    public X509CRL buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509CRLImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509CRL buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509CRL", "ds");
    }
}
