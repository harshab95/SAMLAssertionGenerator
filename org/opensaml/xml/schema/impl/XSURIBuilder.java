// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSURI;
import org.opensaml.xml.AbstractXMLObjectBuilder;

public class XSURIBuilder extends AbstractXMLObjectBuilder<XSURI>
{
    @Override
    public XSURI buildObject(final String namespaceURI, final String localName, final String namespacePrefix) {
        return new XSURIImpl(namespaceURI, localName, namespacePrefix);
    }
}
