// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import org.opensaml.xml.encryption.KeySize;
import org.opensaml.xml.schema.validator.XSIntegerSchemaValidator;

public class KeySizeSchemaValidator extends XSIntegerSchemaValidator<KeySize>
{
    public KeySizeSchemaValidator() {
        super(false);
    }
}
