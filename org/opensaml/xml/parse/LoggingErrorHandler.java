// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.parse;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;

public class LoggingErrorHandler implements ErrorHandler
{
    private Logger log;
    
    public LoggingErrorHandler(final Logger logger) {
        this.log = logger;
    }
    
    public void error(final SAXParseException exception) throws SAXException {
        this.log.error("XML Parsing Error:", (Throwable)exception);
        throw exception;
    }
    
    public void fatalError(final SAXParseException exception) throws SAXException {
        this.log.error("XML Parsing Error", (Throwable)exception);
        throw exception;
    }
    
    public void warning(final SAXParseException exception) throws SAXException {
        this.log.warn("XML Parsing Error", (Throwable)exception);
        throw exception;
    }
}
