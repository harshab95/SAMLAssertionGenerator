// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.slf4j.LoggerFactory;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.signature.impl.SignatureImpl;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.apache.xml.security.Init;

public class Signer
{
    static {
        final Logger log = getLogger();
        if (!Init.isInitialized()) {
            log.debug("Initializing XML security library");
            Init.init();
        }
    }
    
    protected Signer() {
    }
    
    public static void signObjects(final List<Signature> xmlObjects) throws SignatureException {
        for (final Signature xmlObject : xmlObjects) {
            signObject(xmlObject);
        }
    }
    
    public static void signObject(final Signature signature) throws SignatureException {
        final Logger log = getLogger();
        try {
            final XMLSignature xmlSignature = ((SignatureImpl)signature).getXMLSignature();
            if (xmlSignature == null) {
                log.error("Unable to compute signature, Signature XMLObject does not have the XMLSignature created during marshalling.");
                throw new SignatureException("XMLObject does not have an XMLSignature instance, unable to compute signature");
            }
            log.debug("Computing signature over XMLSignature object");
            xmlSignature.sign(SecurityHelper.extractSigningKey(signature.getSigningCredential()));
        }
        catch (XMLSecurityException e) {
            log.error("An error occured computing the digital signature", (Throwable)e);
            throw new SignatureException("Signature computation error", (Exception)e);
        }
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)Signer.class);
    }
}
