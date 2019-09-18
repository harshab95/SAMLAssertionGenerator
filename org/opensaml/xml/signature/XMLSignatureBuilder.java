// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObject;

public interface XMLSignatureBuilder<XMLSignatureType extends XMLObject> extends XMLObjectBuilder<XMLSignatureType>
{
    XMLSignatureType buildObject();
}
