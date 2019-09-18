// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.CipherData;
import org.opensaml.xml.validation.Validator;

public class CipherDataSchemaValidator implements Validator<CipherData>
{
    public void validate(final CipherData xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
    }
    
    protected void validateChildrenPresence(final CipherData xmlObject) throws ValidationException {
        if (xmlObject.getCipherValue() == null && xmlObject.getCipherReference() == null) {
            throw new ValidationException("CipherData did not contain either a CipherValue or CipherReference child");
        }
        if (xmlObject.getCipherValue() != null && xmlObject.getCipherReference() != null) {
            throw new ValidationException("CipherData contained both a CipherValue and a CipherReference child");
        }
    }
}
