// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import org.opensaml.xml.parse.XMLParserException;
import java.util.Set;
import org.opensaml.xml.Namespace;
import java.util.Iterator;
import java.util.List;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.Configuration;
import org.slf4j.LoggerFactory;
import javax.xml.namespace.QName;
import org.slf4j.Logger;

public abstract class AbstractXMLObjectMarshaller implements Marshaller
{
    private final Logger log;
    private QName targetQName;
    private MarshallerFactory marshallerFactory;
    
    protected AbstractXMLObjectMarshaller() {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLObjectMarshaller.class);
        this.marshallerFactory = Configuration.getMarshallerFactory();
    }
    
    @Deprecated
    protected AbstractXMLObjectMarshaller(final String targetNamespaceURI, final String targetLocalName) {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLObjectMarshaller.class);
        this.targetQName = XMLHelper.constructQName(targetNamespaceURI, targetLocalName, null);
        this.marshallerFactory = Configuration.getMarshallerFactory();
    }
    
    public Element marshall(final XMLObject xmlObject) throws MarshallingException {
        try {
            final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            return this.marshall(xmlObject, document);
        }
        catch (ParserConfigurationException e) {
            throw new MarshallingException("Unable to create Document to place marshalled elements in", e);
        }
    }
    
    public Element marshall(final XMLObject xmlObject, final Document document) throws MarshallingException {
        this.log.trace("Starting to marshall {}", (Object)xmlObject.getElementQName());
        if (document == null) {
            throw new MarshallingException("Given document may not be null");
        }
        this.checkXMLObjectIsTarget(xmlObject);
        this.log.trace("Checking if {} contains a cached DOM representation", (Object)xmlObject.getElementQName());
        Element domElement = xmlObject.getDOM();
        if (domElement != null) {
            this.prepareForAdoption(xmlObject);
            if (domElement.getOwnerDocument() != document) {
                this.log.trace("Adopting DOM of XMLObject into given Document");
                XMLHelper.adoptElement(domElement, document);
            }
            this.log.trace("Setting DOM of XMLObject as document element of given Document");
            this.setDocumentElement(document, domElement);
            return domElement;
        }
        this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", (Object)xmlObject.getElementQName());
        domElement = XMLHelper.constructElement(document, xmlObject.getElementQName());
        this.log.trace("Setting created element as document root");
        this.setDocumentElement(document, domElement);
        domElement = this.marshallInto(xmlObject, domElement);
        this.log.trace("Setting created element to DOM cache for XMLObject {}", (Object)xmlObject.getElementQName());
        xmlObject.setDOM(domElement);
        xmlObject.releaseParentDOM(true);
        return domElement;
    }
    
    public Element marshall(final XMLObject xmlObject, final Element parentElement) throws MarshallingException {
        this.log.trace("Starting to marshall {} as child of {}", (Object)xmlObject.getElementQName(), (Object)XMLHelper.getNodeQName(parentElement));
        if (parentElement == null) {
            throw new MarshallingException("Given parent element is null");
        }
        this.checkXMLObjectIsTarget(xmlObject);
        this.log.trace("Checking if {} contains a cached DOM representation", (Object)xmlObject.getElementQName());
        Element domElement = xmlObject.getDOM();
        if (domElement != null) {
            this.log.trace("{} contains a cached DOM representation", (Object)xmlObject.getElementQName());
            this.prepareForAdoption(xmlObject);
            this.log.trace("Appending DOM of XMLObject {} as child of parent element {}", (Object)xmlObject.getElementQName(), (Object)XMLHelper.getNodeQName(parentElement));
            XMLHelper.appendChildElement(parentElement, domElement);
            return domElement;
        }
        this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", (Object)xmlObject.getElementQName());
        final Document owningDocument = parentElement.getOwnerDocument();
        domElement = XMLHelper.constructElement(owningDocument, xmlObject.getElementQName());
        this.log.trace("Appending newly created element to given parent element");
        XMLHelper.appendChildElement(parentElement, domElement);
        domElement = this.marshallInto(xmlObject, domElement);
        this.log.trace("Setting created element to DOM cache for XMLObject {}", (Object)xmlObject.getElementQName());
        xmlObject.setDOM(domElement);
        xmlObject.releaseParentDOM(true);
        return domElement;
    }
    
    protected void setDocumentElement(final Document document, final Element element) {
        final Element documentRoot = document.getDocumentElement();
        if (documentRoot != null) {
            document.replaceChild(element, documentRoot);
        }
        else {
            document.appendChild(element);
        }
    }
    
    protected Element marshallInto(final XMLObject xmlObject, final Element targetElement) throws MarshallingException {
        this.log.trace("Setting namespace prefix for {} for XMLObject {}", (Object)xmlObject.getElementQName().getPrefix(), (Object)xmlObject.getElementQName());
        this.marshallNamespacePrefix(xmlObject, targetElement);
        this.marshallSchemaInstanceAttributes(xmlObject, targetElement);
        this.marshallNamespaces(xmlObject, targetElement);
        this.marshallAttributes(xmlObject, targetElement);
        this.marshallChildElements(xmlObject, targetElement);
        this.marshallElementContent(xmlObject, targetElement);
        return targetElement;
    }
    
    protected void checkXMLObjectIsTarget(final XMLObject xmlObject) throws MarshallingException {
        if (this.targetQName == null) {
            this.log.trace("Targeted QName checking is not available for this marshaller, XMLObject {} was not verified", (Object)xmlObject.getElementQName());
            return;
        }
        this.log.trace("Checking that {} meets target criteria", (Object)xmlObject.getElementQName());
        final QName type = xmlObject.getSchemaType();
        if (type != null && type.equals(this.targetQName)) {
            this.log.trace("{} schema type matches target", (Object)xmlObject.getElementQName());
            return;
        }
        final QName elementQName = xmlObject.getElementQName();
        if (elementQName.equals(this.targetQName)) {
            this.log.trace("{} element QName matches target", (Object)xmlObject.getElementQName());
            return;
        }
        final String errorMsg = "This marshaller only operations on " + this.targetQName + " elements not " + xmlObject.getElementQName();
        this.log.error(errorMsg);
        throw new MarshallingException(errorMsg);
    }
    
    protected void marshallNamespacePrefix(final XMLObject xmlObject, final Element domElement) {
        String prefix = xmlObject.getElementQName().getPrefix();
        prefix = DatatypeHelper.safeTrimOrNullString(prefix);
        if (prefix != null) {
            domElement.setPrefix(prefix);
        }
    }
    
    protected void marshallChildElements(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        this.log.trace("Marshalling child elements for XMLObject {}", (Object)xmlObject.getElementQName());
        final List<XMLObject> childXMLObjects = xmlObject.getOrderedChildren();
        if (childXMLObjects != null && childXMLObjects.size() > 0) {
            for (final XMLObject childXMLObject : childXMLObjects) {
                if (childXMLObject == null) {
                    continue;
                }
                this.log.trace("Getting marshaller for child XMLObject {}", (Object)childXMLObject.getElementQName());
                Marshaller marshaller = this.marshallerFactory.getMarshaller(childXMLObject);
                if (marshaller == null) {
                    marshaller = this.marshallerFactory.getMarshaller(Configuration.getDefaultProviderQName());
                    if (marshaller == null) {
                        final String errorMsg = "No marshaller available for " + childXMLObject.getElementQName() + ", child of " + xmlObject.getElementQName();
                        this.log.error(errorMsg);
                        throw new MarshallingException(errorMsg);
                    }
                    this.log.trace("No marshaller was registered for {}, child of {}. Using default marshaller", (Object)childXMLObject.getElementQName(), (Object)xmlObject.getElementQName());
                }
                this.log.trace("Marshalling {} and adding it to DOM", (Object)childXMLObject.getElementQName());
                marshaller.marshall(childXMLObject, domElement);
            }
        }
        else {
            this.log.trace("No child elements to marshall for XMLObject {}", (Object)xmlObject.getElementQName());
        }
    }
    
    protected void marshallNamespaces(final XMLObject xmlObject, final Element domElement) {
        this.log.trace("Marshalling namespace attributes for XMLObject {}", (Object)xmlObject.getElementQName());
        final Set<Namespace> namespaces = xmlObject.getNamespaces();
        for (final Namespace namespace : namespaces) {
            if (!namespace.alwaysDeclare()) {
                if (DatatypeHelper.safeEquals(namespace.getNamespacePrefix(), "xml")) {
                    continue;
                }
                if (DatatypeHelper.safeEquals(namespace.getNamespaceURI(), "http://www.w3.org/XML/1998/namespace")) {
                    continue;
                }
                final String declared = XMLHelper.lookupNamespaceURI(domElement, namespace.getNamespacePrefix());
                if (declared != null && namespace.getNamespaceURI().equals(declared)) {
                    this.log.trace("Namespace {} has already been declared on an ancestor of {} no need to add it here", (Object)namespace, (Object)xmlObject.getElementQName());
                    continue;
                }
            }
            this.log.trace("Adding namespace declaration {} to {}", (Object)namespace, (Object)xmlObject.getElementQName());
            final String nsURI = DatatypeHelper.safeTrimOrNullString(namespace.getNamespaceURI());
            final String nsPrefix = DatatypeHelper.safeTrimOrNullString(namespace.getNamespacePrefix());
            XMLHelper.appendNamespaceDeclaration(domElement, nsURI, nsPrefix);
        }
    }
    
    protected void marshallSchemaInstanceAttributes(final XMLObject xmlObject, final Element domElement) throws MarshallingException {
        if (!DatatypeHelper.isEmpty(xmlObject.getSchemaLocation())) {
            this.log.trace("Setting xsi:schemaLocation for XMLObject {} to {}", (Object)xmlObject.getElementQName(), (Object)xmlObject.getSchemaLocation());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", xmlObject.getSchemaLocation());
        }
        if (!DatatypeHelper.isEmpty(xmlObject.getNoNamespaceSchemaLocation())) {
            this.log.trace("Setting xsi:noNamespaceSchemaLocation for XMLObject {} to {}", (Object)xmlObject.getElementQName(), (Object)xmlObject.getNoNamespaceSchemaLocation());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", xmlObject.getNoNamespaceSchemaLocation());
        }
        if (xmlObject.isNilXSBoolean() != null && xmlObject.isNil()) {
            this.log.trace("Setting xsi:nil for XMLObject {} to true", (Object)xmlObject.getElementQName());
            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:nil", xmlObject.isNilXSBoolean().toString());
        }
        final QName type = xmlObject.getSchemaType();
        if (type == null) {
            return;
        }
        this.log.trace("Setting xsi:type attribute with for XMLObject {}", (Object)xmlObject.getElementQName());
        final String typeLocalName = DatatypeHelper.safeTrimOrNullString(type.getLocalPart());
        final String typePrefix = DatatypeHelper.safeTrimOrNullString(type.getPrefix());
        if (typeLocalName == null) {
            throw new MarshallingException("The type QName on XMLObject " + xmlObject.getElementQName() + " may not have a null local name");
        }
        if (type.getNamespaceURI() == null) {
            throw new MarshallingException("The type URI QName on XMLObject " + xmlObject.getElementQName() + " may not have a null namespace URI");
        }
        String attributeValue;
        if (typePrefix == null) {
            attributeValue = typeLocalName;
        }
        else {
            attributeValue = String.valueOf(typePrefix) + ":" + typeLocalName;
        }
        domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", attributeValue);
    }
    
    protected abstract void marshallAttributes(final XMLObject p0, final Element p1) throws MarshallingException;
    
    protected abstract void marshallElementContent(final XMLObject p0, final Element p1) throws MarshallingException;
    
    private void prepareForAdoption(final XMLObject domCachingObject) throws MarshallingException {
        if (domCachingObject.getParent() != null) {
            this.log.trace("Rooting all visible namespaces of XMLObject {} before adding it to new parent Element", (Object)domCachingObject.getElementQName());
            try {
                XMLHelper.rootNamespaces(domCachingObject.getDOM());
            }
            catch (XMLParserException e) {
                final String errorMsg = "Unable to root namespaces of cached DOM element, " + domCachingObject.getElementQName();
                this.log.error(errorMsg, (Throwable)e);
                throw new MarshallingException(errorMsg, e);
            }
            this.log.trace("Release DOM of XMLObject parent");
            domCachingObject.releaseParentDOM(true);
        }
    }
}
