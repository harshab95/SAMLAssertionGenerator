// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.DataReference;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class DataReferenceBuilder extends AbstractXMLObjectBuilder<DataReference> implements XMLEncryptionBuilder<DataReference>
{
    @Override
    public DataReference buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new DataReferenceImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public DataReference buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DataReference", "xenc");
    }
}
