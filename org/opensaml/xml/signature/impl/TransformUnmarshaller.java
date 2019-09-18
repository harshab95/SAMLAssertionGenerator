// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Transform;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class TransformUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final Transform transform = (Transform)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            transform.setAlgorithm(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final Transform transform = (Transform)parentXMLObject;
        transform.getAllChildren().add(childXMLObject);
    }
}
