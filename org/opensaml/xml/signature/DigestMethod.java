// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface DigestMethod extends ValidatingXMLObject, ElementExtensibleXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DigestMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod", "ds");
    public static final String TYPE_LOCAL_NAME = "DigestMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethodType", "ds");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";
    
    String getAlgorithm();
    
    void setAlgorithm(final String p0);
}
