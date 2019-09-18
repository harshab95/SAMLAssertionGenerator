// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.parse;

public class XMLParserException extends Exception
{
    private static final long serialVersionUID = 7260425832643941776L;
    
    public XMLParserException() {
    }
    
    public XMLParserException(final String message) {
        super(message);
    }
    
    public XMLParserException(final Exception wrappedException) {
        super(wrappedException);
    }
    
    public XMLParserException(final String message, final Exception wrappedException) {
        super(message, wrappedException);
    }
}
