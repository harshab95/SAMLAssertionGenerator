// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.Criteria;

public final class X509SubjectKeyIdentifierCriteria implements Criteria
{
    private byte[] subjectKeyIdentifier;
    
    public X509SubjectKeyIdentifierCriteria(final byte[] ski) {
        this.setSubjectKeyIdentifier(ski);
    }
    
    public byte[] getSubjectKeyIdentifier() {
        return this.subjectKeyIdentifier;
    }
    
    public void setSubjectKeyIdentifier(final byte[] ski) {
        if (ski == null || ski.length == 0) {
            throw new IllegalArgumentException("Subject key identifier criteria value must be non-null and non-empty");
        }
        this.subjectKeyIdentifier = ski;
    }
}
