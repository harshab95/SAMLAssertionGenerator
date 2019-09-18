// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.validation;

import org.opensaml.xml.XMLObject;

public interface Validator<XMLObjectType extends XMLObject>
{
    void validate(final XMLObjectType p0) throws ValidationException;
}
