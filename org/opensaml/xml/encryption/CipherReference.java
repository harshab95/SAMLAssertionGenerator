// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface CipherReference extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CipherReference";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReference", "xenc");
    public static final String TYPE_LOCAL_NAME = "CipherReferenceType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReferenceType", "xenc");
    public static final String URI_ATTRIB_NAME = "URI";
    
    String getURI();
    
    void setURI(final String p0);
    
    Transforms getTransforms();
    
    void setTransforms(final Transforms p0);
}
