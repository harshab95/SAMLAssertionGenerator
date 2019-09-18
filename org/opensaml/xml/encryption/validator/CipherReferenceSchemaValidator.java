// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.CipherReference;
import org.opensaml.xml.validation.Validator;

public class CipherReferenceSchemaValidator implements Validator<CipherReference>
{
    public void validate(final CipherReference xmlObject) throws ValidationException {
        this.validateURI(xmlObject);
    }
    
    protected void validateURI(final CipherReference xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getURI())) {
            throw new ValidationException("CipherReference URI was empty");
        }
    }
}
