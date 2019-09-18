// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.validation.Validator;

public class X509SerialNumberSchemaValidator implements Validator<X509SerialNumber>
{
    public void validate(final X509SerialNumber xmlObject) throws ValidationException {
        this.validateContent(xmlObject);
    }
    
    protected void validateContent(final X509SerialNumber xmlObject) throws ValidationException {
        if (xmlObject.getValue() == null) {
            throw new ValidationException("X509SerialNumber did not contain a value");
        }
    }
}
