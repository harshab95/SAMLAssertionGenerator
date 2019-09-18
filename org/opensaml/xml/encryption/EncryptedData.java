// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;

public interface EncryptedData extends EncryptedType
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptedDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedDataType", "xenc");
}
