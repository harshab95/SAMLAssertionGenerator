// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;
import org.opensaml.xml.schema.XSString;

public class XSStringSchemaValidator<T extends XSString> implements Validator<T>
{
    private boolean allowEmptyContent;
    
    public XSStringSchemaValidator(final boolean allowEmptyElementContent) {
        this.allowEmptyContent = allowEmptyElementContent;
    }
    
    public XSStringSchemaValidator() {
        this.allowEmptyContent = false;
    }
    
    public void validate(final T xmlObject) throws ValidationException {
        this.validateStringContent(xmlObject);
    }
    
    protected boolean isAllowEmptyContent() {
        return this.allowEmptyContent;
    }
    
    protected void validateStringContent(final T xmlObject) throws ValidationException {
        if (!this.isAllowEmptyContent() && DatatypeHelper.isEmpty(xmlObject.getValue())) {
            throw new ValidationException("String content may not be empty");
        }
    }
}
