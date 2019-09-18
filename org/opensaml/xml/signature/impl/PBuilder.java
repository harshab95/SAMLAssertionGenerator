// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.P;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PBuilder extends AbstractXMLObjectBuilder<P> implements XMLSignatureBuilder<P>
{
    @Override
    public P buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public P buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "P", "ds");
    }
}
