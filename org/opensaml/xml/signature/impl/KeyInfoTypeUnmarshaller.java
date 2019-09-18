// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.KeyInfoType;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class KeyInfoTypeUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final KeyInfoType keyInfo = (KeyInfoType)xmlObject;
        if (attribute.getLocalName().equals("Id")) {
            keyInfo.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final KeyInfoType keyInfo = (KeyInfoType)parentXMLObject;
        keyInfo.getXMLObjects().add(childXMLObject);
    }
}
