// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.MgmtData;
import org.opensaml.xml.signature.SPKIData;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.RetrievalMethod;
import org.opensaml.xml.signature.KeyValue;
import org.opensaml.xml.signature.KeyName;
import java.util.HashSet;
import javax.xml.namespace.QName;
import java.util.Set;
import org.opensaml.xml.signature.KeyInfoType;
import org.opensaml.xml.validation.Validator;

public class KeyInfoTypeSchemaValidator implements Validator<KeyInfoType>
{
    private static final Set<QName> VALID_DS_CHILD_NAMES;
    
    static {
        (VALID_DS_CHILD_NAMES = new HashSet<QName>(10)).add(KeyName.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(KeyValue.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(RetrievalMethod.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(X509Data.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(PGPData.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(SPKIData.DEFAULT_ELEMENT_NAME);
        KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES.add(MgmtData.DEFAULT_ELEMENT_NAME);
    }
    
    public void validate(final KeyInfoType xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
    }
    
    protected static Set<QName> getValidDSChildNames() {
        return KeyInfoTypeSchemaValidator.VALID_DS_CHILD_NAMES;
    }
    
    protected void validateChildrenPresence(final KeyInfoType xmlObject) throws ValidationException {
        if (xmlObject.getXMLObjects().isEmpty()) {
            throw new ValidationException("No children were present in the KeyInfoType object");
        }
    }
    
    protected void validateChildrenNamespaces(final KeyInfoType xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getXMLObjects()) {
            final QName childName = child.getElementQName();
            if (!getValidDSChildNames().contains(childName) && "http://www.w3.org/2000/09/xmldsig#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("KeyInfoType contains an illegal child extension element: " + childName);
            }
        }
    }
}
