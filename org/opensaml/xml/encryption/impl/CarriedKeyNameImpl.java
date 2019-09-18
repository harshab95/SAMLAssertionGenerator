// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption.impl;

import org.opensaml.xml.encryption.CarriedKeyName;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class CarriedKeyNameImpl extends XSStringImpl implements CarriedKeyName
{
    protected CarriedKeyNameImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
