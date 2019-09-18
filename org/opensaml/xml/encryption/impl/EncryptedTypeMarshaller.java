// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.EncryptedType;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public abstract class EncryptedTypeMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final EncryptedType et = (EncryptedType)xmlObject;
        if (et.getID() != null) {
            domElement.setAttributeNS(null, "Id", et.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
        if (et.getType() != null) {
            domElement.setAttributeNS(null, "Type", et.getType());
        }
        if (et.getMimeType() != null) {
            domElement.setAttributeNS(null, "MimeType", et.getMimeType());
        }
        if (et.getEncoding() != null) {
            domElement.setAttributeNS(null, "Encoding", et.getEncoding());
        }
    }
}
