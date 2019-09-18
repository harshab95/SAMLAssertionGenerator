// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.encryption.EncryptedKey;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class EncryptedKeyMarshaller extends EncryptedTypeMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final EncryptedKey ek = (EncryptedKey)xmlObject;
        if (ek.getRecipient() != null) {
            domElement.setAttributeNS(null, "Recipient", ek.getRecipient());
        }
        super.marshallAttributes(xmlObject, domElement);
    }
}
