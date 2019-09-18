// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import org.opensaml.xml.signature.CryptoBinary;
import org.opensaml.xml.schema.validator.XSBase64BinarySchemaValidator;

public class CryptoBinarySchemaValidator extends XSBase64BinarySchemaValidator<CryptoBinary>
{
    public CryptoBinarySchemaValidator() {
        super(false);
    }
}
