// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

public interface SignableXMLObject extends XMLObject
{
    boolean isSigned();
    
    Signature getSignature();
    
    void setSignature(final Signature p0);
}
