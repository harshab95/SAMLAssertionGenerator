// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.RSAKeyValue;
import org.opensaml.xml.validation.Validator;

public class RSAKeyValueSchemaValidator implements Validator<RSAKeyValue>
{
    public void validate(final RSAKeyValue xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
    }
    
    protected void validateChildrenPresence(final RSAKeyValue xmlObject) throws ValidationException {
        if (xmlObject.getModulus() == null) {
            throw new ValidationException("RSAKeyValue did not contain a required Modulus value");
        }
        if (xmlObject.getExponent() == null) {
            throw new ValidationException("RSAKeyValue did not contain a required Exponent value");
        }
    }
}
