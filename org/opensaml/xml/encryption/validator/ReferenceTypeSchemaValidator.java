// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import javax.xml.namespace.QName;
import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.ReferenceType;
import org.opensaml.xml.validation.Validator;

public class ReferenceTypeSchemaValidator implements Validator<ReferenceType>
{
    public void validate(final ReferenceType xmlObject) throws ValidationException {
        this.validateURI(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
    }
    
    protected void validateURI(final ReferenceType xmlObject) throws ValidationException {
        if (DatatypeHelper.isEmpty(xmlObject.getURI())) {
            throw new ValidationException("ReferenceType URI was empty");
        }
    }
    
    protected void validateChildrenNamespaces(final ReferenceType xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getUnknownXMLObjects()) {
            final QName childName = child.getElementQName();
            if ("http://www.w3.org/2001/04/xmlenc#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("ReferenceType contains an illegal child extension element: " + childName);
            }
        }
    }
}
