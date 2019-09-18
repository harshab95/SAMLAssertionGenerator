// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.opensaml.xml.encryption.EncryptedType;
import org.opensaml.xml.XMLObject;

public abstract class EncryptedTypeUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final EncryptedType et = (EncryptedType)parentXMLObject;
        if (childXMLObject instanceof EncryptionMethod) {
            et.setEncryptionMethod((EncryptionMethod)childXMLObject);
        }
        else if (childXMLObject instanceof KeyInfo) {
            et.setKeyInfo((KeyInfo)childXMLObject);
        }
        else if (childXMLObject instanceof CipherData) {
            et.setCipherData((CipherData)childXMLObject);
        }
        else if (childXMLObject instanceof EncryptionProperties) {
            et.setEncryptionProperties((EncryptionProperties)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final EncryptedType et = (EncryptedType)xmlObject;
        if (attribute.getLocalName().equals("Id")) {
            et.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
        else if (attribute.getLocalName().equals("Type")) {
            et.setType(attribute.getValue());
        }
        else if (attribute.getLocalName().equals("MimeType")) {
            et.setMimeType(attribute.getValue());
        }
        else if (attribute.getLocalName().equals("Encoding")) {
            et.setEncoding(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
}
