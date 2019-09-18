// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.KeyName;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KeyNameBuilder extends AbstractXMLObjectBuilder<KeyName> implements XMLSignatureBuilder<KeyName>
{
    @Override
    public KeyName buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KeyNameImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KeyName buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyName", "ds");
    }
}
