// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.Transforms;
import org.opensaml.xml.validation.Validator;

public class TransformsSchemaValidator implements Validator<Transforms>
{
    public void validate(final Transforms xmlObject) throws ValidationException {
        this.validateTransforms(xmlObject);
    }
    
    protected void validateTransforms(final Transforms xmlObject) throws ValidationException {
        if (xmlObject.getTransforms().isEmpty()) {
            throw new ValidationException("No Transform children were present in the Transforms object");
        }
    }
}
