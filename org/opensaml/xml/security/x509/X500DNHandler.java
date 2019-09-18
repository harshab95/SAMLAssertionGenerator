// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import javax.security.auth.x500.X500Principal;

public interface X500DNHandler
{
    public static final String FORMAT_RFC1779 = "RFC1779";
    public static final String FORMAT_RFC2253 = "RFC2253";
    
    X500Principal parse(final String p0);
    
    X500Principal parse(final byte[] p0);
    
    String getName(final X500Principal p0);
    
    String getName(final X500Principal p0, final String p1);
    
    byte[] getEncoded(final X500Principal p0);
    
    X500DNHandler clone();
}
