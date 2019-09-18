// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.validation.Validator;

public class ReferenceListSchemaValidator implements Validator<ReferenceList>
{
    public void validate(final ReferenceList xmlObject) throws ValidationException {
        this.validateReferences(xmlObject);
    }
    
    protected void validateReferences(final ReferenceList xmlObject) throws ValidationException {
        if (xmlObject.getReferences().isEmpty()) {
            throw new ValidationException("No DataReference or KeyReference children were present in the ReferenceList object");
        }
    }
}
