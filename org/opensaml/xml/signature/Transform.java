// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface Transform extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Transform";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
    public static final String TYPE_LOCAL_NAME = "TransformType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "TransformType", "ds");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";
    
    String getAlgorithm();
    
    void setAlgorithm(final String p0);
    
    List<XMLObject> getXMLObjects(final QName p0);
    
    List<XPath> getXPaths();
    
    List<XMLObject> getAllChildren();
}
