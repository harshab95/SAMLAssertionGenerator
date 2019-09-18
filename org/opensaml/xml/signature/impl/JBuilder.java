// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.J;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class JBuilder extends AbstractXMLObjectBuilder<J> implements XMLSignatureBuilder<J>
{
    @Override
    public J buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new JImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public J buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "J", "ds");
    }
}
