// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.AttributeExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface EncryptionProperty extends ValidatingXMLObject, AttributeExtensibleXMLObject, ElementExtensibleXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperty";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionPropertyType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertyType", "xenc");
    public static final String TARGET_ATTRIB_NAME = "Target";
    public static final String ID_ATTRIB_NAME = "Id";
    
    String getTarget();
    
    void setTarget(final String p0);
    
    String getID();
    
    void setID(final String p0);
}
