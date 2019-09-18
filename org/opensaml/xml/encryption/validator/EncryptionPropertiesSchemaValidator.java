// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.EncryptionProperties;
import org.opensaml.xml.validation.Validator;

public class EncryptionPropertiesSchemaValidator implements Validator<EncryptionProperties>
{
    public void validate(final EncryptionProperties xmlObject) throws ValidationException {
        this.validateTransforms(xmlObject);
    }
    
    protected void validateTransforms(final EncryptionProperties xmlObject) throws ValidationException {
        if (xmlObject.getEncryptionProperties().isEmpty()) {
            throw new ValidationException("No EncryptionProperty children were present in the EncryptionProperties object");
        }
    }
}
