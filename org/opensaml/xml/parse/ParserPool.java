// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.parse;

import javax.xml.validation.Schema;
import java.io.Reader;
import java.io.InputStream;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;

public interface ParserPool
{
    DocumentBuilder getBuilder() throws XMLParserException;
    
    void returnBuilder(final DocumentBuilder p0);
    
    Document newDocument() throws XMLParserException;
    
    Document parse(final InputStream p0) throws XMLParserException;
    
    Document parse(final Reader p0) throws XMLParserException;
    
    Schema getSchema();
    
    void setSchema(final Schema p0);
}
