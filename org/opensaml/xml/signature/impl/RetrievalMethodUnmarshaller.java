// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.Transforms;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.RetrievalMethod;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class RetrievalMethodUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final RetrievalMethod rm = (RetrievalMethod)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            rm.setURI(attribute.getValue());
        }
        else if (attribute.getLocalName().equals("Type")) {
            rm.setType(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final RetrievalMethod rm = (RetrievalMethod)parentXMLObject;
        if (childXMLObject instanceof Transforms) {
            rm.setTransforms((Transforms)childXMLObject);
        }
        else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}
