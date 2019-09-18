// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.ReferenceType;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class ReferenceTypeUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final ReferenceType rt = (ReferenceType)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            rt.setURI(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final ReferenceType rt = (ReferenceType)parentXMLObject;
        rt.getUnknownXMLObjects().add(childXMLObject);
    }
}
