// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import java.util.Iterator;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.util.XMLHelper;
import javax.xml.namespace.QName;
import java.util.Map;
import org.opensaml.xml.schema.XSAny;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectMarshaller;

public class XSAnyMarshaller extends AbstractXMLObjectMarshaller
{
    @Override
    protected void marshallAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final XSAny xsAny = (XSAny)xmlObject;
        for (final Map.Entry<QName, String> entry : xsAny.getUnknownAttributes().entrySet()) {
            final Attr attribute = XMLHelper.constructAttribute(domElement.getOwnerDocument(), entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (Configuration.isIDAttribute(entry.getKey()) || xsAny.getUnknownAttributes().isIDAttribute(entry.getKey())) {
                attribute.getOwnerElement().setIdAttributeNode(attribute, true);
            }
        }
    }
    
    @Override
    protected void marshallElementContent(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        final XSAny xsAny = (XSAny)xmlObject;
        if (xsAny.getTextContent() != null) {
            XMLHelper.appendTextContent(domElement, xsAny.getTextContent());
        }
    }
}
