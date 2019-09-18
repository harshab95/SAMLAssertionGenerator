// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.SecurityException;
import java.util.Set;

public interface X509CredentialNameEvaluator
{
    boolean evaluate(final X509Credential p0, final Set<String> p1) throws SecurityException;
}
