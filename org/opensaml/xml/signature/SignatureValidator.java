// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.impl.SignatureImpl;
import java.security.Key;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.security.SecurityHelper;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.Logger;
import org.opensaml.xml.validation.Validator;

public class SignatureValidator implements Validator<Signature>
{
    private final Logger log;
    private Credential validationCredential;
    
    public SignatureValidator(final Credential validatingCredential) {
        this.log = LoggerFactory.getLogger((Class)SignatureValidator.class);
        this.validationCredential = validatingCredential;
    }
    
    public void validate(final Signature signature) throws ValidationException {
        this.log.debug("Attempting to validate signature using key from supplied credential");
        final XMLSignature xmlSig = this.buildSignature(signature);
        final Key validationKey = SecurityHelper.extractVerificationKey(this.validationCredential);
        if (validationKey == null) {
            this.log.debug("Supplied credential contained no key suitable for signature validation");
            throw new ValidationException("No key available to validate signature");
        }
        this.log.debug("Validating signature with signature algorithm URI: {}", (Object)signature.getSignatureAlgorithm());
        this.log.debug("Validation credential key algorithm '{}', key instance class '{}'", (Object)validationKey.getAlgorithm(), (Object)validationKey.getClass().getName());
        try {
            if (xmlSig.checkSignatureValue(validationKey)) {
                this.log.debug("Signature validated with key from supplied credential");
                return;
            }
        }
        catch (XMLSignatureException e) {
            throw new ValidationException("Unable to evaluate key against signature", (Exception)e);
        }
        this.log.debug("Signature did not validate against the credential's key");
        throw new ValidationException("Signature did not validate against the credential's key");
    }
    
    protected XMLSignature buildSignature(final Signature signature) {
        this.log.debug("Creating XMLSignature object");
        return ((SignatureImpl)signature).getXMLSignature();
    }
}
