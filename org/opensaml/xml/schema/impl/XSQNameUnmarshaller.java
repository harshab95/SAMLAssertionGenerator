// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.schema.XSQName;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Text;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;

public class XSQNameUnmarshaller extends AbstractXMLObjectUnmarshaller
{
    @Override
    protected void processChildElement(final XMLObject parentXMLObject, final XMLObject childXMLObject) throws UnmarshallingException {
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
    }
    
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
    }
    
    @Override
    protected void unmarshallTextContent(final XMLObject xmlObject, final Text content) throws UnmarshallingException {
        final String textContent = DatatypeHelper.safeTrimOrNullString(content.getWholeText());
        if (textContent != null) {
            final XSQName qname = (XSQName)xmlObject;
            qname.setValue(XMLHelper.constructQName(textContent, XMLHelper.getElementAncestor(content)));
        }
    }
}
