// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import javax.xml.datatype.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.ls.LSSerializerFilter;
import java.io.OutputStream;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.DOMImplementationLS;
import java.io.Writer;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.ArrayList;
import org.opensaml.xml.XMLRuntimeException;
import org.opensaml.xml.XMLObject;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import java.util.Iterator;
import org.opensaml.xml.Configuration;
import java.util.Collections;
import java.util.List;
import org.w3c.dom.Document;
import java.util.Locale;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;
import java.util.StringTokenizer;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Map;

public final class XMLHelper
{
    public static final String LIST_DELIMITERS = " \n\r\t";
    private static Map<String, Object> prettyPrintParams;
    private static DatatypeFactory dataTypeFactory;
    
    private XMLHelper() {
    }
    
    public static DatatypeFactory getDataTypeFactory() {
        if (XMLHelper.dataTypeFactory == null) {
            try {
                XMLHelper.dataTypeFactory = DatatypeFactory.newInstance();
            }
            catch (DatatypeConfigurationException ex) {}
        }
        return XMLHelper.dataTypeFactory;
    }
    
    public static boolean hasXSIType(final Element e) {
        return e != null && e.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "type") != null;
    }
    
    public static QName getXSIType(final Element e) {
        if (hasXSIType(e)) {
            final Attr attribute = e.getAttributeNodeNS("http://www.w3.org/2001/XMLSchema-instance", "type");
            final String attributeValue = attribute.getTextContent().trim();
            final StringTokenizer tokenizer = new StringTokenizer(attributeValue, ":");
            String prefix = null;
            String localPart;
            if (tokenizer.countTokens() > 1) {
                prefix = tokenizer.nextToken();
                localPart = tokenizer.nextToken();
            }
            else {
                localPart = tokenizer.nextToken();
            }
            return constructQName(e.lookupNamespaceURI(prefix), localPart, prefix);
        }
        return null;
    }
    
    public static Attr getIdAttribute(final Element domElement) {
        if (!domElement.hasAttributes()) {
            return null;
        }
        final NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            final Attr attribute = (Attr)attributes.item(i);
            if (attribute.isId()) {
                return attribute;
            }
        }
        return null;
    }
    
    public static QName getNodeQName(final Node domNode) {
        if (domNode != null) {
            return constructQName(domNode.getNamespaceURI(), domNode.getLocalName(), domNode.getPrefix());
        }
        return null;
    }
    
    public static Locale getLanguage(final Element element) {
        String lang = DatatypeHelper.safeTrimOrNullString(element.getAttributeNS("http://www.w3.org/XML/1998/namespace", "lang"));
        if (lang != null) {
            if (lang.contains("-")) {
                lang = lang.substring(0, lang.indexOf("-"));
            }
            return new Locale(lang.toUpperCase());
        }
        return Locale.getDefault();
    }
    
    public static Attr constructAttribute(final Document owningDocument, final QName attributeName) {
        return constructAttribute(owningDocument, attributeName.getNamespaceURI(), attributeName.getLocalPart(), attributeName.getPrefix());
    }
    
    public static Attr constructAttribute(final Document document, final String namespaceURI, final String localName, final String prefix) {
        final String trimmedLocalName = DatatypeHelper.safeTrimOrNullString(localName);
        if (trimmedLocalName == null) {
            throw new IllegalArgumentException("Local name may not be null or empty");
        }
        final String trimmedPrefix = DatatypeHelper.safeTrimOrNullString(prefix);
        String qualifiedName;
        if (trimmedPrefix != null) {
            qualifiedName = String.valueOf(trimmedPrefix) + ":" + DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        else {
            qualifiedName = DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        if (DatatypeHelper.isEmpty(namespaceURI)) {
            return document.createAttributeNS(null, qualifiedName);
        }
        return document.createAttributeNS(namespaceURI, qualifiedName);
    }
    
    public static QName getAttributeValueAsQName(final Attr attribute) {
        if (attribute == null || DatatypeHelper.isEmpty(attribute.getValue())) {
            return null;
        }
        final String attributeValue = attribute.getTextContent();
        final String[] valueComponents = attributeValue.split(":");
        if (valueComponents.length == 1) {
            return constructQName(attribute.lookupNamespaceURI(null), valueComponents[0], null);
        }
        return constructQName(attribute.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
    }
    
    public static Boolean getAttributeValueAsBoolean(final Attr attribute) {
        if (attribute == null) {
            return null;
        }
        final String valueStr = attribute.getValue();
        if (valueStr.equals("0") || valueStr.equals("false")) {
            return Boolean.FALSE;
        }
        if (valueStr.equals("1") || valueStr.equals("true")) {
            return Boolean.TRUE;
        }
        return null;
    }
    
    public static List<String> getAttributeValueAsList(final Attr attribute) {
        if (attribute == null) {
            return Collections.emptyList();
        }
        return DatatypeHelper.stringToList(attribute.getValue(), " \n\r\t");
    }
    
    public static void marshallAttribute(final QName attributeName, final String attributeValue, final Element domElement, final boolean isIDAttribute) {
        final Document document = domElement.getOwnerDocument();
        final Attr attribute = constructAttribute(document, attributeName);
        attribute.setValue(attributeValue);
        domElement.setAttributeNodeNS(attribute);
        if (isIDAttribute) {
            domElement.setIdAttributeNode(attribute, true);
        }
    }
    
    public static void marshallAttribute(final QName attributeName, final List<String> attributeValues, final Element domElement, final boolean isIDAttribute) {
        marshallAttribute(attributeName, DatatypeHelper.listToStringValue(attributeValues, " "), domElement, isIDAttribute);
    }
    
    public static void marshallAttributeMap(final AttributeMap attributeMap, final Element domElement) {
        final Document document = domElement.getOwnerDocument();
        Attr attribute = null;
        for (final Map.Entry<QName, String> entry : attributeMap.entrySet()) {
            attribute = constructAttribute(document, entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (Configuration.isIDAttribute(entry.getKey()) || attributeMap.isIDAttribute(entry.getKey())) {
                domElement.setIdAttributeNode(attribute, true);
            }
        }
    }
    
    public static void unmarshallToAttributeMap(final AttributeMap attributeMap, final Attr attribute) {
        final QName attribQName = constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
        attributeMap.put(attribQName, attribute.getValue());
        if (attribute.isId() || Configuration.isIDAttribute(attribQName)) {
            attributeMap.registerID(attribQName);
        }
    }
    
    public static QName getElementContentAsQName(final Element element) {
        if (element == null) {
            return null;
        }
        String elementContent = null;
        final NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            final Node node = nodeList.item(i);
            if (node.getNodeType() == 3) {
                elementContent = DatatypeHelper.safeTrimOrNullString(((Text)node).getWholeText());
                break;
            }
        }
        if (elementContent == null) {
            return null;
        }
        final String[] valueComponents = elementContent.split(":");
        if (valueComponents.length == 1) {
            return constructQName(element.lookupNamespaceURI(null), valueComponents[0], null);
        }
        return constructQName(element.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
    }
    
    public static List<String> getElementContentAsList(final Element element) {
        if (element == null) {
            return Collections.emptyList();
        }
        return DatatypeHelper.stringToList(element.getTextContent(), " \n\r\t");
    }
    
    public static QName constructQName(final String namespaceURI, final String localName, final String prefix) {
        if (DatatypeHelper.isEmpty(prefix)) {
            return new QName(namespaceURI, localName);
        }
        if (DatatypeHelper.isEmpty(namespaceURI)) {
            return new QName(localName);
        }
        return new QName(namespaceURI, localName, prefix);
    }
    
    public static QName constructQName(final String qname, final XMLObject owningObject) {
        return constructQName(qname, owningObject.getDOM());
    }
    
    public static QName constructQName(final String qname, final Element owningElement) {
        String nsPrefix;
        String name;
        if (qname.indexOf(":") > -1) {
            final StringTokenizer qnameTokens = new StringTokenizer(qname, ":");
            nsPrefix = qnameTokens.nextToken();
            name = qnameTokens.nextToken();
        }
        else {
            nsPrefix = "";
            name = qname;
        }
        final String nsURI = lookupNamespaceURI(owningElement, nsPrefix);
        return constructQName(nsURI, name, nsPrefix);
    }
    
    public static Element constructElement(final Document document, final QName elementName) {
        return constructElement(document, elementName.getNamespaceURI(), elementName.getLocalPart(), elementName.getPrefix());
    }
    
    public static Element constructElement(final Document document, final String namespaceURI, final String localName, final String prefix) {
        final String trimmedLocalName = DatatypeHelper.safeTrimOrNullString(localName);
        if (trimmedLocalName == null) {
            throw new IllegalArgumentException("Local name may not be null or empty");
        }
        final String trimmedPrefix = DatatypeHelper.safeTrimOrNullString(prefix);
        String qualifiedName;
        if (trimmedPrefix != null) {
            qualifiedName = String.valueOf(trimmedPrefix) + ":" + DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        else {
            qualifiedName = DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        if (!DatatypeHelper.isEmpty(namespaceURI)) {
            return document.createElementNS(namespaceURI, qualifiedName);
        }
        return document.createElementNS(null, qualifiedName);
    }
    
    public static void appendChildElement(final Element parentElement, final Element childElement) {
        final Document parentDocument = parentElement.getOwnerDocument();
        adoptElement(childElement, parentDocument);
        parentElement.appendChild(childElement);
    }
    
    public static void adoptElement(final Element adoptee, final Document adopter) {
        if (!adoptee.getOwnerDocument().equals(adopter) && adopter.adoptNode(adoptee) == null) {
            throw new XMLRuntimeException("DOM Element node adoption failed");
        }
    }
    
    public static void appendTextContent(final Element domElement, final String textContent) {
        if (textContent == null) {
            return;
        }
        final Document parentDocument = domElement.getOwnerDocument();
        final Text textNode = parentDocument.createTextNode(textContent);
        domElement.appendChild(textNode);
    }
    
    public static void appendNamespaceDeclaration(final Element domElement, final String namespaceURI, final String prefix) {
        final String nsURI = DatatypeHelper.safeTrimOrNullString(namespaceURI);
        final String nsPrefix = DatatypeHelper.safeTrimOrNullString(prefix);
        String attributeName;
        if (nsPrefix == null) {
            attributeName = "xmlns";
        }
        else {
            attributeName = "xmlns:" + nsPrefix;
        }
        String attributeValue;
        if (nsURI == null) {
            attributeValue = "";
        }
        else {
            attributeValue = nsURI;
        }
        domElement.setAttributeNS("http://www.w3.org/2000/xmlns/", attributeName, attributeValue);
    }
    
    public static String lookupNamespaceURI(final Element startingElement, final String prefix) {
        return lookupNamespaceURI(startingElement, null, prefix);
    }
    
    public static String lookupNamespaceURI(final Element startingElement, final Element stopingElement, final String prefix) {
        if (startingElement.hasAttributes()) {
            final NamedNodeMap map = startingElement.getAttributes();
            for (int length = map.getLength(), i = 0; i < length; ++i) {
                final Node attr = map.item(i);
                final String attrPrefix = attr.getPrefix();
                final String value = attr.getNodeValue();
                final String namespaceURI = attr.getNamespaceURI();
                if (namespaceURI != null && namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
                    if (prefix == null && attr.getNodeName().equals("xmlns")) {
                        return value;
                    }
                    if (attrPrefix != null && attrPrefix.equals("xmlns") && attr.getLocalName().equals(prefix)) {
                        return value;
                    }
                }
            }
        }
        if (startingElement != stopingElement) {
            final Element ancestor = getElementAncestor(startingElement);
            if (ancestor != null) {
                return lookupNamespaceURI(ancestor, stopingElement, prefix);
            }
        }
        return null;
    }
    
    public static String lookupPrefix(final Element startingElement, final String namespaceURI) {
        return lookupPrefix(startingElement, null, namespaceURI);
    }
    
    public static String lookupPrefix(final Element startingElement, final Element stopingElement, final String namespaceURI) {
        if (startingElement.hasAttributes()) {
            final NamedNodeMap map = startingElement.getAttributes();
            for (int length = map.getLength(), i = 0; i < length; ++i) {
                final Node attr = map.item(i);
                final String attrPrefix = attr.getPrefix();
                final String value = attr.getNodeValue();
                final String namespace = attr.getNamespaceURI();
                if (namespace != null && namespace.equals("http://www.w3.org/2000/xmlns/") && (attr.getNodeName().equals("xmlns") || (attrPrefix != null && attrPrefix.equals("xmlns") && value.equals(namespaceURI)))) {
                    final String localname = attr.getLocalName();
                    final String foundNamespace = startingElement.lookupNamespaceURI(localname);
                    if (foundNamespace != null && foundNamespace.equals(namespaceURI)) {
                        return localname;
                    }
                }
            }
        }
        if (startingElement != stopingElement) {
            final Element ancestor = getElementAncestor(startingElement);
            if (ancestor != null) {
                return lookupPrefix(ancestor, stopingElement, namespaceURI);
            }
        }
        return null;
    }
    
    public static List<Element> getChildElementsByTagNameNS(final Element root, final String namespaceURI, final String localName) {
        final ArrayList<Element> children = new ArrayList<Element>();
        final NodeList childNodes = root.getChildNodes();
        for (int numOfNodes = childNodes.getLength(), i = 0; i < numOfNodes; ++i) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == 1) {
                final Element e = (Element)childNode;
                if (DatatypeHelper.safeEquals(e.getNamespaceURI(), namespaceURI) && DatatypeHelper.safeEquals(e.getLocalName(), localName)) {
                    children.add(e);
                }
            }
        }
        return children;
    }
    
    public static List<Element> getChildElementsByTagName(final Element root, final String localName) {
        final ArrayList<Element> children = new ArrayList<Element>();
        final NodeList childNodes = root.getChildNodes();
        for (int numOfNodes = childNodes.getLength(), i = 0; i < numOfNodes; ++i) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == 1) {
                final Element e = (Element)childNode;
                if (DatatypeHelper.safeEquals(e.getLocalName(), localName)) {
                    children.add(e);
                }
            }
        }
        return children;
    }
    
    public static Map<QName, List<Element>> getChildElements(final Element root) {
        final Map<QName, List<Element>> children = new HashMap<QName, List<Element>>();
        final NodeList childNodes = root.getChildNodes();
        for (int numOfNodes = childNodes.getLength(), i = 0; i < numOfNodes; ++i) {
            final Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == 1) {
                final Element e = (Element)childNode;
                final QName qname = getNodeQName(e);
                List<Element> elements = children.get(qname);
                if (elements == null) {
                    elements = new ArrayList<Element>();
                    children.put(qname, elements);
                }
                elements.add(e);
            }
        }
        return children;
    }
    
    public static Element getElementAncestor(final Node currentNode) {
        final Node parent = currentNode.getParentNode();
        if (parent == null) {
            return null;
        }
        final short type = parent.getNodeType();
        if (type == 1) {
            return (Element)parent;
        }
        return getElementAncestor(parent);
    }
    
    public static String nodeToString(final Node node) {
        final StringWriter writer = new StringWriter();
        writeNode(node, writer);
        return writer.toString();
    }
    
    public static String prettyPrintXML(final Node node) {
        final StringWriter writer = new StringWriter();
        writeNode(node, writer, getPrettyPrintParams());
        return writer.toString();
    }
    
    private static Map<String, Object> getPrettyPrintParams() {
        if (XMLHelper.prettyPrintParams == null) {
            (XMLHelper.prettyPrintParams = new LazyMap<String, Object>()).put("format-pretty-print", Boolean.TRUE);
        }
        return XMLHelper.prettyPrintParams;
    }
    
    public static void writeNode(final Node node, final Writer output) {
        writeNode(node, output, null);
    }
    
    public static void writeNode(final Node node, final Writer output, final Map<String, Object> serializerParams) {
        final DOMImplementationLS domImplLS = getLSDOMImpl(node);
        final LSSerializer serializer = getLSSerializer(domImplLS, serializerParams);
        final LSOutput serializerOut = domImplLS.createLSOutput();
        serializerOut.setCharacterStream(output);
        serializer.write(node, serializerOut);
    }
    
    public static void writeNode(final Node node, final OutputStream output) {
        writeNode(node, output, null);
    }
    
    public static void writeNode(final Node node, final OutputStream output, final Map<String, Object> serializerParams) {
        final DOMImplementationLS domImplLS = getLSDOMImpl(node);
        final LSSerializer serializer = getLSSerializer(domImplLS, serializerParams);
        final LSOutput serializerOut = domImplLS.createLSOutput();
        serializerOut.setByteStream(output);
        serializer.write(node, serializerOut);
    }
    
    public static LSSerializer getLSSerializer(final DOMImplementationLS domImplLS, final Map<String, Object> serializerParams) {
        final LSSerializer serializer = domImplLS.createLSSerializer();
        serializer.setFilter(new LSSerializerFilter() {
            public short acceptNode(final Node arg0) {
                return 1;
            }
            
            public int getWhatToShow() {
                return -1;
            }
        });
        if (serializerParams != null) {
            final DOMConfiguration serializerDOMConfig = serializer.getDomConfig();
            for (final String key : serializerParams.keySet()) {
                serializerDOMConfig.setParameter(key, serializerParams.get(key));
            }
        }
        return serializer;
    }
    
    public static DOMImplementationLS getLSDOMImpl(final Node node) {
        DOMImplementation domImpl;
        if (node instanceof Document) {
            domImpl = ((Document)node).getImplementation();
        }
        else {
            domImpl = node.getOwnerDocument().getImplementation();
        }
        final DOMImplementationLS domImplLS = (DOMImplementationLS)domImpl.getFeature("LS", "3.0");
        return domImplLS;
    }
    
    public static String qnameToContentString(final QName qname) {
        final StringBuffer buf = new StringBuffer();
        final String prefix = DatatypeHelper.safeTrimOrNullString(qname.getPrefix());
        if (prefix != null) {
            buf.append(prefix);
            buf.append(":");
        }
        buf.append(qname.getLocalPart());
        return buf.toString();
    }
    
    public static void rootNamespaces(final Element domElement) throws XMLParserException {
        rootNamespaces(domElement, domElement);
    }
    
    private static void rootNamespaces(final Element domElement, final Element upperNamespaceSearchBound) throws XMLParserException {
        String namespaceURI = null;
        String namespacePrefix = domElement.getPrefix();
        boolean nsDeclaredOnElement = false;
        if (namespacePrefix == null) {
            nsDeclaredOnElement = domElement.hasAttributeNS(null, "xmlns");
        }
        else {
            nsDeclaredOnElement = domElement.hasAttributeNS("http://www.w3.org/2000/xmlns/", namespacePrefix);
        }
        if (!nsDeclaredOnElement) {
            namespaceURI = lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix);
            if (namespaceURI == null) {
                namespaceURI = lookupNamespaceURI(upperNamespaceSearchBound, null, namespacePrefix);
                if (namespaceURI != null) {
                    appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
                }
                else if (namespacePrefix != null) {
                    throw new XMLParserException("Unable to resolve namespace prefix " + namespacePrefix + " found on element " + getNodeQName(domElement));
                }
            }
        }
        final NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            namespacePrefix = null;
            namespaceURI = null;
            final Node attributeNode = attributes.item(i);
            if (attributeNode.getNodeType() == 2) {
                namespacePrefix = attributeNode.getPrefix();
                if (!DatatypeHelper.isEmpty(namespacePrefix) && !namespacePrefix.equals("xmlns")) {
                    if (!namespacePrefix.equals("xml")) {
                        namespaceURI = lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix);
                        if (namespaceURI == null) {
                            namespaceURI = lookupNamespaceURI(upperNamespaceSearchBound, null, namespacePrefix);
                            if (namespaceURI == null) {
                                throw new XMLParserException("Unable to resolve namespace prefix " + namespacePrefix + " found on attribute " + getNodeQName(attributeNode) + " found on element " + getNodeQName(domElement));
                            }
                            appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
                        }
                    }
                }
            }
        }
        final NodeList childNodes = domElement.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); ++j) {
            final Node childNode = childNodes.item(j);
            if (childNode.getNodeType() == 1) {
                rootNamespaces((Element)childNode, upperNamespaceSearchBound);
            }
        }
    }
    
    public static boolean isElementNamed(final Element e, final String ns, final String localName) {
        return e != null && DatatypeHelper.safeEquals(ns, e.getNamespaceURI()) && DatatypeHelper.safeEquals(localName, e.getLocalName());
    }
    
    public static Element getFirstChildElement(final Node n) {
        Node child;
        for (child = n.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {}
        if (child != null) {
            return (Element)child;
        }
        return null;
    }
    
    public static Element getNextSiblingElement(final Node n) {
        Node sib;
        for (sib = n.getNextSibling(); sib != null && sib.getNodeType() != 1; sib = sib.getNextSibling()) {}
        if (sib != null) {
            return (Element)sib;
        }
        return null;
    }
    
    public static long durationToLong(final String duration) {
        final Duration xmlDuration = getDataTypeFactory().newDuration(duration);
        return xmlDuration.getTimeInMillis(new GregorianCalendar());
    }
    
    public static String longToDuration(final long duration) {
        final Duration xmlDuration = getDataTypeFactory().newDuration(duration);
        return xmlDuration.toString();
    }
}
