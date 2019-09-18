// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.signature.KeyInfoType;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;

public class KeyInfoTypeMarshaller extends AbstractXMLSignatureMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final KeyInfoType keyInfo = (KeyInfoType)xmlObject;
        if (keyInfo.getID() != null) {
            domElement.setAttributeNS(null, "Id", keyInfo.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
    }
}
