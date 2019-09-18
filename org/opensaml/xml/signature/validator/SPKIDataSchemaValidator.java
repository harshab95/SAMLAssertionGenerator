// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import javax.xml.namespace.QName;
import java.util.Iterator;
import org.opensaml.xml.signature.SPKISexp;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.validation.Validator;

public class SPKIDataSchemaValidator implements Validator<SPKIData>
{
    public void validate(final SPKIData xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
    }
    
    protected void validateChildrenPresence(final SPKIData xmlObject) throws ValidationException {
        if (xmlObject.getSPKISexps().isEmpty()) {
            throw new ValidationException("SPKIData does not contain at least one SPKISexp child");
        }
    }
    
    protected void validateChildrenNamespaces(final SPKIData xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getXMLObjects()) {
            final QName childName = child.getElementQName();
            if (!SPKISexp.DEFAULT_ELEMENT_NAME.equals(childName) && "http://www.w3.org/2000/09/xmldsig#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("PGPData contains an illegal child extension element: " + childName);
            }
        }
    }
}
