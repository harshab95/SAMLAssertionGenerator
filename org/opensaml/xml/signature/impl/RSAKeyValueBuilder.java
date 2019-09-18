// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class RSAKeyValueBuilder extends AbstractXMLObjectBuilder<RSAKeyValue> implements XMLSignatureBuilder<RSAKeyValue>
{
    @Override
    public RSAKeyValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new RSAKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public RSAKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue", "ds");
    }
}
