// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.SecurityException;

public interface PKIXTrustEvaluator
{
    boolean validate(final PKIXValidationInformation p0, final X509Credential p1) throws SecurityException;
    
    PKIXValidationOptions getPKIXValidationOptions();
}
