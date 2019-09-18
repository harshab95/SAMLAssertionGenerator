// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.credential.Credential;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.PrivateKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyException;
import java.security.interfaces.DSAPublicKey;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class SecurityTestHelper
{
    private SecurityTestHelper() {
    }
    
    @Deprecated
    public static X509Certificate buildJavaX509Cert(final String base64Cert) throws CertificateException {
        return SecurityHelper.buildJavaX509Cert(base64Cert);
    }
    
    @Deprecated
    public static X509CRL buildJavaX509CRL(final String base64CRL) throws CertificateException, CRLException {
        return SecurityHelper.buildJavaX509CRL(base64CRL);
    }
    
    @Deprecated
    public static DSAPublicKey buildJavaDSAPublicKey(final String base64EncodedKey) throws KeyException {
        return SecurityHelper.buildJavaDSAPublicKey(base64EncodedKey);
    }
    
    @Deprecated
    public static RSAPublicKey buildJavaRSAPublicKey(final String base64EncodedKey) throws KeyException {
        return SecurityHelper.buildJavaRSAPublicKey(base64EncodedKey);
    }
    
    @Deprecated
    public static RSAPrivateKey buildJavaRSAPrivateKey(final String base64EncodedKey) throws KeyException {
        return SecurityHelper.buildJavaRSAPrivateKey(base64EncodedKey);
    }
    
    @Deprecated
    public static DSAPrivateKey buildJavaDSAPrivateKey(final String base64EncodedKey) throws KeyException {
        return SecurityHelper.buildJavaDSAPrivateKey(base64EncodedKey);
    }
    
    @Deprecated
    public static PrivateKey buildJavaPrivateKey(final String base64EncodedKey) throws KeyException {
        return SecurityHelper.buildJavaPrivateKey(base64EncodedKey);
    }
    
    @Deprecated
    public static PublicKey buildKey(final KeySpec keySpec, final String keyAlgorithm) throws KeyException {
        return SecurityHelper.buildKey(keySpec, keyAlgorithm);
    }
    
    @Deprecated
    public static SecretKey generateKeyFromURI(final String algoURI) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKeyFromURI(algoURI);
    }
    
    @Deprecated
    public static KeyPair generateKeyPairFromURI(final String algoURI, final int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKeyPairFromURI(algoURI, keyLength);
    }
    
    @Deprecated
    public static SecretKey generateKey(final String algo, final int keyLength, final String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKey(algo, keyLength, provider);
    }
    
    @Deprecated
    public static KeyPair generateKeyPair(final String algo, final int keyLength, final String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKeyPair(algo, keyLength, provider);
    }
    
    @Deprecated
    public static Credential generateKeyAndCredential(final String algorithmURI) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKeyAndCredential(algorithmURI);
    }
    
    @Deprecated
    public static Credential generateKeyPairAndCredential(final String algorithmURI, final int keyLength, final boolean includePrivate) throws NoSuchAlgorithmException, NoSuchProviderException {
        return SecurityHelper.generateKeyPairAndCredential(algorithmURI, keyLength, includePrivate);
    }
    
    @Deprecated
    public static KeyInfoCredentialResolver buildBasicInlineKeyInfoResolver() {
        return SecurityHelper.buildBasicInlineKeyInfoResolver();
    }
}
