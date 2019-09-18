// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

public class SignatureException extends Exception
{
    private static final long serialVersionUID = 7879866991794922684L;
    
    public SignatureException() {
    }
    
    public SignatureException(final String message) {
        super(message);
    }
    
    public SignatureException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public SignatureException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
