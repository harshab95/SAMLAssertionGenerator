// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import javax.xml.namespace.QName;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.validation.Validator;

public class KeyValueSchemaValidator implements Validator<KeyValue>
{
    public void validate(final KeyValue xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
        this.validateExtensionChildNamespace(xmlObject);
    }
    
    protected void validateChildrenPresence(final KeyValue xmlObject) throws ValidationException {
        final List<XMLObject> children = xmlObject.getOrderedChildren();
        if (children == null || children.isEmpty()) {
            throw new ValidationException("No children were present in the KeyValue object");
        }
        if (children.size() > 1) {
            throw new ValidationException("Invalid number of children were present in the KeyValue object");
        }
    }
    
    protected void validateExtensionChildNamespace(final KeyValue xmlObject) throws ValidationException {
        final XMLObject unknownChild = xmlObject.getUnknownXMLObject();
        if (unknownChild == null) {
            return;
        }
        final QName childName = unknownChild.getElementQName();
        if ("http://www.w3.org/2000/09/xmldsig#".equals(childName.getNamespaceURI())) {
            throw new ValidationException("KeyValue contains an illegal child extension element: " + childName);
        }
    }
}
