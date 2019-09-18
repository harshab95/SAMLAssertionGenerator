// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.encryption.XMLEncryptionBuilder;
import org.opensaml.xml.encryption.Generator;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class GeneratorBuilder extends AbstractXMLObjectBuilder<Generator> implements XMLEncryptionBuilder<Generator>
{
    @Override
    public Generator buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new GeneratorImpl(namespaceURI, localName, namespacePrefix);
    }
    
    public Generator buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Generator", "xenc");
    }
}
