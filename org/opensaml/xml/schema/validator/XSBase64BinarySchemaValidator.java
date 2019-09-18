// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.validator;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;
import org.opensaml.xml.schema.XSBase64Binary;

public class XSBase64BinarySchemaValidator<T extends XSBase64Binary> implements Validator<T>
{
    private boolean allowEmptyContent;
    
    public XSBase64BinarySchemaValidator(final boolean allowEmptyElementContent) {
        this.allowEmptyContent = allowEmptyElementContent;
    }
    
    public XSBase64BinarySchemaValidator() {
        this.allowEmptyContent = false;
    }
    
    public void validate(final T xmlObject) throws ValidationException {
        this.validateBase64BinaryContent(xmlObject);
    }
    
    protected boolean isAllowEmptyContent() {
        return this.allowEmptyContent;
    }
    
    protected void validateBase64BinaryContent(final T xmlObject) throws ValidationException {
        if (!this.isAllowEmptyContent() && DatatypeHelper.isEmpty(xmlObject.getValue())) {
            throw new ValidationException("Base64Binary content may not be empty");
        }
    }
}
