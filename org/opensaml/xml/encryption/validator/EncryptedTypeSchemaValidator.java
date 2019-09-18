// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.EncryptedType;
import org.opensaml.xml.validation.Validator;

public class EncryptedTypeSchemaValidator implements Validator<EncryptedType>
{
    public void validate(final EncryptedType xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
    }
    
    protected void validateChildrenPresence(final EncryptedType xmlObject) throws ValidationException {
        if (xmlObject.getCipherData() == null) {
            throw new ValidationException("EncryptedType did not contain a CipherData child");
        }
    }
}
