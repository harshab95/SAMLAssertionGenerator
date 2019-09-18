// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

public class CertPathPKIXValidationOptions extends PKIXValidationOptions
{
    private boolean forceRevocationEnabled;
    private boolean revocationEnabled;
    
    public CertPathPKIXValidationOptions() {
        this.forceRevocationEnabled = false;
        this.revocationEnabled = true;
    }
    
    public boolean isForceRevocationEnabled() {
        return this.forceRevocationEnabled;
    }
    
    public void setForceRevocationEnabled(final boolean forceRevocationEnabled) {
        this.forceRevocationEnabled = forceRevocationEnabled;
    }
    
    public boolean isRevocationEnabled() {
        return this.revocationEnabled;
    }
    
    public void setRevocationEnabled(final boolean revocationEnabled) {
        this.revocationEnabled = revocationEnabled;
    }
}
