// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class KeyInfoBuilder extends AbstractXMLObjectBuilder<KeyInfo> implements XMLSignatureBuilder<KeyInfo>
{
    @Override
    public KeyInfo buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new KeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public KeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyInfo", "ds");
    }
}
