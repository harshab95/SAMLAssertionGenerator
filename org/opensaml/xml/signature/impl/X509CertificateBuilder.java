// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509CertificateBuilder extends AbstractXMLObjectBuilder<X509Certificate> implements XMLSignatureBuilder<X509Certificate>
{
    @Override
    public X509Certificate buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509CertificateImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509Certificate buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Certificate", "ds");
    }
}
