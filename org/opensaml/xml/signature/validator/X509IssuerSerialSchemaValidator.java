// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.X509IssuerSerial;
import org.opensaml.xml.validation.Validator;

public class X509IssuerSerialSchemaValidator implements Validator<X509IssuerSerial>
{
    public void validate(final X509IssuerSerial xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
    }
    
    protected void validateChildrenPresence(final X509IssuerSerial xmlObject) throws ValidationException {
        if (xmlObject.getX509IssuerName() == null) {
            throw new ValidationException("X509IssuerSerial does not contain an X509IssuerName");
        }
        if (xmlObject.getX509SerialNumber() == null) {
            throw new ValidationException("X509IssuerSerial does not contain an X509SerialNumber");
        }
    }
}
