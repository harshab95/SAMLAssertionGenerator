// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.CriteriaSet;

public interface TrustEngine<TokenType>
{
    boolean validate(final TokenType p0, final CriteriaSet p1) throws SecurityException;
}
