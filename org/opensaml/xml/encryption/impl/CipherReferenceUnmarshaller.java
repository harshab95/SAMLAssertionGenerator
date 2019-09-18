// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.Transforms;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.encryption.CipherReference;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class CipherReferenceUnmarshaller extends AbstractXMLEncryptionUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final CipherReference cr = (CipherReference)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            cr.setURI(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final CipherReference cr = (CipherReference)parentXMLObject;
        if (childXMLObject instanceof Transforms) {
            cr.setTransforms((Transforms)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
