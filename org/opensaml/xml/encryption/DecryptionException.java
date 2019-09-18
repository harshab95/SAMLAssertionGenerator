// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

public class DecryptionException extends Exception
{
    private static final long serialVersionUID = 7785660781162212790L;
    
    public DecryptionException() {
    }
    
    public DecryptionException(final String message) {
        super(message);
    }
    
    public DecryptionException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public DecryptionException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
