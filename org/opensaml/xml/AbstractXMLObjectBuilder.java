// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;

public abstract class AbstractXMLObjectBuilder<XMLObjectType extends XMLObject> implements XMLObjectBuilder<XMLObjectType>
{
    public XMLObjectType buildObject(final QName objectName) {
        return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix());
    }
    
    public XMLObjectType buildObject(final QName objectName, final QName schemaType) {
        return this.buildObject(objectName.getNamespaceURI(), objectName.getLocalPart(), objectName.getPrefix(), schemaType);
    }
    
    public abstract XMLObjectType buildObject(final String p0, final String p1, final String p2);
    
    public XMLObjectType buildObject(final String namespaceURI, final String localName, final String namespacePrefix, final QName schemaType) {
        final XMLObjectType xmlObject = this.buildObject(namespaceURI, localName, namespacePrefix);
        ((AbstractXMLObject)xmlObject).setSchemaType(schemaType);
        return xmlObject;
    }
    
    public XMLObjectType buildObject(final Element element) {
        final String localName = element.getLocalName();
        final String nsURI = element.getNamespaceURI();
        final String nsPrefix = element.getPrefix();
        final QName schemaType = XMLHelper.getXSIType(element);
        final XMLObjectType xmlObject = this.buildObject(nsURI, localName, nsPrefix, schemaType);
        return xmlObject;
    }
}
