// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import javax.security.auth.x500.X500Principal;

public class InternalX500DNHandler implements X500DNHandler
{
    public byte[] getEncoded(final X500Principal principal) {
        if (principal == null) {
            throw new NullPointerException("X500Principal may not be null");
        }
        return principal.getEncoded();
    }
    
    public String getName(final X500Principal principal) {
        if (principal == null) {
            throw new NullPointerException("X500Principal may not be null");
        }
        return principal.getName();
    }
    
    public String getName(final X500Principal principal, final String format) {
        if (principal == null) {
            throw new NullPointerException("X500Principal may not be null");
        }
        return principal.getName(format);
    }
    
    public X500Principal parse(final String name) {
        if (name == null) {
            throw new NullPointerException("X.500 name string may not be null");
        }
        return new X500Principal(name);
    }
    
    public X500Principal parse(final byte[] name) {
        if (name == null) {
            throw new NullPointerException("X.500 DER-encoded name may not be null");
        }
        return new X500Principal(name);
    }
    
    public X500DNHandler clone() {
        return new InternalX500DNHandler();
    }
}
