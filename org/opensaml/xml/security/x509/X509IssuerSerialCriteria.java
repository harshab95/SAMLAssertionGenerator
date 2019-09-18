// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.math.BigInteger;
import javax.security.auth.x500.X500Principal;
import org.opensaml.xml.security.Criteria;

public final class X509IssuerSerialCriteria implements Criteria
{
    private X500Principal issuerName;
    private BigInteger serialNumber;
    
    public X509IssuerSerialCriteria(final X500Principal issuer, final BigInteger serial) {
        this.setIssuerName(issuer);
        this.setSerialNumber(serial);
    }
    
    public X500Principal getIssuerName() {
        return this.issuerName;
    }
    
    public void setIssuerName(final X500Principal issuer) {
        if (issuer == null) {
            throw new IllegalArgumentException("Issuer principal criteria value may not be null");
        }
        this.issuerName = issuer;
    }
    
    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }
    
    public void setSerialNumber(final BigInteger serial) {
        if (serial == null) {
            throw new IllegalArgumentException("Serial number criteria value may not be null");
        }
        this.serialNumber = serial;
    }
}
