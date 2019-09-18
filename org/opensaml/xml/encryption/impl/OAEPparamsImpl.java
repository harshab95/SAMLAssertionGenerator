// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.OAEPparams;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class OAEPparamsImpl extends XSBase64BinaryImpl implements OAEPparams
{
    protected OAEPparamsImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
