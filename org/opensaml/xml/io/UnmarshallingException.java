// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

public class UnmarshallingException extends Exception
{
    private static final long serialVersionUID = 7512624219806550152L;
    
    public UnmarshallingException() {
    }
    
    public UnmarshallingException(final String message) {
        super(message);
    }
    
    public UnmarshallingException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public UnmarshallingException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
