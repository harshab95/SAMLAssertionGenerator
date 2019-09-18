// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.PGPKeyPacket;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class PGPKeyPacketImpl extends XSBase64BinaryImpl implements PGPKeyPacket
{
    protected PGPKeyPacketImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
