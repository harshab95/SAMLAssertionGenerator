// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;
import org.opensaml.xml.schema.XSInteger;

public class XSIntegerSchemaValidator<T extends XSInteger> implements Validator<T>
{
    private boolean allowEmptyContent;
    
    public XSIntegerSchemaValidator(final boolean allowEmptyElementContent) {
        this.allowEmptyContent = allowEmptyElementContent;
    }
    
    public XSIntegerSchemaValidator() {
        this.allowEmptyContent = false;
    }
    
    public void validate(final T xmlObject) throws ValidationException {
        this.validateIntegerContent(xmlObject);
    }
    
    protected boolean isAllowEmptyContent() {
        return this.allowEmptyContent;
    }
    
    protected void validateIntegerContent(final T xmlObject) throws ValidationException {
        if (!this.isAllowEmptyContent() && xmlObject.getValue() == null) {
            throw new ValidationException("Integer content may not be empty");
        }
    }
}
