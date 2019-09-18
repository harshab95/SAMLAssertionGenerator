// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xml.security.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xml.encryption.KeyEncryptionParameters;
import org.opensaml.xml.encryption.EncryptionParameters;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.Configuration;
import java.util.List;
import org.opensaml.xml.security.keyinfo.BasicProviderKeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.provider.InlineX509DataProvider;
import org.opensaml.xml.security.keyinfo.provider.DSAKeyValueProvider;
import org.opensaml.xml.security.keyinfo.provider.RSAKeyValueProvider;
import org.opensaml.xml.security.keyinfo.KeyInfoProvider;
import java.util.ArrayList;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.security.cert.CertificateException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import org.opensaml.xml.util.Base64;
import java.security.cert.CertificateFactory;
import org.apache.commons.ssl.PKCS8Key;
import java.io.IOException;
import java.io.File;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.DSAPublicKeySpec;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.opensaml.xml.security.x509.BasicX509Credential;
import java.security.cert.X509Certificate;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.opensaml.xml.security.credential.BasicCredential;
import java.security.Key;
import org.opensaml.xml.security.credential.Credential;
import javax.crypto.KeyGenerator;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.opensaml.xml.util.DatatypeHelper;
import org.apache.xml.security.algorithms.JCEMapper;
import java.util.HashSet;
import org.opensaml.xml.util.LazySet;
import org.apache.xml.security.Init;
import java.util.Set;

public final class SecurityHelper
{
    private static Set<String> rsaAlgorithmURIs;
    private static Set<String> dsaAlgorithmURIs;
    private static Set<String> ecdsaAlgorithmURIs;
    
    static {
        if (!Init.isInitialized()) {
            Init.init();
        }
        (SecurityHelper.dsaAlgorithmURIs = new LazySet<String>()).add("http://www.w3.org/2000/09/xmldsig#dsa-sha1");
        (SecurityHelper.ecdsaAlgorithmURIs = new LazySet<String>()).add("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1");
        (SecurityHelper.rsaAlgorithmURIs = new HashSet<String>(10)).add("http://www.w3.org/2000/09/xmldsig#rsa-sha1");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160");
        SecurityHelper.rsaAlgorithmURIs.add("http://www.w3.org/2001/04/xmldsig-more#rsa-md5");
    }
    
    private SecurityHelper() {
    }
    
    public static String getAlgorithmIDFromURI(final String algorithmURI) {
        return DatatypeHelper.safeTrimOrNullString(JCEMapper.translateURItoJCEID(algorithmURI));
    }
    
    public static boolean isHMAC(final String signatureAlgorithm) {
        final String algoClass = DatatypeHelper.safeTrimOrNullString(JCEMapper.getAlgorithmClassFromURI(signatureAlgorithm));
        return "Mac".equals(algoClass);
    }
    
    public static String getKeyAlgorithmFromURI(final String algorithmURI) {
        final String apacheValue = DatatypeHelper.safeTrimOrNullString(JCEMapper.getJCEKeyAlgorithmFromURI(algorithmURI));
        if (apacheValue != null) {
            return apacheValue;
        }
        if (isHMAC(algorithmURI)) {
            return null;
        }
        if (SecurityHelper.rsaAlgorithmURIs.contains(algorithmURI)) {
            return "RSA";
        }
        if (SecurityHelper.dsaAlgorithmURIs.contains(algorithmURI)) {
            return "DSA";
        }
        if (SecurityHelper.ecdsaAlgorithmURIs.contains(algorithmURI)) {
            return "ECDSA";
        }
        return null;
    }
    
    public static Integer getKeyLengthFromURI(final String algorithmURI) {
        final Logger log = getLogger();
        final String algoClass = DatatypeHelper.safeTrimOrNullString(JCEMapper.getAlgorithmClassFromURI(algorithmURI));
        Label_0067: {
            if (!"BlockEncryption".equals(algoClass)) {
                if (!"SymmetricKeyWrap".equals(algoClass)) {
                    break Label_0067;
                }
            }
            try {
                final int keyLength = JCEMapper.getKeyLengthFromURI(algorithmURI);
                return new Integer(keyLength);
            }
            catch (NumberFormatException e) {
                log.warn("XML Security config contained invalid key length value for algorithm URI: " + algorithmURI);
            }
        }
        log.info("Mapping from algorithm URI {} to key length not available", (Object)algorithmURI);
        return null;
    }
    
    public static SecretKey generateSymmetricKey(final String algoURI) throws NoSuchAlgorithmException, KeyException {
        final Logger log = getLogger();
        final String jceAlgorithmName = getKeyAlgorithmFromURI(algoURI);
        if (DatatypeHelper.isEmpty(jceAlgorithmName)) {
            log.error("Mapping from algorithm URI '" + algoURI + "' to key algorithm not available, key generation failed");
            throw new NoSuchAlgorithmException("Algorithm URI'" + algoURI + "' is invalid for key generation");
        }
        final Integer keyLength = getKeyLengthFromURI(algoURI);
        if (keyLength == null) {
            log.error("Key length could not be determined from algorithm URI, can't generate key");
            throw new KeyException("Key length not determinable from algorithm URI, could not generate new key");
        }
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
        keyGenerator.init(keyLength);
        return keyGenerator.generateKey();
    }
    
    public static Key extractEncryptionKey(final Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPublicKey() != null) {
            return credential.getPublicKey();
        }
        return credential.getSecretKey();
    }
    
    public static Key extractDecryptionKey(final Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPrivateKey() != null) {
            return credential.getPrivateKey();
        }
        return credential.getSecretKey();
    }
    
    public static Key extractSigningKey(final Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPrivateKey() != null) {
            return credential.getPrivateKey();
        }
        return credential.getSecretKey();
    }
    
    public static Key extractVerificationKey(final Credential credential) {
        if (credential == null) {
            return null;
        }
        if (credential.getPublicKey() != null) {
            return credential.getPublicKey();
        }
        return credential.getSecretKey();
    }
    
    public static Integer getKeyLength(final Key key) {
        final Logger log = getLogger();
        if (key instanceof SecretKey && "RAW".equals(key.getFormat())) {
            return key.getEncoded().length * 8;
        }
        log.debug("Unable to determine length in bits of specified Key instance");
        return null;
    }
    
    public static BasicCredential getSimpleCredential(final SecretKey secretKey) {
        if (secretKey == null) {
            throw new IllegalArgumentException("A secret key is required");
        }
        final BasicCredential cred = new BasicCredential();
        cred.setSecretKey(secretKey);
        return cred;
    }
    
    public static BasicCredential getSimpleCredential(final PublicKey publicKey, final PrivateKey privateKey) {
        if (publicKey == null) {
            throw new IllegalArgumentException("A public key is required");
        }
        final BasicCredential cred = new BasicCredential();
        cred.setPublicKey(publicKey);
        cred.setPrivateKey(privateKey);
        return cred;
    }
    
    public static BasicX509Credential getSimpleCredential(final X509Certificate cert, final PrivateKey privateKey) {
        if (cert == null) {
            throw new IllegalArgumentException("A certificate is required");
        }
        final BasicX509Credential cred = new BasicX509Credential();
        cred.setEntityCertificate(cert);
        cred.setPrivateKey(privateKey);
        return cred;
    }
    
    public static SecretKey decodeSecretKey(final byte[] key, final char[] password) throws KeyException {
        throw new UnsupportedOperationException("This method is not yet supported");
    }
    
    public static PublicKey decodePublicKey(final byte[] key, final char[] password) throws KeyException {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        try {
            return buildKey(keySpec, "RSA");
        }
        catch (KeyException ex) {
            try {
                return buildKey(keySpec, "DSA");
            }
            catch (KeyException ex2) {
                try {
                    return buildKey(keySpec, "EC");
                }
                catch (KeyException ex3) {
                    throw new KeyException("Unsupported key type.");
                }
            }
        }
    }
    
    public static PublicKey derivePublicKey(final PrivateKey key) throws KeyException {
        if (key instanceof DSAPrivateKey) {
            final DSAPrivateKey dsaKey = (DSAPrivateKey)key;
            final DSAParams keyParams = dsaKey.getParams();
            final BigInteger y = keyParams.getQ().modPow(dsaKey.getX(), keyParams.getP());
            final DSAPublicKeySpec pubKeySpec = new DSAPublicKeySpec(y, keyParams.getP(), keyParams.getQ(), keyParams.getG());
            try {
                final KeyFactory factory = KeyFactory.getInstance("DSA");
                return factory.generatePublic(pubKeySpec);
            }
            catch (GeneralSecurityException e) {
                throw new KeyException("Unable to derive public key from DSA private key", e);
            }
        }
        if (key instanceof RSAPrivateCrtKey) {
            final RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)key;
            final RSAPublicKeySpec pubKeySpec2 = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());
            try {
                final KeyFactory factory = KeyFactory.getInstance("RSA");
                return factory.generatePublic(pubKeySpec2);
            }
            catch (GeneralSecurityException e2) {
                throw new KeyException("Unable to derive public key from RSA private key", e2);
            }
        }
        throw new KeyException("Private key was not a DSA or RSA key");
    }
    
    public static PrivateKey decodePrivateKey(final File key, final char[] password) throws KeyException {
        if (!key.exists()) {
            throw new KeyException("Key file " + key.getAbsolutePath() + " does not exist");
        }
        if (!key.canRead()) {
            throw new KeyException("Key file " + key.getAbsolutePath() + " is not readable");
        }
        try {
            return decodePrivateKey(DatatypeHelper.fileToByteArray(key), password);
        }
        catch (IOException e) {
            throw new KeyException("Error reading Key file " + key.getAbsolutePath(), e);
        }
    }
    
    public static PrivateKey decodePrivateKey(final byte[] key, final char[] password) throws KeyException {
        try {
            final PKCS8Key deocodedKey = new PKCS8Key(key, password);
            return deocodedKey.getPrivateKey();
        }
        catch (GeneralSecurityException e) {
            throw new KeyException("Unable to decode private key", e);
        }
    }
    
    public static X509Certificate buildJavaX509Cert(final String base64Cert) throws CertificateException {
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final ByteArrayInputStream input = new ByteArrayInputStream(Base64.decode(base64Cert));
        return (X509Certificate)cf.generateCertificate(input);
    }
    
    public static X509CRL buildJavaX509CRL(final String base64CRL) throws CertificateException, CRLException {
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final ByteArrayInputStream input = new ByteArrayInputStream(Base64.decode(base64CRL));
        return (X509CRL)cf.generateCRL(input);
    }
    
    public static DSAPublicKey buildJavaDSAPublicKey(final String base64EncodedKey) throws KeyException {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64EncodedKey));
        return (DSAPublicKey)buildKey(keySpec, "DSA");
    }
    
    public static RSAPublicKey buildJavaRSAPublicKey(final String base64EncodedKey) throws KeyException {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64EncodedKey));
        return (RSAPublicKey)buildKey(keySpec, "RSA");
    }
    
    public static ECPublicKey buildJavaECPublicKey(final String base64EncodedKey) throws KeyException {
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64EncodedKey));
        return (ECPublicKey)buildKey(keySpec, "EC");
    }
    
    public static RSAPrivateKey buildJavaRSAPrivateKey(final String base64EncodedKey) throws KeyException {
        final PrivateKey key = buildJavaPrivateKey(base64EncodedKey);
        if (!(key instanceof RSAPrivateKey)) {
            throw new KeyException("Generated key was not an RSAPrivateKey instance");
        }
        return (RSAPrivateKey)key;
    }
    
    public static DSAPrivateKey buildJavaDSAPrivateKey(final String base64EncodedKey) throws KeyException {
        final PrivateKey key = buildJavaPrivateKey(base64EncodedKey);
        if (!(key instanceof DSAPrivateKey)) {
            throw new KeyException("Generated key was not a DSAPrivateKey instance");
        }
        return (DSAPrivateKey)key;
    }
    
    public static PrivateKey buildJavaPrivateKey(final String base64EncodedKey) throws KeyException {
        return decodePrivateKey(Base64.decode(base64EncodedKey), null);
    }
    
    public static PublicKey buildKey(final KeySpec keySpec, final String keyAlgorithm) throws KeyException {
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            return keyFactory.generatePublic(keySpec);
        }
        catch (NoSuchAlgorithmException e) {
            throw new KeyException(String.valueOf(keyAlgorithm) + "algorithm is not supported by the JCE", e);
        }
        catch (InvalidKeySpecException e2) {
            throw new KeyException("Invalid key information", e2);
        }
    }
    
    public static SecretKey generateKeyFromURI(final String algoURI) throws NoSuchAlgorithmException, NoSuchProviderException {
        final String jceAlgorithmName = JCEMapper.getJCEKeyAlgorithmFromURI(algoURI);
        final int keyLength = JCEMapper.getKeyLengthFromURI(algoURI);
        return generateKey(jceAlgorithmName, keyLength, null);
    }
    
    public static KeyPair generateKeyPairFromURI(final String algoURI, final int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
        final String jceAlgorithmName = JCEMapper.getJCEKeyAlgorithmFromURI(algoURI);
        return generateKeyPair(jceAlgorithmName, keyLength, null);
    }
    
    public static SecretKey generateKey(final String algo, final int keyLength, final String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        SecretKey key = null;
        KeyGenerator keyGenerator = null;
        if (provider != null) {
            keyGenerator = KeyGenerator.getInstance(algo, provider);
        }
        else {
            keyGenerator = KeyGenerator.getInstance(algo);
        }
        keyGenerator.init(keyLength);
        key = keyGenerator.generateKey();
        return key;
    }
    
    public static KeyPair generateKeyPair(final String algo, final int keyLength, final String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGenerator = null;
        if (provider != null) {
            keyGenerator = KeyPairGenerator.getInstance(algo, provider);
        }
        else {
            keyGenerator = KeyPairGenerator.getInstance(algo);
        }
        keyGenerator.initialize(keyLength);
        return keyGenerator.generateKeyPair();
    }
    
    public static Credential generateKeyAndCredential(final String algorithmURI) throws NoSuchAlgorithmException, NoSuchProviderException {
        final SecretKey key = generateKeyFromURI(algorithmURI);
        final BasicCredential credential = new BasicCredential();
        credential.setSecretKey(key);
        return credential;
    }
    
    public static Credential generateKeyPairAndCredential(final String algorithmURI, final int keyLength, final boolean includePrivate) throws NoSuchAlgorithmException, NoSuchProviderException {
        final KeyPair keyPair = generateKeyPairFromURI(algorithmURI, keyLength);
        final BasicCredential credential = new BasicCredential();
        credential.setPublicKey(keyPair.getPublic());
        if (includePrivate) {
            credential.setPrivateKey(keyPair.getPrivate());
        }
        return credential;
    }
    
    public static KeyInfoCredentialResolver buildBasicInlineKeyInfoResolver() {
        final List<KeyInfoProvider> providers = new ArrayList<KeyInfoProvider>();
        providers.add(new RSAKeyValueProvider());
        providers.add(new DSAKeyValueProvider());
        providers.add(new InlineX509DataProvider());
        return new BasicProviderKeyInfoCredentialResolver(providers);
    }
    
    public static boolean matchKeyPair(final PublicKey pubKey, final PrivateKey privKey) throws SecurityException {
        final Logger log = getLogger();
        if (pubKey == null || privKey == null) {
            throw new SecurityException("Either public or private key was null");
        }
        final SecurityConfiguration secConfig = Configuration.getGlobalSecurityConfiguration();
        if (secConfig == null) {
            throw new SecurityException("Global security configuration was null, could not resolve signing algorithm");
        }
        final String algoURI = secConfig.getSignatureAlgorithmURI(privKey.getAlgorithm());
        if (algoURI == null) {
            throw new SecurityException("Can't determine algorithm URI from key algorithm: " + privKey.getAlgorithm());
        }
        final String jcaAlgoID = getAlgorithmIDFromURI(algoURI);
        if (jcaAlgoID == null) {
            throw new SecurityException("Can't determine JCA algorithm ID from algorithm URI: " + algoURI);
        }
        if (log.isDebugEnabled()) {
            log.debug("Attempting to match key pair containing key algorithms public '{}' private '{}', using JCA signature algorithm '{}'", new Object[] { pubKey.getAlgorithm(), privKey.getAlgorithm(), jcaAlgoID });
        }
        final byte[] data = "This is the data to sign".getBytes();
        final byte[] signature = SigningUtil.sign(privKey, jcaAlgoID, data);
        return SigningUtil.verify(pubKey, jcaAlgoID, signature, data);
    }
    
    public static void prepareSignatureParams(final Signature signature, final Credential signingCredential, final SecurityConfiguration config, final String keyInfoGenName) throws SecurityException {
        final Logger log = getLogger();
        SecurityConfiguration secConfig;
        if (config != null) {
            secConfig = config;
        }
        else {
            secConfig = Configuration.getGlobalSecurityConfiguration();
        }
        String signAlgo = signature.getSignatureAlgorithm();
        if (signAlgo == null) {
            signAlgo = secConfig.getSignatureAlgorithmURI(signingCredential);
            signature.setSignatureAlgorithm(signAlgo);
        }
        if (isHMAC(signAlgo) && signature.getHMACOutputLength() == null) {
            signature.setHMACOutputLength(secConfig.getSignatureHMACOutputLength());
        }
        if (signature.getCanonicalizationAlgorithm() == null) {
            signature.setCanonicalizationAlgorithm(secConfig.getSignatureCanonicalizationAlgorithm());
        }
        if (signature.getKeyInfo() == null) {
            final KeyInfoGenerator kiGenerator = getKeyInfoGenerator(signingCredential, secConfig, keyInfoGenName);
            if (kiGenerator != null) {
                try {
                    final KeyInfo keyInfo = kiGenerator.generate(signingCredential);
                    signature.setKeyInfo(keyInfo);
                    return;
                }
                catch (SecurityException e) {
                    log.error("Error generating KeyInfo from credential", (Throwable)e);
                    throw e;
                }
            }
            log.info("No factory for named KeyInfoGenerator {} was found for credential type {}", (Object)keyInfoGenName, (Object)signingCredential.getCredentialType().getName());
            log.info("No KeyInfo will be generated for Signature");
        }
    }
    
    public static EncryptionParameters buildDataEncryptionParams(final Credential encryptionCredential, final SecurityConfiguration config, final String keyInfoGenName) {
        final Logger log = getLogger();
        SecurityConfiguration secConfig;
        if (config != null) {
            secConfig = config;
        }
        else {
            secConfig = Configuration.getGlobalSecurityConfiguration();
        }
        final EncryptionParameters encParams = new EncryptionParameters();
        encParams.setEncryptionCredential(encryptionCredential);
        if (encryptionCredential == null) {
            encParams.setAlgorithm(secConfig.getAutoGeneratedDataEncryptionKeyAlgorithmURI());
        }
        else {
            encParams.setAlgorithm(secConfig.getDataEncryptionAlgorithmURI(encryptionCredential));
            final KeyInfoGenerator kiGenerator = getKeyInfoGenerator(encryptionCredential, secConfig, keyInfoGenName);
            if (kiGenerator != null) {
                encParams.setKeyInfoGenerator(kiGenerator);
            }
            else {
                log.info("No factory for named KeyInfoGenerator {} was found for credential type{}", (Object)keyInfoGenName, (Object)encryptionCredential.getCredentialType().getName());
                log.info("No KeyInfo will be generated for EncryptedData");
            }
        }
        return encParams;
    }
    
    public static KeyEncryptionParameters buildKeyEncryptionParams(final Credential encryptionCredential, final String wrappedKeyAlgorithm, final SecurityConfiguration config, final String keyInfoGenName, final String recipient) throws SecurityException {
        final Logger log = getLogger();
        SecurityConfiguration secConfig;
        if (config != null) {
            secConfig = config;
        }
        else {
            secConfig = Configuration.getGlobalSecurityConfiguration();
        }
        final KeyEncryptionParameters kekParams = new KeyEncryptionParameters();
        kekParams.setEncryptionCredential(encryptionCredential);
        if (encryptionCredential == null) {
            throw new SecurityException("Key encryption credential may not be null");
        }
        kekParams.setAlgorithm(secConfig.getKeyTransportEncryptionAlgorithmURI(encryptionCredential, wrappedKeyAlgorithm));
        final KeyInfoGenerator kiGenerator = getKeyInfoGenerator(encryptionCredential, secConfig, keyInfoGenName);
        if (kiGenerator != null) {
            kekParams.setKeyInfoGenerator(kiGenerator);
        }
        else {
            log.info("No factory for named KeyInfoGenerator {} was found for credential type {}", (Object)keyInfoGenName, (Object)encryptionCredential.getCredentialType().getName());
            log.info("No KeyInfo will be generated for EncryptedKey");
        }
        kekParams.setRecipient(recipient);
        return kekParams;
    }
    
    public static KeyInfoGenerator getKeyInfoGenerator(final Credential credential, final SecurityConfiguration config, final String keyInfoGenName) {
        SecurityConfiguration secConfig;
        if (config != null) {
            secConfig = config;
        }
        else {
            secConfig = Configuration.getGlobalSecurityConfiguration();
        }
        final NamedKeyInfoGeneratorManager kiMgr = secConfig.getKeyInfoGeneratorManager();
        if (kiMgr != null) {
            KeyInfoGeneratorFactory kiFactory = null;
            if (DatatypeHelper.isEmpty(keyInfoGenName)) {
                kiFactory = kiMgr.getDefaultManager().getFactory(credential);
            }
            else {
                kiFactory = kiMgr.getFactory(keyInfoGenName, credential);
            }
            if (kiFactory != null) {
                return kiFactory.newInstance();
            }
        }
        return null;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)SecurityHelper.class);
    }
}
