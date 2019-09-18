// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.ReferenceList;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class ReferenceListBuilder extends AbstractXMLObjectBuilder<ReferenceList> implements XMLEncryptionBuilder<ReferenceList>
{
    @Override
    public ReferenceList buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new ReferenceListImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public ReferenceList buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");
    }
}
