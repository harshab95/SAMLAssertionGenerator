// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.trust;

import org.opensaml.xml.security.credential.CredentialResolver;

public interface TrustedCredentialTrustEngine<TokenType> extends TrustEngine<TokenType>
{
    CredentialResolver getCredentialResolver();
}
