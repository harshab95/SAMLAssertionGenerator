// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface RetrievalMethod extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RetrievalMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod", "ds");
    public static final String TYPE_LOCAL_NAME = "RetrievalMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethodType", "ds");
    public static final String URI_ATTRIB_NAME = "URI";
    public static final String TYPE_ATTRIB_NAME = "Type";
    
    String getURI();
    
    void setURI(final String p0);
    
    String getType();
    
    void setType(final String p0);
    
    Transforms getTransforms();
    
    void setTransforms(final Transforms p0);
}
