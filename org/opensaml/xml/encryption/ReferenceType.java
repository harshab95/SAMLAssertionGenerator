// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface ReferenceType extends ValidatingXMLObject, ElementExtensibleXMLObject
{
    public static final String TYPE_LOCAL_NAME = "ReferenceType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceType", "xenc");
    public static final String URI_ATTRIB_NAME = "URI";
    
    String getURI();
    
    void setURI(final String p0);
}
