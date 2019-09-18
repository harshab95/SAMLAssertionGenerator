// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface EncryptionProperties extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperties";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionPropertiesType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertiesType", "xenc");
    public static final String ID_ATTRIB_NAME = "Id";
    
    String getID();
    
    void setID(final String p0);
    
    List<EncryptionProperty> getEncryptionProperties();
}
