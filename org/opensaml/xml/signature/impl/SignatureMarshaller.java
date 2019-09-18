// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Iterator;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.signature.ContentReference;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.xml.security.SecurityHelper;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.signature.Signature;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import org.opensaml.xml.io.MarshallingException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.opensaml.xml.XMLObject;
import org.apache.xml.security.Init;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.opensaml.xml.io.Marshaller;

public class SignatureMarshaller implements Marshaller
{
    private final Logger log;
    
    public SignatureMarshaller() {
        this.log = LoggerFactory.getLogger((Class)SignatureMarshaller.class);
        if (!Init.isInitialized()) {
            this.log.debug("Initializing XML security library");
            Init.init();
        }
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
    
    public Element marshall(final XMLObject xmlObject, final Element parentElement) throws MarshallingException {
        final Element signatureElement = this.createSignatureElement((Signature)xmlObject, parentElement.getOwnerDocument());
        XMLHelper.appendChildElement(parentElement, signatureElement);
        return signatureElement;
    }
    
    public Element marshall(final XMLObject xmlObject, final Document document) throws MarshallingException {
        final Element signatureElement = this.createSignatureElement((Signature)xmlObject, document);
        final Element documentRoot = document.getDocumentElement();
        if (documentRoot != null) {
            document.replaceChild(signatureElement, documentRoot);
        }
        else {
            document.appendChild(signatureElement);
        }
        return signatureElement;
    }
    
    private Element createSignatureElement(final Signature signature, final Document document) throws MarshallingException {
        this.log.debug("Starting to marshall {}", (Object)signature.getElementQName());
        try {
            this.log.debug("Creating XMLSignature object");
            XMLSignature dsig = null;
            if (signature.getHMACOutputLength() != null && SecurityHelper.isHMAC(signature.getSignatureAlgorithm())) {
                dsig = new XMLSignature(document, "", signature.getSignatureAlgorithm(), (int)signature.getHMACOutputLength(), signature.getCanonicalizationAlgorithm());
            }
            else {
                dsig = new XMLSignature(document, "", signature.getSignatureAlgorithm(), signature.getCanonicalizationAlgorithm());
            }
            this.log.debug("Adding content to XMLSignature.");
            for (final ContentReference contentReference : signature.getContentReferences()) {
                contentReference.createReference(dsig);
            }
            this.log.debug("Creating Signature DOM element");
            final Element signatureElement = dsig.getElement();
            if (signature.getKeyInfo() != null) {
                final Marshaller keyInfoMarshaller = Configuration.getMarshallerFactory().getMarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
                keyInfoMarshaller.marshall(signature.getKeyInfo(), signatureElement);
            }
            ((SignatureImpl)signature).setXMLSignature(dsig);
            signature.setDOM(signatureElement);
            signature.releaseParentDOM(true);
            return signatureElement;
        }
        catch (XMLSecurityException e) {
            this.log.error("Unable to construct signature Element " + signature.getElementQName(), (Throwable)e);
            throw new MarshallingException("Unable to construct signature Element " + signature.getElementQName(), (Exception)e);
        }
    }
}
