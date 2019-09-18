// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface ReferenceList extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ReferenceList";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");
    
    List<ReferenceType> getReferences();
    
    List<DataReference> getDataReferences();
    
    List<KeyReference> getKeyReferences();
}
