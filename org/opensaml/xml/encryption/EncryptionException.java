// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

public class EncryptionException extends Exception
{
    private static final long serialVersionUID = -3196546983060216532L;
    
    public EncryptionException() {
    }
    
    public EncryptionException(final String message) {
        super(message);
    }
    
    public EncryptionException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public EncryptionException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
