// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;

public interface EncryptedKey extends EncryptedType
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedKey";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptedKeyType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKeyType", "xenc");
    public static final String RECIPIENT_ATTRIB_NAME = "Recipient";
    
    String getRecipient();
    
    void setRecipient(final String p0);
    
    ReferenceList getReferenceList();
    
    void setReferenceList(final ReferenceList p0);
    
    CarriedKeyName getCarriedKeyName();
    
    void setCarriedKeyName(final CarriedKeyName p0);
}
