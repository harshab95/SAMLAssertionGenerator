// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.trust.TrustEngine;

public interface SignatureTrustEngine extends TrustEngine<Signature>
{
    KeyInfoCredentialResolver getKeyInfoResolver();
    
    boolean validate(final byte[] p0, final byte[] p1, final String p2, final CriteriaSet p3, final Credential p4) throws SecurityException;
}
