// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

public class PKIXValidationOptions
{
    private boolean processEmptyCRLs;
    private boolean processExpiredCRLs;
    private boolean processCredentialCRLs;
    private Integer defaultVerificationDepth;
    
    public PKIXValidationOptions() {
        this.processEmptyCRLs = true;
        this.processExpiredCRLs = true;
        this.processCredentialCRLs = true;
        this.defaultVerificationDepth = new Integer(1);
    }
    
    public boolean isProcessEmptyCRLs() {
        return this.processEmptyCRLs;
    }
    
    public void setProcessEmptyCRLs(final boolean processEmptyCRLs) {
        this.processEmptyCRLs = processEmptyCRLs;
    }
    
    public boolean isProcessExpiredCRLs() {
        return this.processExpiredCRLs;
    }
    
    public void setProcessExpiredCRLs(final boolean processExpiredCRLs) {
        this.processExpiredCRLs = processExpiredCRLs;
    }
    
    public boolean isProcessCredentialCRLs() {
        return this.processCredentialCRLs;
    }
    
    public void setProcessCredentialCRLs(final boolean processCredentialCRLs) {
        this.processCredentialCRLs = processCredentialCRLs;
    }
    
    public Integer getDefaultVerificationDepth() {
        return this.defaultVerificationDepth;
    }
    
    public void setDefaultVerificationDepth(final Integer defaultVerificationDepth) {
        if (defaultVerificationDepth == null) {
            throw new IllegalArgumentException("Default verification depth may not be null");
        }
        this.defaultVerificationDepth = defaultVerificationDepth;
    }
}
