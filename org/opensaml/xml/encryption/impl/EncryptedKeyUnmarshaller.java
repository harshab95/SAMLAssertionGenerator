// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.CarriedKeyName;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.EncryptedKey;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class EncryptedKeyUnmarshaller extends EncryptedTypeUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final EncryptedKey ek = (EncryptedKey)xmlObject;
        if (attribute.getLocalName().equals("Recipient")) {
            ek.setRecipient(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final EncryptedKey ek = (EncryptedKey)parentXMLObject;
        if (childXMLObject instanceof ReferenceList) {
            ek.setReferenceList((ReferenceList)childXMLObject);
        }
        else if (childXMLObject instanceof CarriedKeyName) {
            ek.setCarriedKeyName((CarriedKeyName)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
