// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.util.ClassIndexedSet;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.criteria.KeyAlgorithmCriteria;
import org.opensaml.xml.security.criteria.KeyLengthCriteria;
import java.util.HashSet;
import java.util.Collections;
import java.util.Set;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.UsageCriteria;
import org.opensaml.xml.security.Criteria;
import java.util.Collection;
import org.opensaml.xml.security.keyinfo.KeyInfoCriteria;
import org.opensaml.xml.signature.DigestMethod;
import java.util.Iterator;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.encryption.XMLCipher;
import java.security.Key;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.DocumentFragment;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Node;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Element;
import java.util.LinkedList;
import java.util.List;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.Configuration;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import org.apache.xml.security.Init;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.parse.BasicParserPool;

public class Decrypter
{
    private final BasicParserPool parserPool;
    private UnmarshallerFactory unmarshallerFactory;
    private final Logger log;
    private KeyInfoCredentialResolver resolver;
    private KeyInfoCredentialResolver kekResolver;
    private EncryptedKeyResolver encKeyResolver;
    private CriteriaSet resolverCriteria;
    private CriteriaSet kekResolverCriteria;
    private String jcaProviderName;
    private boolean defaultRootInNewDocument;
    
    static {
        if (!Init.isInitialized()) {
            Init.init();
        }
    }
    
    public Decrypter(final KeyInfoCredentialResolver newResolver, final KeyInfoCredentialResolver newKEKResolver, final EncryptedKeyResolver newEncKeyResolver) {
        this.log = LoggerFactory.getLogger((Class)Decrypter.class);
        this.resolver = newResolver;
        this.kekResolver = newKEKResolver;
        this.encKeyResolver = newEncKeyResolver;
        this.resolverCriteria = null;
        this.kekResolverCriteria = null;
        (this.parserPool = new BasicParserPool()).setNamespaceAware(true);
        final HashMap<String, Boolean> features = new HashMap<String, Boolean>();
        features.put("http://apache.org/xml/features/dom/defer-node-expansion", Boolean.FALSE);
        this.parserPool.setBuilderFeatures(features);
        this.unmarshallerFactory = Configuration.getUnmarshallerFactory();
        this.defaultRootInNewDocument = false;
    }
    
    public boolean isRootInNewDocument() {
        return this.defaultRootInNewDocument;
    }
    
    public void setRootInNewDocument(final boolean flag) {
        this.defaultRootInNewDocument = flag;
    }
    
    public String getJCAProviderName() {
        return this.jcaProviderName;
    }
    
    public void setJCAProviderName(final String providerName) {
        this.jcaProviderName = providerName;
    }
    
    public KeyInfoCredentialResolver getKeyResolver() {
        return this.resolver;
    }
    
    public void setKeyResolver(final KeyInfoCredentialResolver newResolver) {
        this.resolver = newResolver;
    }
    
    public KeyInfoCredentialResolver getKEKResolver() {
        return this.kekResolver;
    }
    
    public void setKEKResolver(final KeyInfoCredentialResolver newKEKResolver) {
        this.kekResolver = newKEKResolver;
    }
    
    public EncryptedKeyResolver getEncryptedKeyResolver() {
        return this.encKeyResolver;
    }
    
    public void setEncryptedKeyResolver(final EncryptedKeyResolver newResolver) {
        this.encKeyResolver = newResolver;
    }
    
    public CriteriaSet setKeyResolverCriteria() {
        return this.resolverCriteria;
    }
    
    public void setKeyResolverCriteria(final CriteriaSet newCriteria) {
        this.resolverCriteria = newCriteria;
    }
    
    public CriteriaSet getKEKResolverCriteria() {
        return this.kekResolverCriteria;
    }
    
    public void setKEKResolverCriteria(final CriteriaSet newCriteria) {
        this.kekResolverCriteria = newCriteria;
    }
    
    public XMLObject decryptData(final EncryptedData encryptedData) throws DecryptionException {
        return this.decryptData(encryptedData, this.isRootInNewDocument());
    }
    
    public XMLObject decryptData(final EncryptedData encryptedData, final boolean rootInNewDocument) throws DecryptionException {
        final List<XMLObject> xmlObjects = this.decryptDataToList(encryptedData, rootInNewDocument);
        if (xmlObjects.size() != 1) {
            this.log.error("The decrypted data contained more than one top-level XMLObject child");
            throw new DecryptionException("The decrypted data contained more than one XMLObject child");
        }
        return xmlObjects.get(0);
    }
    
    public List<XMLObject> decryptDataToList(final EncryptedData encryptedData) throws DecryptionException {
        return this.decryptDataToList(encryptedData, this.isRootInNewDocument());
    }
    
    public List<XMLObject> decryptDataToList(final EncryptedData encryptedData, final boolean rootInNewDocument) throws DecryptionException {
        final List<XMLObject> xmlObjects = new LinkedList<XMLObject>();
        final DocumentFragment docFragment = this.decryptDataToDOM(encryptedData);
        final NodeList children = docFragment.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            final Node node = children.item(i);
            if (node.getNodeType() != 1) {
                this.log.error("Decryption returned a top-level node that was not of type Element: " + node.getNodeType());
                throw new DecryptionException("Top-level node was not of type Element");
            }
            final Element element = (Element)node;
            if (rootInNewDocument) {
                Document newDoc = null;
                try {
                    newDoc = this.parserPool.newDocument();
                }
                catch (XMLParserException e) {
                    this.log.error("There was an error creating a new DOM Document", (Throwable)e);
                    throw new DecryptionException("Error creating new DOM Document", e);
                }
                newDoc.adoptNode(element);
                newDoc.appendChild(element);
            }
            XMLObject xmlObject;
            try {
                xmlObject = this.unmarshallerFactory.getUnmarshaller(element).unmarshall(element);
            }
            catch (UnmarshallingException e2) {
                this.log.error("There was an error during unmarshalling of the decrypted element", (Throwable)e2);
                throw new DecryptionException("Unmarshalling error during decryption", e2);
            }
            xmlObjects.add(xmlObject);
        }
        return xmlObjects;
    }
    
    public DocumentFragment decryptDataToDOM(final EncryptedData encryptedData) throws DecryptionException {
        if (this.resolver == null && this.encKeyResolver == null) {
            this.log.error("Decryption can not be attempted, required resolvers are not available");
            throw new DecryptionException("Unable to decrypt EncryptedData, required resolvers are not available");
        }
        DocumentFragment docFrag = null;
        if (this.resolver != null) {
            docFrag = this.decryptUsingResolvedKey(encryptedData);
            if (docFrag != null) {
                return docFrag;
            }
            this.log.debug("Failed to decrypt EncryptedData using standard KeyInfo resolver");
        }
        final String algorithm = encryptedData.getEncryptionMethod().getAlgorithm();
        if (DatatypeHelper.isEmpty(algorithm)) {
            final String msg = "EncryptedData's EncryptionMethod Algorithm attribute was empty, key decryption could not be attempted";
            this.log.error(msg);
            throw new DecryptionException(msg);
        }
        if (this.encKeyResolver != null) {
            docFrag = this.decryptUsingResolvedEncryptedKey(encryptedData, algorithm);
            if (docFrag != null) {
                return docFrag;
            }
            this.log.debug("Failed to decrypt EncryptedData using EncryptedKeyResolver");
        }
        this.log.error("Failed to decrypt EncryptedData using either EncryptedData KeyInfoCredentialResolver or EncryptedKeyResolver + EncryptedKey KeyInfoCredentialResolver");
        throw new DecryptionException("Failed to decrypt EncryptedData");
    }
    
    public DocumentFragment decryptDataToDOM(final EncryptedData encryptedData, final Key dataEncKey) throws DecryptionException {
        if (!"http://www.w3.org/2001/04/xmlenc#Element".equals(encryptedData.getType())) {
            this.log.error("EncryptedData was of unsupported type '" + encryptedData.getType() + "', could not attempt decryption");
            throw new DecryptionException("EncryptedData of unsupported type was encountered");
        }
        if (dataEncKey == null) {
            this.log.error("Data decryption key was null");
            throw new IllegalArgumentException("Data decryption key may not be null");
        }
        try {
            this.checkAndMarshall(encryptedData);
        }
        catch (DecryptionException e) {
            this.log.error("Error marshalling EncryptedData for decryption", (Throwable)e);
            throw e;
        }
        final Element targetElement = encryptedData.getDOM();
        XMLCipher xmlCipher;
        try {
            if (this.getJCAProviderName() != null) {
                xmlCipher = XMLCipher.getProviderInstance(this.getJCAProviderName());
            }
            else {
                xmlCipher = XMLCipher.getInstance();
            }
            xmlCipher.init(2, dataEncKey);
        }
        catch (XMLEncryptionException e2) {
            this.log.error("Error initialzing cipher instance on data decryption", (Throwable)e2);
            throw new DecryptionException("Error initialzing cipher instance on data decryption", (Exception)e2);
        }
        byte[] bytes = null;
        try {
            bytes = xmlCipher.decryptToByteArray(targetElement);
        }
        catch (XMLEncryptionException e3) {
            this.log.error("Error decrypting the encrypted data element", (Throwable)e3);
            throw new DecryptionException("Error decrypting the encrypted data element", (Exception)e3);
        }
        if (bytes == null) {
            throw new DecryptionException("EncryptedData could not be decrypted");
        }
        final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        final DocumentFragment docFragment = this.parseInputStream(input, encryptedData.getDOM().getOwnerDocument());
        return docFragment;
    }
    
    public Key decryptKey(final EncryptedKey encryptedKey, final String algorithm) throws DecryptionException {
        if (this.kekResolver == null) {
            this.log.warn("No KEK KeyInfo credential resolver is available, can not attempt EncryptedKey decryption");
            throw new DecryptionException("No KEK KeyInfo resolver is available for EncryptedKey decryption");
        }
        if (DatatypeHelper.isEmpty(algorithm)) {
            this.log.error("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
            throw new DecryptionException("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
        }
        final CriteriaSet criteriaSet = this.buildCredentialCriteria(encryptedKey, this.kekResolverCriteria);
        try {
            for (final Credential cred : this.kekResolver.resolve(criteriaSet)) {
                try {
                    return this.decryptKey(encryptedKey, algorithm, SecurityHelper.extractDecryptionKey(cred));
                }
                catch (DecryptionException e) {
                    final String msg = "Attempt to decrypt EncryptedKey using credential from KEK KeyInfo resolver failed: ";
                    this.log.debug(msg, (Throwable)e);
                }
            }
        }
        catch (SecurityException e2) {
            this.log.error("Error resolving credentials from EncryptedKey KeyInfo", (Throwable)e2);
        }
        this.log.error("Failed to decrypt EncryptedKey, valid decryption key could not be resolved");
        throw new DecryptionException("Valid decryption key for EncryptedKey could not be resolved");
    }
    
    public Key decryptKey(final EncryptedKey encryptedKey, final String algorithm, final Key kek) throws DecryptionException {
        if (kek == null) {
            this.log.error("Data encryption key was null");
            throw new IllegalArgumentException("Data encryption key may not be null");
        }
        if (DatatypeHelper.isEmpty(algorithm)) {
            this.log.error("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
            throw new DecryptionException("Algorithm of encrypted key not supplied, key decryption cannot proceed.");
        }
        try {
            this.checkAndMarshall(encryptedKey);
        }
        catch (DecryptionException e) {
            this.log.error("Error marshalling EncryptedKey for decryption", (Throwable)e);
            throw e;
        }
        this.preProcessEncryptedKey(encryptedKey, algorithm, kek);
        final Element targetElement = encryptedKey.getDOM();
        XMLCipher xmlCipher;
        try {
            if (this.getJCAProviderName() != null) {
                xmlCipher = XMLCipher.getProviderInstance(this.getJCAProviderName());
            }
            else {
                xmlCipher = XMLCipher.getInstance();
            }
            xmlCipher.init(4, kek);
        }
        catch (XMLEncryptionException e2) {
            this.log.error("Error initialzing cipher instance on key decryption", (Throwable)e2);
            throw new DecryptionException("Error initialzing cipher instance on key decryption", (Exception)e2);
        }
        org.apache.xml.security.encryption.EncryptedKey encKey;
        try {
            encKey = xmlCipher.loadEncryptedKey(targetElement.getOwnerDocument(), targetElement);
        }
        catch (XMLEncryptionException e3) {
            this.log.error("Error when loading library native encrypted key representation", (Throwable)e3);
            throw new DecryptionException("Error when loading library native encrypted key representation", (Exception)e3);
        }
        Key key = null;
        try {
            key = xmlCipher.decryptKey(encKey, algorithm);
        }
        catch (XMLEncryptionException e4) {
            this.log.error("Error decrypting encrypted key", (Throwable)e4);
            throw new DecryptionException("Error decrypting encrypted key", (Exception)e4);
        }
        if (key == null) {
            throw new DecryptionException("Key could not be decrypted");
        }
        return key;
    }
    
    protected void preProcessEncryptedKey(final EncryptedKey encryptedKey, final String algorithm, final Key kek) throws DecryptionException {
        final String keyTransportAlgorithm = encryptedKey.getEncryptionMethod().getAlgorithm();
        if ("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(keyTransportAlgorithm)) {
            final List<XMLObject> digestMethods = encryptedKey.getEncryptionMethod().getUnknownXMLObjects(DigestMethod.DEFAULT_ELEMENT_NAME);
            if (!digestMethods.isEmpty()) {
                final DigestMethod dm = digestMethods.get(0);
                if (!"http://www.w3.org/2000/09/xmldsig#sha1".equals(DatatypeHelper.safeTrimOrNullString(dm.getAlgorithm()))) {
                    this.log.error("EncryptedKey/EncryptionMethod/DigestMethod contains unsupported algorithm URI: {}", (Object)dm.getAlgorithm());
                    throw new DecryptionException("EncryptedKey/EncryptionMethod/DigestMethod contains unsupported algorithm URI");
                }
            }
        }
    }
    
    private DocumentFragment decryptUsingResolvedKey(final EncryptedData encryptedData) {
        if (this.resolver != null) {
            final CriteriaSet criteriaSet = this.buildCredentialCriteria(encryptedData, this.resolverCriteria);
            try {
                for (final Credential cred : this.resolver.resolve(criteriaSet)) {
                    try {
                        return this.decryptDataToDOM(encryptedData, SecurityHelper.extractDecryptionKey(cred));
                    }
                    catch (DecryptionException e) {
                        final String msg = "Decryption attempt using credential from standard KeyInfo resolver failed: ";
                        this.log.debug(msg, (Throwable)e);
                    }
                }
            }
            catch (SecurityException e2) {
                this.log.error("Error resolving credentials from EncryptedData KeyInfo", (Throwable)e2);
            }
        }
        return null;
    }
    
    private DocumentFragment decryptUsingResolvedEncryptedKey(final EncryptedData encryptedData, final String algorithm) {
        if (this.encKeyResolver != null) {
            for (final EncryptedKey encryptedKey : this.encKeyResolver.resolve(encryptedData)) {
                try {
                    final Key decryptedKey = this.decryptKey(encryptedKey, algorithm);
                    return this.decryptDataToDOM(encryptedData, decryptedKey);
                }
                catch (DecryptionException e) {
                    final String msg = "Attempt to decrypt EncryptedData using key extracted from EncryptedKey failed: ";
                    this.log.debug(msg, (Throwable)e);
                }
            }
        }
        return null;
    }
    
    private DocumentFragment parseInputStream(final InputStream input, final Document owningDocument) throws DecryptionException {
        Document newDocument = null;
        try {
            newDocument = this.parserPool.parse(input);
        }
        catch (XMLParserException e) {
            this.log.error("Error parsing decrypted input stream", (Throwable)e);
            throw new DecryptionException("Error parsing input stream", e);
        }
        final Element element = newDocument.getDocumentElement();
        owningDocument.adoptNode(element);
        final DocumentFragment container = owningDocument.createDocumentFragment();
        container.appendChild(element);
        return container;
    }
    
    private CriteriaSet buildCredentialCriteria(final EncryptedType encryptedType, final CriteriaSet staticCriteria) {
        final CriteriaSet newCriteriaSet = new CriteriaSet();
        ((ClassIndexedSet<KeyInfoCriteria>)newCriteriaSet).add(new KeyInfoCriteria(encryptedType.getKeyInfo()));
        final Set<Criteria> keyCriteria = this.buildKeyCriteria(encryptedType);
        if (keyCriteria != null && !keyCriteria.isEmpty()) {
            newCriteriaSet.addAll(keyCriteria);
        }
        if (staticCriteria != null && !staticCriteria.isEmpty()) {
            newCriteriaSet.addAll(staticCriteria);
        }
        if (!newCriteriaSet.contains(UsageCriteria.class)) {
            ((ClassIndexedSet<UsageCriteria>)newCriteriaSet).add(new UsageCriteria(UsageType.ENCRYPTION));
        }
        return newCriteriaSet;
    }
    
    private Set<Criteria> buildKeyCriteria(final EncryptedType encryptedType) {
        final EncryptionMethod encMethod = encryptedType.getEncryptionMethod();
        if (encMethod == null) {
            return Collections.emptySet();
        }
        final String encAlgorithmURI = DatatypeHelper.safeTrimOrNullString(encMethod.getAlgorithm());
        if (encAlgorithmURI == null) {
            return Collections.emptySet();
        }
        final Set<Criteria> critSet = new HashSet<Criteria>(2);
        final KeyAlgorithmCriteria algoCrit = this.buildKeyAlgorithmCriteria(encAlgorithmURI);
        if (algoCrit != null) {
            critSet.add(algoCrit);
            this.log.debug("Added decryption key algorithm criteria: {}", (Object)algoCrit.getKeyAlgorithm());
        }
        KeyLengthCriteria lengthCrit = this.buildKeyLengthCriteria(encAlgorithmURI);
        if (lengthCrit != null) {
            critSet.add(lengthCrit);
            this.log.debug("Added decryption key length criteria from EncryptionMethod algorithm URI: {}", (Object)lengthCrit.getKeyLength());
        }
        else if (encMethod.getKeySize() != null && encMethod.getKeySize().getValue() != null) {
            lengthCrit = new KeyLengthCriteria(encMethod.getKeySize().getValue());
            critSet.add(lengthCrit);
            this.log.debug("Added decryption key length criteria from EncryptionMethod/KeySize: {}", (Object)lengthCrit.getKeyLength());
        }
        return critSet;
    }
    
    private KeyAlgorithmCriteria buildKeyAlgorithmCriteria(final String encAlgorithmURI) {
        if (DatatypeHelper.isEmpty(encAlgorithmURI)) {
            return null;
        }
        final String jcaKeyAlgorithm = SecurityHelper.getKeyAlgorithmFromURI(encAlgorithmURI);
        if (!DatatypeHelper.isEmpty(jcaKeyAlgorithm)) {
            return new KeyAlgorithmCriteria(jcaKeyAlgorithm);
        }
        return null;
    }
    
    private KeyLengthCriteria buildKeyLengthCriteria(final String encAlgorithmURI) {
        if (!DatatypeHelper.isEmpty(encAlgorithmURI)) {
            return null;
        }
        final Integer keyLength = SecurityHelper.getKeyLengthFromURI(encAlgorithmURI);
        if (keyLength != null) {
            return new KeyLengthCriteria(keyLength);
        }
        return null;
    }
    
    protected void checkAndMarshall(final XMLObject xmlObject) throws DecryptionException {
        Element targetElement = xmlObject.getDOM();
        if (targetElement == null) {
            final Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(xmlObject);
            try {
                targetElement = marshaller.marshall(xmlObject);
            }
            catch (MarshallingException e) {
                this.log.error("Error marshalling target XMLObject", (Throwable)e);
                throw new DecryptionException("Error marshalling target XMLObject", e);
            }
        }
    }
}
