// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.validator;

import java.util.Iterator;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.signature.PGPKeyPacket;
import org.opensaml.xml.signature.PGPKeyID;
import java.util.HashSet;
import javax.xml.namespace.QName;
import java.util.Set;
import org.opensaml.xml.signature.PGPData;
import org.opensaml.xml.validation.Validator;

public class PGPDataSchemaValidator implements Validator<PGPData>
{
    private static final Set<QName> VALID_DS_CHILD_NAMES;
    
    static {
        (VALID_DS_CHILD_NAMES = new HashSet<QName>(5)).add(PGPKeyID.DEFAULT_ELEMENT_NAME);
        PGPDataSchemaValidator.VALID_DS_CHILD_NAMES.add(PGPKeyPacket.DEFAULT_ELEMENT_NAME);
    }
    
    public void validate(final PGPData xmlObject) throws ValidationException {
        this.validateChildrenPresence(xmlObject);
        this.validateChildrenNamespaces(xmlObject);
    }
    
    protected static Set<QName> getValidDSChildNames() {
        return PGPDataSchemaValidator.VALID_DS_CHILD_NAMES;
    }
    
    protected void validateChildrenPresence(final PGPData xmlObject) throws ValidationException {
        if (xmlObject.getPGPKeyID() == null && xmlObject.getPGPKeyPacket() == null) {
            throw new ValidationException("PGPData must contain at least one of PGPKeyID or PGPKeyPacket");
        }
    }
    
    protected void validateChildrenNamespaces(final PGPData xmlObject) throws ValidationException {
        for (final XMLObject child : xmlObject.getUnknownXMLObjects()) {
            final QName childName = child.getElementQName();
            if (!getValidDSChildNames().contains(childName) && "http://www.w3.org/2000/09/xmldsig#".equals(childName.getNamespaceURI())) {
                throw new ValidationException("PGPData contains an illegal child extension element: " + childName);
            }
        }
    }
}
