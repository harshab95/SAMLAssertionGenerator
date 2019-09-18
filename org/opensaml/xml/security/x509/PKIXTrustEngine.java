// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.opensaml.xml.security.trust.TrustEngine;

public interface PKIXTrustEngine<TokenType> extends TrustEngine<TokenType>
{
    PKIXValidationInformationResolver getPKIXResolver();
}
