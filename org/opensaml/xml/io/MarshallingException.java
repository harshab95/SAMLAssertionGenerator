// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

public class MarshallingException extends Exception
{
    private static final long serialVersionUID = 7381813529926476459L;
    
    public MarshallingException() {
    }
    
    public MarshallingException(final String message) {
        super(message);
    }
    
    public MarshallingException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public MarshallingException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
