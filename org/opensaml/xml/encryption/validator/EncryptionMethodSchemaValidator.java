// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.EncryptionMethod;
import org.opensaml.xml.validation.Validator;

public class EncryptionMethodSchemaValidator implements Validator<EncryptionMethod>
{
    public void validate(final EncryptionMethod xmlObject) throws ValidationException {
        this.validateAlgorithm(xmlObject);
    }
    
    protected void validateAlgorithm(final EncryptionMethod xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getAlgorithm())) {
            throw new ValidationException("EncryptionMethod algorithm URI was empty");
        }
    }
}
