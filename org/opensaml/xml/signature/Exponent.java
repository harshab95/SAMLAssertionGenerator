// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;

public interface Exponent extends CryptoBinary
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Exponent";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Exponent", "ds");
}
