// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.credential.Credential;

public interface KeyInfoGeneratorFactory
{
    KeyInfoGenerator newInstance();
    
    boolean handles(final Credential p0);
    
    Class<? extends Credential> getCredentialType();
}
