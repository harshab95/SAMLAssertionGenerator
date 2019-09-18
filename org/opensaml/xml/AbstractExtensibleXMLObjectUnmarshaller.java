// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.io.UnmarshallingException;
import javax.xml.namespace.QName;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Attr;

public abstract class AbstractExtensibleXMLObjectUnmarshaller extends AbstractElementExtensibleXMLObjectUnmarshaller
{
    public AbstractExtensibleXMLObjectUnmarshaller() {
    }
    
    @Deprecated
    public AbstractExtensibleXMLObjectUnmarshaller(final String targetNamespaceURI, final String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }
    
    @Override
    protected void processAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
        final QName attribQName = XMLHelper.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        if (attribute.isId()) {
            anyAttribute.getUnknownAttributes().registerID(attribQName);
        }
        anyAttribute.getUnknownAttributes().put(attribQName, attribute.getValue());
    }
}
