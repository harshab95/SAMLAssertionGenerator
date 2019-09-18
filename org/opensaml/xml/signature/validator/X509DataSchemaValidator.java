// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.X509CRL;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509SubjectName;
import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.signature.X509IssuerSerial;
import java.util.HashSet;
import javax.xml.namespace.QName;
import java.util.Set;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.validation.Validator;

public class X509DataSchemaValidator implements Validator<X509Data>
{
    private static final Set<QName> VALID_DS_CHILD_NAMES;
    
    static {
        (VALID_DS_CHILD_NAMES = new HashSet<QName>(10)).add(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
        X509DataSchemaValidator.VALID_DS_CHILD_NAMES.add(X509SKI.DEFAULT_ELEMENT_NAME);
        X509DataSchemaValidator.VALID_DS_CHILD_NAMES.add(X509SubjectName.DEFAULT_ELEMENT_NAME);
        X509DataSchemaValidator.VALID_DS_CHILD_NAMES.add(X509Certificate.DEFAULT_ELEMENT_NAME);
        X509DataSchemaValidator.VALID_DS_CHILD_NAMES.add(X509CRL.DEFAULT_ELEMENT_NAME);
    }
    
    public void validate(final X509Data xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
    }
    
    protected static Set<QName> getValidDSChildNames() {
        return X509DataSchemaValidator.VALID_DS_CHILD_NAMES;
    }
    
    protected void validateChildrenPresence(final X509Data xmlObject) throws ValidationException {
        if (xmlObject.getXMLObjects().isEmpty()) {
            throw new ValidationException("No children were present in the X509Data object");
        }
    }
    
    protected void validateChildrenNamespaces(final X509Data xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getXMLObjects()) {
            final QName childName = child.getElementQName();
            if (!getValidDSChildNames().contains(childName) && "http://www.w3.org/2000/09/xmldsig#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("X509Data contains an illegal child extension element: " + childName);
            }
        }
    }
}
