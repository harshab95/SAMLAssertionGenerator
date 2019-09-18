// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.validation;

import java.util.List;
import org.opensaml.xml.XMLObject;

public interface ValidatingXMLObject extends XMLObject
{
    List<Validator> getValidators();
    
    void registerValidator(final Validator p0);
    
    void deregisterValidator(final Validator p0);
    
    void validate(final boolean p0) throws ValidationException;
}
