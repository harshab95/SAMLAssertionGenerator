// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.DigestMethod;
import org.w3c.dom.Attr;
import org.opensaml.xml.XMLObject;

public class DigestMethodUnmarshaller extends AbstractXMLSignatureUnmarshaller
{
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final DigestMethod dm = (DigestMethod)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            dm.setAlgorithm(attribute.getValue());
        }
        else {
            super.processAttribute(xmlObject, attribute);
        }
    }
    
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final DigestMethod dm = (DigestMethod)parentXMLObject;
        dm.getUnknownXMLObjects().add(childXMLObject);
    }
}
