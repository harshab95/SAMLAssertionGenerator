// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;
import org.opensaml.xml.schema.XSDateTime;

public class XSDateTimeSchemaValidator<T extends XSDateTime> implements Validator<T>
{
    private boolean allowEmptyContent;
    
    public XSDateTimeSchemaValidator(final boolean allowEmptyContent) {
        this.allowEmptyContent = allowEmptyContent;
    }
    
    public XSDateTimeSchemaValidator() {
        this.allowEmptyContent = false;
    }
    
    protected boolean isAllowEmptyContent() {
        return this.allowEmptyContent;
    }
    
    public void validate(final T xmlObject) throws ValidationException {
        this.validateDateTimeContent(xmlObject);
    }
    
    protected void validateDateTimeContent(final T xmlObject) throws ValidationException {
        if (!this.allowEmptyContent && xmlObject.getValue() == null) {
            throw new ValidationException("dateTime content may not be empty");
        }
    }
}
