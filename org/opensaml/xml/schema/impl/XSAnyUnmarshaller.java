// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import javax.xml.namespace.QName;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public class XSAnyUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
        final XSAny xsAny = (XSAny)parentXMLObject;
        xsAny.getUnknownXMLObjects().add(childXMLObject);
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final XSAny xsAny = (XSAny)xmlObject;
        final QName attribQName = XMLHelper.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        if (attribute.isId()) {
            xsAny.getUnknownAttributes().registerID(attribQName);
        }
        xsAny.getUnknownAttributes().put(attribQName, attribute.getValue());
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        final XSAny xsAny = (XSAny)xmlObject;
        xsAny.setTextContent(elementContent);
    }
}
