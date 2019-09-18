// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.validation.Validator;

public class SignatureSchemaValidator implements Validator<Signature>
{
    public void validate(final Signature xmlObject) throws ValidationException {
        this.validateSignatureMethod(xmlObject);
        this.validateCanonicalizationMethod(xmlObject);
    }
    
    protected void validateCanonicalizationMethod(final Signature xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getCanonicalizationAlgorithm())) {
            throw new ValidationException("The CanonicalizationMethod value was empty");
        }
    }
    
    protected void validateSignatureMethod(final Signature xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getSignatureAlgorithm())) {
            throw new ValidationException("The SignatureMethod value was empty");
        }
    }
}
