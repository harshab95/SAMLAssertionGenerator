// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class SignatureBuilder extends AbstractXMLObjectBuilder<SignatureImpl>
{
    public SignatureImpl buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
    }
    
    @Override
    public SignatureImpl buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new SignatureImpl(namespaceURI, localName, namespacePrefix);
    }
}
