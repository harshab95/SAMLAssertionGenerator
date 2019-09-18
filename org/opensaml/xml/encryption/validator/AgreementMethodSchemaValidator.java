// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.AgreementMethod;
import org.opensaml.xml.validation.Validator;

public class AgreementMethodSchemaValidator implements Validator<AgreementMethod>
{
    public void validate(final AgreementMethod xmlObject) throws ValidationException {
        this.validateAlgorithm(xmlObject);
    }
    
    protected void validateAlgorithm(final AgreementMethod xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getAlgorithm())) {
            throw new ValidationException("AgreementMethod algorithm URI was empty");
        }
    }
}
