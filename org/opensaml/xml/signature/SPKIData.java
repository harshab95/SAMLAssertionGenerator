// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface SPKIData extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SPKIData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
    public static final String TYPE_LOCAL_NAME = "SPKIDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIDataType", "ds");
    
    List<XMLObject> getXMLObjects();
    
    List<XMLObject> getXMLObjects(final QName p0);
    
    List<SPKISexp> getSPKISexps();
}
