// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.validator;

import javax.xml.namespace.QName;
import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.encryption.EncryptionProperty;
import org.opensaml.xml.validation.Validator;

public class EncryptionPropertySchemaValidator implements Validator<EncryptionProperty>
{
    public void validate(final EncryptionProperty xmlObject) throws ValidationException {
        this.validateUnknownChildren(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
        this.validateAttributeNamespaces(xmlObject);
    }
    
    protected void validateUnknownChildren(final EncryptionProperty xmlObject) throws ValidationException {
        if (xmlObject.getUnknownXMLObjects().isEmpty()) {
            throw new ValidationException("No children were present in the EncryptionProperty object");
        }
    }
    
    protected void validateChildrenNamespaces(final EncryptionProperty xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getUnknownXMLObjects()) {
            final QName childName = child.getElementQName();
            if ("http://www.w3.org/2001/04/xmlenc#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("EncryptionProperty contains an illegal child extension element: " + childName);
            }
        }
    }
    
    protected void validateAttributeNamespaces(final EncryptionProperty xmlObject) throws ValidationException {
        for (final QName attribName : xmlObject.getUnknownAttributes().keySet()) {
            if (!"http://www.w3.org/XML/1998/namespace".equals(attribName.getNamespaceURI())) {
                throw new ValidationException("EncryptionProperty contains an illegal extension attribute: " + attribName);
            }
        }
    }
}
