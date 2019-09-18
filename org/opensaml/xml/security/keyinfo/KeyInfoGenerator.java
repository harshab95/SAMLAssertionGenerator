// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.credential.Credential;

public interface KeyInfoGenerator
{
    KeyInfo generate(final Credential p0) throws SecurityException;
}
