// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.schema.XSBase64Binary;

public interface CipherValue extends XSBase64Binary
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CipherValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherValue", "xenc");
}
