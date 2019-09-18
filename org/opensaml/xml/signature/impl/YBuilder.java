// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Y;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class YBuilder extends AbstractXMLObjectBuilder<Y> implements XMLSignatureBuilder<Y>
{
    @Override
    public Y buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new YImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Y buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Y", "ds");
    }
}
