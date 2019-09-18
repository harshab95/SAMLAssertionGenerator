// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

public class XMLRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1299468635977382060L;
    
    public XMLRuntimeException() {
    }
    
    public XMLRuntimeException(final String message) {
        super(message);
    }
    
    public XMLRuntimeException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public XMLRuntimeException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
