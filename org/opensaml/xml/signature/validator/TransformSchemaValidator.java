// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.Transform;
import org.opensaml.xml.validation.Validator;

public class TransformSchemaValidator implements Validator<Transform>
{
    public void validate(final Transform xmlObject) throws ValidationException {
        this.validateAlgorithm(xmlObject);
    }
    
    protected void validateAlgorithm(final Transform xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getAlgorithm())) {
            throw new ValidationException("Transform algorithm URI was empty");
        }
    }
}
