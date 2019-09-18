// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import javax.xml.namespace.QName;
import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface PGPData extends ValidatingXMLObject, ElementExtensibleXMLObject
{
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PGPData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
    public static final String TYPE_LOCAL_NAME = "PGPDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPDataType", "ds");
    
    PGPKeyID getPGPKeyID();
    
    void setPGPKeyID(final PGPKeyID p0);
    
    PGPKeyPacket getPGPKeyPacket();
    
    void setPGPKeyPacket(final PGPKeyPacket p0);
}
