// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

public class ConfigurationException extends Exception
{
    private static final long serialVersionUID = -6777602050296807774L;
    
    public ConfigurationException() {
    }
    
    public ConfigurationException(final String message) {
        super(message);
    }
    
    public ConfigurationException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public ConfigurationException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
