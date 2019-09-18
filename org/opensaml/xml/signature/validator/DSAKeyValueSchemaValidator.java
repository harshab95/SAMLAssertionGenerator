// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.validation.Validator;

public class DSAKeyValueSchemaValidator implements Validator<DSAKeyValue>
{
    public void validate(final DSAKeyValue xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
    }
    
    protected void validateChildrenPresence(final DSAKeyValue xmlObject) throws ValidationException {
        if (xmlObject.getY() == null) {
            throw new ValidationException("DSAKeyValue did not contain a required Y value");
        }
        if (xmlObject.getP() != null && xmlObject.getQ() == null) {
            throw new ValidationException("RSAKeyValue did contained a P value without a Q value");
        }
        if (xmlObject.getQ() != null && xmlObject.getP() == null) {
            throw new ValidationException("RSAKeyValue did contained a Q value without a P value");
        }
        if (xmlObject.getPgenCounter() != null && xmlObject.getSeed() == null) {
            throw new ValidationException("RSAKeyValue did contained a PgenCounter value without a Seed value");
        }
        if (xmlObject.getSeed() != null && xmlObject.getPgenCounter() == null) {
            throw new ValidationException("RSAKeyValue did contained a Seed value without a PgenCounter value");
        }
    }
}
