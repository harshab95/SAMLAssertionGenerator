// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.schema.XSBase64Binary;

public interface PGPKeyID extends XSBase64Binary
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PGPKeyID";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID", "ds");
}
