// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import java.math.BigInteger;
import javax.xml.namespace.QName;
import org.opensaml.xml.schema.XSBase64Binary;

public interface CryptoBinary extends XSBase64Binary
{
    public static final String TYPE_LOCAL_NAME = "CryptoBinary";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CryptoBinary", "ds");
    
    BigInteger getValueBigInt();
    
    void setValueBigInt(final BigInteger p0);
}
