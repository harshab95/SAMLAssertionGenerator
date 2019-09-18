// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

public class SecurityException extends Exception
{
    private static final long serialVersionUID = 485895728446891757L;
    
    public SecurityException() {
    }
    
    public SecurityException(final String message) {
        super(message);
    }
    
    public SecurityException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public SecurityException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
