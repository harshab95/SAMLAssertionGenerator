// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.util.XMLConstants;
import org.opensaml.xml.Namespace;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.XMLObjectBuilder;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Text;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.Element;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.Configuration;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.XMLObjectBuilderFactory;
import javax.xml.namespace.QName;
import org.slf4j.Logger;

public abstract class AbstractXMLObjectUnmarshaller implements Unmarshaller
{
    private final Logger log;
    private QName targetQName;
    private XMLObjectBuilderFactory xmlObjectBuilderFactory;
    private UnmarshallerFactory unmarshallerFactory;
    
    protected AbstractXMLObjectUnmarshaller() {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLObjectUnmarshaller.class);
        this.xmlObjectBuilderFactory = Configuration.getBuilderFactory();
        this.unmarshallerFactory = Configuration.getUnmarshallerFactory();
    }
    
    @Deprecated
    protected AbstractXMLObjectUnmarshaller(final String targetNamespaceURI, final String targetLocalName) {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLObjectUnmarshaller.class);
        this.targetQName = XMLHelper.constructQName(targetNamespaceURI, targetLocalName, null);
        this.xmlObjectBuilderFactory = Configuration.getBuilderFactory();
        this.unmarshallerFactory = Configuration.getUnmarshallerFactory();
    }
    
    public XMLObject unmarshall(final Element domElement) throws UnmarshallingException {
        this.log.trace("Starting to unmarshall DOM element {}", (Object)XMLHelper.getNodeQName(domElement));
        this.checkElementIsTarget(domElement);
        final XMLObject xmlObject = this.buildXMLObject(domElement);
        this.log.trace("Unmarshalling attributes of DOM Element {}", (Object)XMLHelper.getNodeQName(domElement));
        final NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            final Node attribute = attributes.item(i);
            if (attribute.getNodeType() == 2) {
                this.unmarshallAttribute(xmlObject, (Attr)attribute);
            }
        }
        this.log.trace("Unmarshalling other child nodes of DOM Element {}", (Object)XMLHelper.getNodeQName(domElement));
        final NodeList childNodes = domElement.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); ++j) {
            final Node childNode = childNodes.item(j);
            if (childNode.getNodeType() == 2) {
                this.unmarshallAttribute(xmlObject, (Attr)childNode);
            }
            else if (childNode.getNodeType() == 1) {
                this.unmarshallChildElement(xmlObject, (Element)childNode);
            }
            else if (childNode.getNodeType() == 3) {
                this.unmarshallTextContent(xmlObject, (Text)childNode);
            }
        }
        xmlObject.setDOM(domElement);
        return xmlObject;
    }
    
    protected void checkElementIsTarget(final Element domElement) throws UnmarshallingException {
        final QName elementName = XMLHelper.getNodeQName(domElement);
        if (this.targetQName == null) {
            this.log.trace("Targeted QName checking is not available for this unmarshaller, DOM Element {} was not verified", (Object)elementName);
            return;
        }
        this.log.trace("Checking that {} meets target criteria.", (Object)elementName);
        final QName type = XMLHelper.getXSIType(domElement);
        if (type != null && type.equals(this.targetQName)) {
            this.log.trace("{} schema type matches target.", (Object)elementName);
            return;
        }
        if (elementName.equals(this.targetQName)) {
            this.log.trace("{} element name matches target.", (Object)elementName);
            return;
        }
        final String errorMsg = "This unmarshaller only operates on " + this.targetQName + " elements not " + elementName;
        this.log.error(errorMsg);
        throw new UnmarshallingException(errorMsg);
    }
    
    protected XMLObject buildXMLObject(final Element domElement) throws UnmarshallingException {
        this.log.trace("Building XMLObject for {}", (Object)XMLHelper.getNodeQName(domElement));
        XMLObjectBuilder xmlObjectBuilder = this.xmlObjectBuilderFactory.getBuilder(domElement);
        if (xmlObjectBuilder == null) {
            xmlObjectBuilder = this.xmlObjectBuilderFactory.getBuilder(Configuration.getDefaultProviderQName());
            if (xmlObjectBuilder == null) {
                final String errorMsg = "Unable to located builder for " + XMLHelper.getNodeQName(domElement);
                this.log.error(errorMsg);
                throw new UnmarshallingException(errorMsg);
            }
            this.log.trace("No builder was registered for {} but the default builder {} was available, using it.", (Object)XMLHelper.getNodeQName(domElement), (Object)xmlObjectBuilder.getClass().getName());
        }
        return xmlObjectBuilder.buildObject(domElement);
    }
    
    protected void unmarshallAttribute(final XMLObject xmlObject, final Attr attribute) throws UnmarshallingException {
        final QName attribName = XMLHelper.getNodeQName(attribute);
        this.log.trace("Pre-processing attribute {}", (Object)attribName);
        final String attributeNamespace = DatatypeHelper.safeTrimOrNullString(attribute.getNamespaceURI());
        if (DatatypeHelper.safeEquals(attributeNamespace, "http://www.w3.org/2000/xmlns/")) {
            this.unmarshallNamespaceAttribute(xmlObject, attribute);
        }
        else if (DatatypeHelper.safeEquals(attributeNamespace, "http://www.w3.org/2001/XMLSchema-instance")) {
            this.unmarshallSchemaInstanceAttributes(xmlObject, attribute);
        }
        else {
            this.log.trace("Attribute {} is neither a schema type nor namespace, calling processAttribute()", (Object)XMLHelper.getNodeQName(attribute));
            final String attributeNSURI = attribute.getNamespaceURI();
            if (attributeNSURI != null) {
                String attributeNSPrefix = attribute.lookupPrefix(attributeNSURI);
                if (attributeNSPrefix == null && "http://www.w3.org/XML/1998/namespace".equals(attributeNSURI)) {
                    attributeNSPrefix = "xml";
                }
                xmlObject.getNamespaceManager().registerAttributeName(attribName);
            }
            this.checkIDAttribute(attribute);
            this.processAttribute(xmlObject, attribute);
        }
    }
    
    protected void unmarshallNamespaceAttribute(final XMLObject xmlObject, final Attr attribute) {
        this.log.trace("{} is a namespace declaration, adding it to the list of namespaces on the XMLObject", (Object)XMLHelper.getNodeQName(attribute));
        Namespace namespace;
        if (DatatypeHelper.safeEquals(attribute.getLocalName(), "xmlns")) {
            namespace = new Namespace(attribute.getValue(), null);
        }
        else {
            namespace = new Namespace(attribute.getValue(), attribute.getLocalName());
        }
        namespace.setAlwaysDeclare(true);
        xmlObject.getNamespaceManager().registerNamespaceDeclaration(namespace);
    }
    
    protected void unmarshallSchemaInstanceAttributes(final XMLObject xmlObject, final Attr attribute) {
        final QName attribName = XMLHelper.getNodeQName(attribute);
        if (XMLConstants.XSI_TYPE_ATTRIB_NAME.equals(attribName)) {
            this.log.trace("Saw XMLObject {} with an xsi:type of: {}", (Object)xmlObject.getElementQName(), (Object)attribute.getValue());
        }
        else if (XMLConstants.XSI_SCHEMA_LOCATION_ATTRIB_NAME.equals(attribName)) {
            this.log.trace("Saw XMLObject {} with an xsi:schemaLocation of: {}", (Object)xmlObject.getElementQName(), (Object)attribute.getValue());
            xmlObject.setSchemaLocation(attribute.getValue());
        }
        else if (XMLConstants.XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME.equals(attribName)) {
            this.log.trace("Saw XMLObject {} with an xsi:noNamespaceSchemaLocation of: {}", (Object)xmlObject.getElementQName(), (Object)attribute.getValue());
            xmlObject.setNoNamespaceSchemaLocation(attribute.getValue());
        }
        else if (XMLConstants.XSI_NIL_ATTRIB_NAME.equals(attribName)) {
            this.log.trace("Saw XMLObject {} with an xsi:nil of: {}", (Object)xmlObject.getElementQName(), (Object)attribute.getValue());
            xmlObject.setNil(XSBooleanValue.valueOf(attribute.getValue()));
        }
    }
    
    protected void checkIDAttribute(final Attr attribute) {
        final QName attribName = XMLHelper.getNodeQName(attribute);
        if (Configuration.isIDAttribute(attribName) && !attribute.isId()) {
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
    }
    
    protected void unmarshallChildElement(final XMLObject xmlObject, final Element childElement) throws UnmarshallingException {
        this.log.trace("Unmarshalling child elements of XMLObject {}", (Object)xmlObject.getElementQName());
        Unmarshaller unmarshaller = this.unmarshallerFactory.getUnmarshaller(childElement);
        if (unmarshaller == null) {
            unmarshaller = this.unmarshallerFactory.getUnmarshaller(Configuration.getDefaultProviderQName());
            if (unmarshaller == null) {
                final String errorMsg = "No unmarshaller available for " + XMLHelper.getNodeQName(childElement) + ", child of " + xmlObject.getElementQName();
                this.log.error(errorMsg);
                throw new UnmarshallingException(errorMsg);
            }
            this.log.trace("No unmarshaller was registered for {}, child of {}. Using default unmarshaller.", (Object)XMLHelper.getNodeQName(childElement), (Object)xmlObject.getElementQName());
        }
        this.log.trace("Unmarshalling child element {}with unmarshaller {}", (Object)XMLHelper.getNodeQName(childElement), (Object)unmarshaller.getClass().getName());
        this.processChildElement(xmlObject, unmarshaller.unmarshall(childElement));
    }
    
    protected void unmarshallTextContent(final XMLObject xmlObject, final Text content) throws UnmarshallingException {
        final String textContent = DatatypeHelper.safeTrimOrNullString(content.getWholeText());
        if (textContent != null) {
            this.processElementContent(xmlObject, textContent);
        }
    }
    
    protected abstract void processChildElement(final XMLObject p0, final XMLObject p1) throws UnmarshallingException;
    
    protected abstract void processAttribute(final XMLObject p0, final Attr p1) throws UnmarshallingException;
    
    protected abstract void processElementContent(final XMLObject p0, final String p1);
}
