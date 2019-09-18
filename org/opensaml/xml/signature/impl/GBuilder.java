// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.G;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class GBuilder extends AbstractXMLObjectBuilder<G> implements XMLSignatureBuilder<G>
{
    @Override
    public G buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new GImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public G buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "G", "ds");
    }
}
