// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.PGPKeyID;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PGPKeyIDBuilder extends AbstractXMLObjectBuilder<PGPKeyID> implements XMLSignatureBuilder<PGPKeyID>
{
    @Override
    public PGPKeyID buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PGPKeyIDImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public PGPKeyID buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID", "ds");
    }
}
