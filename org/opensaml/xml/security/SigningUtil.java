// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.security.PublicKey;
import javax.crypto.Mac;
import java.security.GeneralSecurityException;
import org.bouncycastle.util.encoders.Hex;
import java.security.Signature;
import java.security.Key;
import org.slf4j.Logger;
import java.security.PrivateKey;
import org.opensaml.xml.security.credential.Credential;

public final class SigningUtil
{
    private SigningUtil() {
    }
    
    public static byte[] signWithURI(final Credential signingCredential, final String algorithmURI, final byte[] input) throws SecurityException {
        final String jcaAlgorithmID = SecurityHelper.getAlgorithmIDFromURI(algorithmURI);
        if (jcaAlgorithmID == null) {
            throw new SecurityException("Could not derive JCA algorithm identifier from algorithm URI");
        }
        final boolean isHMAC = SecurityHelper.isHMAC(algorithmURI);
        return sign(signingCredential, jcaAlgorithmID, isHMAC, input);
    }
    
    public static byte[] sign(final Credential signingCredential, final String jcaAlgorithmID, final boolean isMAC, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        final Key signingKey = SecurityHelper.extractSigningKey(signingCredential);
        if (signingKey == null) {
            log.error("No signing key supplied in signing credential for signature computation");
            throw new SecurityException("No signing key supplied in signing credential");
        }
        if (isMAC) {
            return signMAC(signingKey, jcaAlgorithmID, input);
        }
        if (signingKey instanceof PrivateKey) {
            return sign((PrivateKey)signingKey, jcaAlgorithmID, input);
        }
        log.error("No PrivateKey present in signing credential for signature computation");
        throw new SecurityException("No PrivateKey supplied for signing");
    }
    
    public static byte[] sign(final PrivateKey signingKey, final String jcaAlgorithmID, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        log.debug("Computing signature over input using private key of type {} and JCA algorithm ID {}", (Object)signingKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            final Signature signature = Signature.getInstance(jcaAlgorithmID);
            signature.initSign(signingKey);
            signature.update(input);
            final byte[] rawSignature = signature.sign();
            log.debug("Computed signature: {}", (Object)new String(Hex.encode(rawSignature)));
            return rawSignature;
        }
        catch (GeneralSecurityException e) {
            log.error("Error during signature generation", (Throwable)e);
            throw new SecurityException("Error during signature generation", e);
        }
    }
    
    public static byte[] signMAC(final Key signingKey, final String jcaAlgorithmID, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        log.debug("Computing MAC over input using key of type {} and JCA algorithm ID {}", (Object)signingKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            final Mac mac = Mac.getInstance(jcaAlgorithmID);
            mac.init(signingKey);
            mac.update(input);
            final byte[] rawMAC = mac.doFinal();
            log.debug("Computed MAC: {}", (Object)new String(Hex.encode(rawMAC)));
            return rawMAC;
        }
        catch (GeneralSecurityException e) {
            log.error("Error during MAC generation", (Throwable)e);
            throw new SecurityException("Error during MAC generation", e);
        }
    }
    
    public static boolean verifyWithURI(final Credential verificationCredential, final String algorithmURI, final byte[] signature, final byte[] input) throws SecurityException {
        final String jcaAlgorithmID = SecurityHelper.getAlgorithmIDFromURI(algorithmURI);
        if (jcaAlgorithmID == null) {
            throw new SecurityException("Could not derive JCA algorithm identifier from algorithm URI");
        }
        final boolean isHMAC = SecurityHelper.isHMAC(algorithmURI);
        return verify(verificationCredential, jcaAlgorithmID, isHMAC, signature, input);
    }
    
    public static boolean verify(final Credential verificationCredential, final String jcaAlgorithmID, final boolean isMAC, final byte[] signature, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        final Key verificationKey = SecurityHelper.extractVerificationKey(verificationCredential);
        if (verificationKey == null) {
            log.error("No verification key supplied in verification credential for signature verification");
            throw new SecurityException("No verification key supplied in verification credential");
        }
        if (isMAC) {
            return verifyMAC(verificationKey, jcaAlgorithmID, signature, input);
        }
        if (verificationKey instanceof PublicKey) {
            return verify((PublicKey)verificationKey, jcaAlgorithmID, signature, input);
        }
        log.error("No PublicKey present in verification credential for signature verification");
        throw new SecurityException("No PublicKey supplied for signature verification");
    }
    
    public static boolean verify(final PublicKey verificationKey, final String jcaAlgorithmID, final byte[] signature, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        log.debug("Verifying signature over input using public key of type {} and JCA algorithm ID {}", (Object)verificationKey.getAlgorithm(), (Object)jcaAlgorithmID);
        try {
            final Signature sig = Signature.getInstance(jcaAlgorithmID);
            sig.initVerify(verificationKey);
            sig.update(input);
            return sig.verify(signature);
        }
        catch (GeneralSecurityException e) {
            log.error("Error during signature verification", (Throwable)e);
            throw new SecurityException("Error during signature verification", e);
        }
    }
    
    public static boolean verifyMAC(final Key verificationKey, final String jcaAlgorithmID, final byte[] signature, final byte[] input) throws SecurityException {
        final Logger log = getLogger();
        log.debug("Verifying MAC over input using key of type {} and JCA algorithm ID {}", (Object)verificationKey.getAlgorithm(), (Object)jcaAlgorithmID);
        final byte[] computed = signMAC(verificationKey, jcaAlgorithmID, input);
        return Arrays.equals(computed, signature);
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)SigningUtil.class);
    }
}
