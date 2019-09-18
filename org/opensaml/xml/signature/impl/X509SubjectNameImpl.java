// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.signature.X509SubjectName;
import org.opensaml.xml.schema.impl.XSStringImpl;

public class X509SubjectNameImpl extends XSStringImpl implements X509SubjectName
{
    protected X509SubjectNameImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
}
