// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Q;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class QBuilder extends AbstractXMLObjectBuilder<Q> implements XMLSignatureBuilder<Q>
{
    @Override
    public Q buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new QImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Q buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Q", "ds");
    }
}
