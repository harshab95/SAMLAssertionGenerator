// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.slf4j.LoggerFactory;
import java.util.Iterator;
import org.opensaml.xml.Namespace;
import java.io.Writer;
import java.io.OutputStream;
import java.io.Reader;
import org.slf4j.Logger;
import java.io.InputStream;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.io.Unmarshaller;
import org.w3c.dom.Document;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.XMLRuntimeException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.XMLObject;

public final class XMLObjectHelper
{
    private XMLObjectHelper() {
    }
    
    public static <T extends XMLObject> T cloneXMLObject(final T originalXMLObject) throws MarshallingException, UnmarshallingException {
        return cloneXMLObject(originalXMLObject, false);
    }
    
    public static <T extends XMLObject> T cloneXMLObject(final T originalXMLObject, final boolean rootInNewDocument) throws MarshallingException, UnmarshallingException {
        if (originalXMLObject == null) {
            return null;
        }
        final Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(originalXMLObject);
        final Element origElement = marshaller.marshall(originalXMLObject);
        Element clonedElement = null;
        Label_0092: {
            if (rootInNewDocument) {
                try {
                    final Document newDocument = Configuration.getParserPool().newDocument();
                    clonedElement = (Element)newDocument.importNode(origElement, true);
                    newDocument.appendChild(clonedElement);
                    break Label_0092;
                }
                catch (XMLParserException e) {
                    throw new XMLRuntimeException("Error obtaining new Document from parser pool", e);
                }
            }
            clonedElement = (Element)origElement.cloneNode(true);
        }
        final Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(clonedElement);
        final T clonedXMLObject = (T)unmarshaller.unmarshall(clonedElement);
        return clonedXMLObject;
    }
    
    public static XMLObject unmarshallFromInputStream(final ParserPool parserPool, final InputStream inputStream) throws XMLParserException, UnmarshallingException {
        final Logger log = getLogger();
        log.debug("Parsing InputStream into DOM document");
        final Document messageDoc = parserPool.parse(inputStream);
        final Element messageElem = messageDoc.getDocumentElement();
        if (log.isTraceEnabled()) {
            log.trace("Resultant DOM message was:");
            log.trace(XMLHelper.nodeToString(messageElem));
        }
        log.debug("Unmarshalling DOM parsed from InputStream");
        final Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(messageElem);
        if (unmarshaller == null) {
            log.error("Unable to unmarshall InputStream, no unmarshaller registered for element " + XMLHelper.getNodeQName(messageElem));
            throw new UnmarshallingException("Unable to unmarshall InputStream, no unmarshaller registered for element " + XMLHelper.getNodeQName(messageElem));
        }
        final XMLObject message = unmarshaller.unmarshall(messageElem);
        log.debug("InputStream succesfully unmarshalled");
        return message;
    }
    
    public static XMLObject unmarshallFromReader(final ParserPool parserPool, final Reader reader) throws XMLParserException, UnmarshallingException {
        final Logger log = getLogger();
        log.debug("Parsing Reader into DOM document");
        final Document messageDoc = parserPool.parse(reader);
        final Element messageElem = messageDoc.getDocumentElement();
        if (log.isTraceEnabled()) {
            log.trace("Resultant DOM message was:");
            log.trace(XMLHelper.nodeToString(messageElem));
        }
        log.debug("Unmarshalling DOM parsed from Reader");
        final Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(messageElem);
        if (unmarshaller == null) {
            log.error("Unable to unmarshall Reader, no unmarshaller registered for element " + XMLHelper.getNodeQName(messageElem));
            throw new UnmarshallingException("Unable to unmarshall Reader, no unmarshaller registered for element " + XMLHelper.getNodeQName(messageElem));
        }
        final XMLObject message = unmarshaller.unmarshall(messageElem);
        log.debug("Reader succesfully unmarshalled");
        return message;
    }
    
    public static Element marshall(final XMLObject xmlObject) throws MarshallingException {
        final Logger log = getLogger();
        log.debug("Marshalling XMLObject");
        if (xmlObject.getDOM() != null) {
            log.debug("XMLObject already had cached DOM, returning that element");
            return xmlObject.getDOM();
        }
        final Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(xmlObject);
        if (marshaller == null) {
            log.error("Unable to marshall XMLOBject, no marshaller registered for object: " + xmlObject.getElementQName());
        }
        final Element messageElem = marshaller.marshall(xmlObject);
        if (log.isTraceEnabled()) {
            log.trace("Marshalled XMLObject into DOM:");
            log.trace(XMLHelper.nodeToString(messageElem));
        }
        return messageElem;
    }
    
    public static void marshallToOutputStream(final XMLObject xmlObject, final OutputStream outputStream) throws MarshallingException {
        final Element element = marshall(xmlObject);
        XMLHelper.writeNode(element, outputStream);
    }
    
    public static void marshallToWriter(final XMLObject xmlObject, final Writer writer) throws MarshallingException {
        final Element element = marshall(xmlObject);
        XMLHelper.writeNode(element, writer);
    }
    
    public static String lookupNamespaceURI(final XMLObject xmlObject, final String prefix) {
        for (XMLObject current = xmlObject; current != null; current = current.getParent()) {
            for (final Namespace ns : current.getNamespaces()) {
                if (DatatypeHelper.safeEquals(ns.getNamespacePrefix(), prefix)) {
                    return ns.getNamespaceURI();
                }
            }
        }
        return null;
    }
    
    public static String lookupNamespacePrefix(final XMLObject xmlObject, final String namespaceURI) {
        for (XMLObject current = xmlObject; current != null; current = current.getParent()) {
            for (final Namespace ns : current.getNamespaces()) {
                if (DatatypeHelper.safeEquals(ns.getNamespaceURI(), namespaceURI)) {
                    return ns.getNamespacePrefix();
                }
            }
        }
        return null;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)XMLObjectHelper.class);
    }
}
