// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KeyValueBuilder extends AbstractXMLObjectBuilder<KeyValue> implements XMLSignatureBuilder<KeyValue>
{
    @Override
    public KeyValue buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KeyValueImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyValue", "ds");
    }
}
