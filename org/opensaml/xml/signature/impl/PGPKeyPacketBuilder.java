// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.signature.PGPKeyPacket;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class PGPKeyPacketBuilder extends AbstractXMLObjectBuilder<PGPKeyPacket> implements XMLSignatureBuilder<PGPKeyPacket>
{
    @Override
    public PGPKeyPacket buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new PGPKeyPacketImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public PGPKeyPacket buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket", "ds");
    }
}
