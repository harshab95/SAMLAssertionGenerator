// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.OAEPparams;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class OAEPparamsBuilder extends AbstractXMLObjectBuilder<OAEPparams> implements XMLEncryptionBuilder<OAEPparams>
{
    @Override
    public OAEPparams buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new OAEPparamsImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public OAEPparams buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "OAEPparams", "xenc");
    }
}
