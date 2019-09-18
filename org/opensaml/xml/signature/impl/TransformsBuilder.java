// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Transforms;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class TransformsBuilder extends AbstractXMLObjectBuilder<Transforms> implements XMLSignatureBuilder<Transforms>
{
    @Override
    public Transforms buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new TransformsImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Transforms buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transforms", "ds");
    }
}
