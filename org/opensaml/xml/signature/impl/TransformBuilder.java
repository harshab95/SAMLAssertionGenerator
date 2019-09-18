// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.Transform;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class TransformBuilder extends AbstractXMLObjectBuilder<Transform> implements XMLSignatureBuilder<Transform>
{
    @Override
    public Transform buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new TransformImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Transform buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
    }
}
