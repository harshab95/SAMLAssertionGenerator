// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.signature.Transform;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface Transforms extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Transforms";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
    public static final String TYPE_LOCAL_NAME = "TransformsType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "TransformsType", "xenc");
    
    List<Transform> getTransforms();
}
