// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.validation.Validator;

public class RetrievalMethodSchemaValidator implements Validator<RetrievalMethod>
{
    public void validate(final RetrievalMethod xmlObject) throws ValidationException {
        this.validateURI(xmlObject);
    }
    
    protected void validateURI(final RetrievalMethod xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getURI())) {
            throw new ValidationException("RetrievalMethod URI was empty");
        }
    }
}
