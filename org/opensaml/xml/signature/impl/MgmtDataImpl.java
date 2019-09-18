// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.MgmtData;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class MgmtDataImpl extends XSStringImpl implements MgmtData
{
    protected MgmtDataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
