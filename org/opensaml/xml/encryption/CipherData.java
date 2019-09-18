// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface CipherData extends ValidatingXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CipherData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
    public static final String TYPE_LOCAL_NAME = "CipherDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherDataType", "xenc");
    
    CipherValue getCipherValue();
    
    void setCipherValue(final CipherValue p0);
    
    CipherReference getCipherReference();
    
    void setCipherReference(final CipherReference p0);
}
