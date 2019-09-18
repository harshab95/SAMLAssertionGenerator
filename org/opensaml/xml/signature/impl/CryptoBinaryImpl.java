// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.util.DatatypeHelper;
import java.math.BigInteger;
import org.opensaml.xml.signature.CryptoBinary;
import org.opensaml.xml.schema.impl.XSBase64BinaryImpl;

public class CryptoBinaryImpl extends XSBase64BinaryImpl implements CryptoBinary
{
    private BigInteger bigIntValue;
    
    protected CryptoBinaryImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public BigInteger getValueBigInt() {
        if (this.bigIntValue == null && !DatatypeHelper.isEmpty(this.getValue())) {
            this.bigIntValue = KeyInfoHelper.decodeBigIntegerFromCryptoBinary(this.getValue());
        }
        return this.bigIntValue;
    }
    
    public void setValueBigInt(final BigInteger bigInt) {
        if (bigInt == null) {
            this.setValue(null);
        }
        else {
            this.setValue(KeyInfoHelper.encodeCryptoBinaryFromBigInteger(bigInt));
        }
        this.bigIntValue = bigInt;
    }
    
    @Override
    public void setValue(final String newValue) {
        if (this.bigIntValue != null && (!DatatypeHelper.safeEquals(this.getValue(), newValue) || newValue == null)) {
            this.bigIntValue = null;
        }
        super.setValue(newValue);
    }
}
