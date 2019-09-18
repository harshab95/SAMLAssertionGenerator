// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class EncryptionPropertiesMarshaller extends AbstractXMLEncryptionMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final EncryptionProperties ep = (EncryptionProperties)xmlObject;
        if (ep.getID() != null) {
            domElement.setAttributeNS(null, "Id", ep.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
    }
}
