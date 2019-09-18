// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.X509SubjectName;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class X509SubjectNameBuilder extends AbstractXMLObjectBuilder<X509SubjectName> implements XMLSignatureBuilder<X509SubjectName>
{
    @Override
    public X509SubjectName buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new X509SubjectNameImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public X509SubjectName buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName", "ds");
    }
}
