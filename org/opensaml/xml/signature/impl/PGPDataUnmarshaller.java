// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.PGPKeyPacket;
import org.opensaml.xml.signature.PGPKeyID;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.XMLObject;

public class PGPDataUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final PGPData pgpData = (PGPData)parentXMLObject;
        if (childXMLObject instanceof PGPKeyID) {
            pgpData.setPGPKeyID((PGPKeyID)childXMLObject);
        }
        else if (childXMLObject instanceof PGPKeyPacket) {
            pgpData.setPGPKeyPacket((PGPKeyPacket)childXMLObject);
        }
        else {
            pgpData.getUnknownXMLObjects().add(childXMLObject);
        }
    }
}
