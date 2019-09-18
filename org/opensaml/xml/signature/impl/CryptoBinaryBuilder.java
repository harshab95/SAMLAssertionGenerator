// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.signature.CryptoBinary;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class CryptoBinaryBuilder extends AbstractXMLObjectBuilder<CryptoBinary> implements XMLObjectBuilder<CryptoBinary>
{
    @Override
    public CryptoBinary buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new CryptoBinaryImpl(namespaceURI, localName, namespacePrefix);
    }
}
