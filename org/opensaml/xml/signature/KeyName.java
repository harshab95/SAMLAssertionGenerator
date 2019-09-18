// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.schema.XSString;

public interface KeyName extends XSString
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyName";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName", "ds");
}
