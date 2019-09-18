// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.apache.xml.security.signature.SignedInfo;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.Configuration;
import org.apache.xml.security.signature.XMLSignature;
import org.opensaml.xml.signature.Signature;
import org.w3c.dom.Element;
import org.apache.xml.security.Init;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.opensaml.xml.io.Unmarshaller;

public class SignatureUnmarshaller implements Unmarshaller
{
    private final Logger log;
    
    public SignatureUnmarshaller() {
        this.log = LoggerFactory.getLogger((Class)SignatureUnmarshaller.class);
        if (!Init.isInitialized()) {
            this.log.debug("Initializing XML security library");
            Init.init();
        }
    }
    
    public Signature unmarshall(final Element signatureElement) throws UnmarshallingException {
        this.log.debug("Starting to unmarshall Apache XML-Security-based SignatureImpl element");
        final SignatureImpl signature = new SignatureImpl(signatureElement.getNamespaceURI(), signatureElement.getLocalName(), signatureElement.getPrefix());
        try {
            this.log.debug("Constructing Apache XMLSignature object");
            final XMLSignature xmlSignature = new XMLSignature(signatureElement, "");
            final SignedInfo signedInfo = xmlSignature.getSignedInfo();
            this.log.debug("Adding canonicalization and signing algorithms, and HMAC output length to Signature");
            signature.setCanonicalizationAlgorithm(signedInfo.getCanonicalizationMethodURI());
            signature.setSignatureAlgorithm(signedInfo.getSignatureMethodURI());
            signature.setHMACOutputLength(this.getHMACOutputLengthValue(signedInfo.getSignatureMethodElement()));
            final org.apache.xml.security.keys.KeyInfo xmlSecKeyInfo = xmlSignature.getKeyInfo();
            if (xmlSecKeyInfo != null) {
                this.log.debug("Adding KeyInfo to Signature");
                final Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(xmlSecKeyInfo.getElement());
                final KeyInfo keyInfo = (KeyInfo)unmarshaller.unmarshall(xmlSecKeyInfo.getElement());
                signature.setKeyInfo(keyInfo);
            }
            signature.setXMLSignature(xmlSignature);
            signature.setDOM(signatureElement);
            return signature;
        }
        catch (XMLSecurityException e) {
            this.log.error("Error constructing Apache XMLSignature instance from Signature element: {}", (Object)e.getMessage());
            throw new UnmarshallingException("Unable to unmarshall Signature with Apache XMLSignature", (Exception)e);
        }
    }
    
    private Integer getHMACOutputLengthValue(final Element signatureMethodElement) {
        if (signatureMethodElement == null) {
            return null;
        }
        final List<Element> children = XMLHelper.getChildElementsByTagNameNS(signatureMethodElement, "http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
        if (!children.isEmpty()) {
            final Element hmacElement = children.get(0);
            final String value = DatatypeHelper.safeTrimOrNullString(hmacElement.getTextContent());
            if (value != null) {
                return new Integer(value);
            }
        }
        return null;
    }
}
