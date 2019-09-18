// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.EncryptionProperty;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class EncryptionPropertiesUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final EncryptionProperties ep = (EncryptionProperties)xmlObject;
        if (attribute.getLocalName().equals("Id")) {
            ep.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final EncryptionProperties ep = (EncryptionProperties)parentXMLObject;
        if (childXMLObject instanceof EncryptionProperty) {
            ep.getEncryptionProperties().add((EncryptionProperty)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
