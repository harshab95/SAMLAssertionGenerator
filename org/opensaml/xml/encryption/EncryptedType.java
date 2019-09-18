// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.signature.KeyInfo;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface EncryptedType extends ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "EncryptedType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedType", "xenc");
    public static final String ID_ATTRIB_NAME = "Id";
    public static final String TYPE_ATTRIB_NAME = "Type";
    public static final String MIMETYPE_ATTRIB_NAME = "MimeType";
    public static final String ENCODING_ATTRIB_NAME = "Encoding";
    
    String getID();
    
    void setID(final String p0);
    
    String getType();
    
    void setType(final String p0);
    
    String getMimeType();
    
    void setMimeType(final String p0);
    
    String getEncoding();
    
    void setEncoding(final String p0);
    
    EncryptionMethod getEncryptionMethod();
    
    void setEncryptionMethod(final EncryptionMethod p0);
    
    KeyInfo getKeyInfo();
    
    void setKeyInfo(final KeyInfo p0);
    
    CipherData getCipherData();
    
    void setCipherData(final CipherData p0);
    
    EncryptionProperties getEncryptionProperties();
    
    void setEncryptionProperties(final EncryptionProperties p0);
}
