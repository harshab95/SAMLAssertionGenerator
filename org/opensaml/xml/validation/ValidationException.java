// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.validation;

public class ValidationException extends Exception
{
    private static final long serialVersionUID = -8268522315645892798L;
    
    public ValidationException() {
    }
    
    public ValidationException(final String message) {
        super(message);
    }
    
    public ValidationException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public ValidationException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
