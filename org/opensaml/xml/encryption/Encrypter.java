// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import java.security.interfaces.DSAPublicKey;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.opensaml.xml.signature.DigestMethod;
import org.w3c.dom.Element;
import org.opensaml.xml.io.UnmarshallingException;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.encryption.XMLCipher;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import java.util.Iterator;
import org.w3c.dom.Document;
import java.security.Key;
import java.util.List;
import java.util.ArrayList;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.Configuration;
import org.slf4j.LoggerFactory;
import org.apache.xml.security.Init;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.XMLSignatureBuilder;
import org.opensaml.xml.io.Unmarshaller;
import org.slf4j.Logger;

public class Encrypter
{
    private final Logger log;
    private Unmarshaller encryptedDataUnmarshaller;
    private Unmarshaller encryptedKeyUnmarshaller;
    private XMLSignatureBuilder<KeyInfo> keyInfoBuilder;
    private String jcaProviderName;
    
    static {
        if (!Init.isInitialized()) {
            Init.init();
        }
    }
    
    public Encrypter() {
        this.log = LoggerFactory.getLogger((Class)Encrypter.class);
        final UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
        this.encryptedDataUnmarshaller = unmarshallerFactory.getUnmarshaller(EncryptedData.DEFAULT_ELEMENT_NAME);
        this.encryptedKeyUnmarshaller = unmarshallerFactory.getUnmarshaller(EncryptedKey.DEFAULT_ELEMENT_NAME);
        final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        this.keyInfoBuilder = (XMLSignatureBuilder<KeyInfo>)builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
        this.jcaProviderName = null;
    }
    
    public String getJCAProviderName() {
        return this.jcaProviderName;
    }
    
    public void setJCAProviderName(final String providerName) {
        this.jcaProviderName = providerName;
    }
    
    public EncryptedData encryptElement(final XMLObject xmlObject, final EncryptionParameters encParams) throws EncryptionException {
        final List<KeyEncryptionParameters> emptyKEKParamsList = new ArrayList<KeyEncryptionParameters>();
        return this.encryptElement(xmlObject, encParams, emptyKEKParamsList, false);
    }
    
    public EncryptedData encryptElement(final XMLObject xmlObject, final EncryptionParameters encParams, final KeyEncryptionParameters kekParams) throws EncryptionException {
        final List<KeyEncryptionParameters> kekParamsList = new ArrayList<KeyEncryptionParameters>();
        kekParamsList.add(kekParams);
        return this.encryptElement(xmlObject, encParams, kekParamsList, false);
    }
    
    public EncryptedData encryptElement(final XMLObject xmlObject, final EncryptionParameters encParams, final List<KeyEncryptionParameters> kekParamsList) throws EncryptionException {
        return this.encryptElement(xmlObject, encParams, kekParamsList, false);
    }
    
    public EncryptedData encryptElementContent(final XMLObject xmlObject, final EncryptionParameters encParams) throws EncryptionException {
        final List<KeyEncryptionParameters> emptyKEKParamsList = new ArrayList<KeyEncryptionParameters>();
        return this.encryptElement(xmlObject, encParams, emptyKEKParamsList, true);
    }
    
    public EncryptedData encryptElementContent(final XMLObject xmlObject, final EncryptionParameters encParams, final KeyEncryptionParameters kekParams) throws EncryptionException {
        final List<KeyEncryptionParameters> kekParamsList = new ArrayList<KeyEncryptionParameters>();
        kekParamsList.add(kekParams);
        return this.encryptElement(xmlObject, encParams, kekParamsList, true);
    }
    
    public EncryptedData encryptElementContent(final XMLObject xmlObject, final EncryptionParameters encParams, final List<KeyEncryptionParameters> kekParamsList) throws EncryptionException {
        return this.encryptElement(xmlObject, encParams, kekParamsList, true);
    }
    
    public List<EncryptedKey> encryptKey(final Key key, final List<KeyEncryptionParameters> kekParamsList, final Document containingDocument) throws EncryptionException {
        this.checkParams(kekParamsList, false);
        final List<EncryptedKey> encKeys = new ArrayList<EncryptedKey>();
        for (final KeyEncryptionParameters kekParam : kekParamsList) {
            encKeys.add(this.encryptKey(key, kekParam, containingDocument));
        }
        return encKeys;
    }
    
    public EncryptedKey encryptKey(final Key key, final KeyEncryptionParameters kekParams, final Document containingDocument) throws EncryptionException {
        this.checkParams(kekParams, false);
        final Key encryptionKey = SecurityHelper.extractEncryptionKey(kekParams.getEncryptionCredential());
        final String encryptionAlgorithmURI = kekParams.getAlgorithm();
        final EncryptedKey encryptedKey = this.encryptKey(key, encryptionKey, encryptionAlgorithmURI, containingDocument);
        if (kekParams.getKeyInfoGenerator() != null) {
            final KeyInfoGenerator generator = kekParams.getKeyInfoGenerator();
            this.log.debug("Dynamically generating KeyInfo from Credential for EncryptedKey using generator: {}", (Object)generator.getClass().getName());
            try {
                encryptedKey.setKeyInfo(generator.generate(kekParams.getEncryptionCredential()));
            }
            catch (SecurityException e) {
                this.log.error("Error during EncryptedKey KeyInfo generation", (Throwable)e);
                throw new EncryptionException("Error during EncryptedKey KeyInfo generation", e);
            }
        }
        if (kekParams.getRecipient() != null) {
            encryptedKey.setRecipient(kekParams.getRecipient());
        }
        return encryptedKey;
    }
    
    protected EncryptedKey encryptKey(final Key targetKey, final Key encryptionKey, final String encryptionAlgorithmURI, final Document containingDocument) throws EncryptionException {
        if (targetKey == null) {
            this.log.error("Target key for key encryption was null");
            throw new EncryptionException("Target key was null");
        }
        if (encryptionKey == null) {
            this.log.error("Encryption key for key encryption was null");
            throw new EncryptionException("Encryption key was null");
        }
        this.log.debug("Encrypting encryption key with algorithm: {}", (Object)encryptionAlgorithmURI);
        XMLCipher xmlCipher;
        try {
            if (this.getJCAProviderName() != null) {
                xmlCipher = XMLCipher.getProviderInstance(encryptionAlgorithmURI, this.getJCAProviderName());
            }
            else {
                xmlCipher = XMLCipher.getInstance(encryptionAlgorithmURI);
            }
            xmlCipher.init(3, encryptionKey);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error initializing cipher instance on key encryption", (Throwable)e);
            throw new EncryptionException("Error initializing cipher instance on key encryption", (Exception)e);
        }
        org.apache.xml.security.encryption.EncryptedKey apacheEncryptedKey;
        try {
            apacheEncryptedKey = xmlCipher.encryptKey(containingDocument, targetKey);
            this.postProcessApacheEncryptedKey(apacheEncryptedKey, targetKey, encryptionKey, encryptionAlgorithmURI, containingDocument);
        }
        catch (XMLEncryptionException e2) {
            this.log.error("Error encrypting element on key encryption", (Throwable)e2);
            throw new EncryptionException("Error encrypting element on key encryption", (Exception)e2);
        }
        EncryptedKey encryptedKey;
        try {
            final Element encKeyElement = xmlCipher.martial(containingDocument, apacheEncryptedKey);
            encryptedKey = (EncryptedKey)this.encryptedKeyUnmarshaller.unmarshall(encKeyElement);
        }
        catch (UnmarshallingException e3) {
            this.log.error("Error unmarshalling EncryptedKey element", (Throwable)e3);
            throw new EncryptionException("Error unmarshalling EncryptedKey element");
        }
        return encryptedKey;
    }
    
    protected void postProcessApacheEncryptedKey(final org.apache.xml.security.encryption.EncryptedKey apacheEncryptedKey, final Key targetKey, final Key encryptionKey, final String encryptionAlgorithmURI, final Document containingDocument) throws EncryptionException {
        if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionAlgorithmURI)) {
            boolean sawDigestMethod = false;
            final Iterator childIter = apacheEncryptedKey.getEncryptionMethod().getEncryptionMethodInformation();
            while (childIter.hasNext()) {
                final Element child = childIter.next();
                if (DigestMethod.DEFAULT_ELEMENT_NAME.equals(XMLHelper.getNodeQName(child))) {
                    sawDigestMethod = true;
                    break;
                }
            }
            if (!sawDigestMethod) {
                final Element digestMethodElem = XMLHelper.constructElement(containingDocument, DigestMethod.DEFAULT_ELEMENT_NAME);
                XMLHelper.appendNamespaceDeclaration(digestMethodElem, "http://www.w3.org/2000/09/xmldsig#", "ds");
                digestMethodElem.setAttributeNS(null, "Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
                apacheEncryptedKey.getEncryptionMethod().addEncryptionMethodInformation(digestMethodElem);
            }
        }
    }
    
    protected EncryptedData encryptElement(final XMLObject xmlObject, final Key encryptionKey, final String encryptionAlgorithmURI, final boolean encryptContentMode) throws EncryptionException {
        if (xmlObject == null) {
            this.log.error("XMLObject for encryption was null");
            throw new EncryptionException("XMLObject was null");
        }
        if (encryptionKey == null) {
            this.log.error("Encryption key for key encryption was null");
            throw new EncryptionException("Encryption key was null");
        }
        this.log.debug("Encrypting XMLObject using algorithm URI {} with content mode {}", (Object)encryptionAlgorithmURI, (Object)encryptContentMode);
        this.checkAndMarshall(xmlObject);
        final Element targetElement = xmlObject.getDOM();
        final Document ownerDocument = targetElement.getOwnerDocument();
        XMLCipher xmlCipher;
        try {
            if (this.getJCAProviderName() != null) {
                xmlCipher = XMLCipher.getProviderInstance(encryptionAlgorithmURI, this.getJCAProviderName());
            }
            else {
                xmlCipher = XMLCipher.getInstance(encryptionAlgorithmURI);
            }
            xmlCipher.init(1, encryptionKey);
        }
        catch (XMLEncryptionException e) {
            this.log.error("Error initializing cipher instance on XMLObject encryption", (Throwable)e);
            throw new EncryptionException("Error initializing cipher instance", (Exception)e);
        }
        org.apache.xml.security.encryption.EncryptedData apacheEncryptedData;
        try {
            apacheEncryptedData = xmlCipher.encryptData(ownerDocument, targetElement, encryptContentMode);
        }
        catch (Exception e2) {
            this.log.error("Error encrypting XMLObject", (Throwable)e2);
            throw new EncryptionException("Error encrypting XMLObject", e2);
        }
        EncryptedData encryptedData;
        try {
            final Element encDataElement = xmlCipher.martial(ownerDocument, apacheEncryptedData);
            encryptedData = (EncryptedData)this.encryptedDataUnmarshaller.unmarshall(encDataElement);
        }
        catch (UnmarshallingException e3) {
            this.log.error("Error unmarshalling EncryptedData element", (Throwable)e3);
            throw new EncryptionException("Error unmarshalling EncryptedData element", e3);
        }
        return encryptedData;
    }
    
    private EncryptedData encryptElement(final XMLObject xmlObject, final EncryptionParameters encParams, final List<KeyEncryptionParameters> kekParamsList, final boolean encryptContentMode) throws EncryptionException {
        this.checkParams(encParams, kekParamsList);
        final String encryptionAlgorithmURI = encParams.getAlgorithm();
        Key encryptionKey = SecurityHelper.extractEncryptionKey(encParams.getEncryptionCredential());
        if (encryptionKey == null) {
            encryptionKey = this.generateEncryptionKey(encryptionAlgorithmURI);
        }
        final EncryptedData encryptedData = this.encryptElement(xmlObject, encryptionKey, encryptionAlgorithmURI, encryptContentMode);
        final Document ownerDocument = encryptedData.getDOM().getOwnerDocument();
        if (encParams.getKeyInfoGenerator() != null) {
            final KeyInfoGenerator generator = encParams.getKeyInfoGenerator();
            this.log.debug("Dynamically generating KeyInfo from Credential for EncryptedData using generator: {}", (Object)generator.getClass().getName());
            try {
                encryptedData.setKeyInfo(generator.generate(encParams.getEncryptionCredential()));
            }
            catch (SecurityException e) {
                this.log.error("Error during EncryptedData KeyInfo generation", (Throwable)e);
                throw new EncryptionException("Error during EncryptedData KeyInfo generation", e);
            }
        }
        for (final KeyEncryptionParameters kekParams : kekParamsList) {
            final EncryptedKey encryptedKey = this.encryptKey(encryptionKey, kekParams, ownerDocument);
            if (encryptedData.getKeyInfo() == null) {
                final KeyInfo keyInfo = this.keyInfoBuilder.buildObject();
                encryptedData.setKeyInfo(keyInfo);
            }
            encryptedData.getKeyInfo().getEncryptedKeys().add(encryptedKey);
        }
        return encryptedData;
    }
    
    protected void checkAndMarshall(final XMLObject xmlObject) throws EncryptionException {
        Element targetElement = xmlObject.getDOM();
        if (targetElement == null) {
            final Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(xmlObject);
            try {
                targetElement = marshaller.marshall(xmlObject);
            }
            catch (MarshallingException e) {
                this.log.error("Error marshalling target XMLObject", (Throwable)e);
                throw new EncryptionException("Error marshalling target XMLObject", e);
            }
        }
    }
    
    protected void checkParams(final EncryptionParameters encParams) throws EncryptionException {
        if (encParams == null) {
            this.log.error("Data encryption parameters are required");
            throw new EncryptionException("Data encryption parameters are required");
        }
        if (DatatypeHelper.isEmpty(encParams.getAlgorithm())) {
            this.log.error("Data encryption algorithm URI is required");
            throw new EncryptionException("Data encryption algorithm URI is required");
        }
    }
    
    protected void checkParams(final KeyEncryptionParameters kekParams, final boolean allowEmpty) throws EncryptionException {
        if (kekParams == null) {
            if (allowEmpty) {
                return;
            }
            this.log.error("Key encryption parameters are required");
            throw new EncryptionException("Key encryption parameters are required");
        }
        else {
            final Key key = SecurityHelper.extractEncryptionKey(kekParams.getEncryptionCredential());
            if (key == null) {
                this.log.error("Key encryption credential and contained key are required");
                throw new EncryptionException("Key encryption credential and contained key are required");
            }
            if (key instanceof DSAPublicKey) {
                this.log.error("Attempt made to use DSA key for encrypted key transport");
                throw new EncryptionException("DSA keys may not be used for encrypted key transport");
            }
            if (DatatypeHelper.isEmpty(kekParams.getAlgorithm())) {
                this.log.error("Key encryption algorithm URI is required");
                throw new EncryptionException("Key encryption algorithm URI is required");
            }
        }
    }
    
    protected void checkParams(final List<KeyEncryptionParameters> kekParamsList, final boolean allowEmpty) throws EncryptionException {
        if (kekParamsList != null && !kekParamsList.isEmpty()) {
            for (final KeyEncryptionParameters kekParams : kekParamsList) {
                this.checkParams(kekParams, false);
            }
            return;
        }
        if (allowEmpty) {
            return;
        }
        this.log.error("Key encryption parameters list may not be empty");
        throw new EncryptionException("Key encryption parameters list may not be empty");
    }
    
    protected void checkParams(final EncryptionParameters encParams, final List<KeyEncryptionParameters> kekParamsList) throws EncryptionException {
        this.checkParams(encParams);
        this.checkParams(kekParamsList, true);
        if (SecurityHelper.extractEncryptionKey(encParams.getEncryptionCredential()) == null && (kekParamsList == null || kekParamsList.isEmpty())) {
            this.log.error("Using a generated encryption key requires a KeyEncryptionParameters object and key encryption key");
            throw new EncryptionException("Using a generated encryption key requires a KeyEncryptionParameters object and key encryption key");
        }
    }
    
    protected SecretKey generateEncryptionKey(final String encryptionAlgorithmURI) throws EncryptionException {
        try {
            this.log.debug("Generating random symmetric data encryption key from algorithm URI: {}", (Object)encryptionAlgorithmURI);
            return SecurityHelper.generateSymmetricKey(encryptionAlgorithmURI);
        }
        catch (NoSuchAlgorithmException e) {
            this.log.error("Could not generate encryption key, algorithm URI was invalid: " + encryptionAlgorithmURI);
            throw new EncryptionException("Could not generate encryption key, algorithm URI was invalid: " + encryptionAlgorithmURI);
        }
        catch (KeyException e2) {
            this.log.error("Could not generate encryption key from algorithm URI: " + encryptionAlgorithmURI);
            throw new EncryptionException("Could not generate encryption key from algorithm URI: " + encryptionAlgorithmURI);
        }
    }
}
