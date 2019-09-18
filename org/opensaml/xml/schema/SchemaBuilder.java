// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import org.xml.sax.ErrorHandler;
import org.opensaml.xml.parse.LoggingErrorHandler;
import org.slf4j.LoggerFactory;
import javax.xml.validation.SchemaFactory;
import org.slf4j.Logger;
import java.io.InputStream;
import java.util.Iterator;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;
import java.util.List;
import java.util.ArrayList;
import org.xml.sax.SAXException;
import java.io.File;
import javax.xml.validation.Schema;

public final class SchemaBuilder
{
    private SchemaBuilder() {
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final String schemaFileOrDirectory) throws SAXException {
        if (schemaFileOrDirectory == null) {
            return null;
        }
        return buildSchema(lang, new File(schemaFileOrDirectory));
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final String[] schemaFilesOrDirectories) throws SAXException {
        if (schemaFilesOrDirectories == null || schemaFilesOrDirectories.length == 0) {
            return null;
        }
        return buildSchema(lang, schemaFilesOrDirectories);
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final File schemaFileOrDirectory) throws SAXException {
        if (schemaFileOrDirectory == null) {
            return null;
        }
        return buildSchema(lang, new File[] { schemaFileOrDirectory });
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final File[] schemaFilesOrDirectories) throws SAXException {
        if (schemaFilesOrDirectories == null || schemaFilesOrDirectories.length == 0) {
            return null;
        }
        final ArrayList<File> schemaFiles = new ArrayList<File>();
        getSchemaFiles(lang, schemaFilesOrDirectories, schemaFiles);
        if (schemaFiles.isEmpty()) {
            return null;
        }
        final ArrayList<Source> schemaSources = new ArrayList<Source>();
        for (final File schemaFile : schemaFiles) {
            schemaSources.add(new StreamSource(schemaFile));
        }
        return buildSchema(lang, schemaSources.toArray(new Source[0]));
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final InputStream schemaSource) throws SAXException {
        if (schemaSource == null) {
            return null;
        }
        return buildSchema(lang, new StreamSource[] { new StreamSource(schemaSource) });
    }
    
    public static Schema buildSchema(final SchemaLanguage lang, final InputStream[] schemaSources) throws SAXException {
        if (schemaSources == null || schemaSources.length == 0) {
            return null;
        }
        final ArrayList<StreamSource> sources = new ArrayList<StreamSource>();
        for (final InputStream schemaSource : schemaSources) {
            if (schemaSource != null) {
                sources.add(new StreamSource(schemaSource));
            }
        }
        if (sources.isEmpty()) {
            return null;
        }
        return buildSchema(lang, sources.toArray(new Source[0]));
    }
    
    protected static void getSchemaFiles(final SchemaLanguage lang, final File[] schemaFilesOrDirectories, final List<File> accumulatedSchemaFiles) {
        final Logger log = getLogger();
        if (lang == null) {
            throw new IllegalArgumentException("Schema language may not be null");
        }
        if (schemaFilesOrDirectories == null || schemaFilesOrDirectories.length == 0) {
            return;
        }
        for (final File handle : schemaFilesOrDirectories) {
            if (handle != null) {
                if (!handle.canRead()) {
                    log.debug("Ignoring '{}', no read permission", (Object)handle.getAbsolutePath());
                }
                if (handle.isFile() && handle.getName().endsWith(lang.getSchemaFileExtension())) {
                    log.debug("Added schema source '{}'", (Object)handle.getAbsolutePath());
                    accumulatedSchemaFiles.add(handle);
                }
                if (handle.isDirectory()) {
                    getSchemaFiles(lang, handle.listFiles(), accumulatedSchemaFiles);
                }
            }
        }
    }
    
    protected static Schema buildSchema(final SchemaLanguage lang, final Source[] schemaSources) throws SAXException {
        if (lang == null) {
            throw new IllegalArgumentException("Schema language may not be null");
        }
        if (schemaSources == null) {
            throw new IllegalArgumentException("Schema sources may not be null");
        }
        SchemaFactory schemaFactory;
        if (lang == SchemaLanguage.XML) {
            schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        }
        else {
            schemaFactory = SchemaFactory.newInstance("http://relaxng.org/ns/structure/1.0");
        }
        schemaFactory.setErrorHandler(new LoggingErrorHandler(LoggerFactory.getLogger((Class)SchemaBuilder.class)));
        return schemaFactory.newSchema(schemaSources);
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)SchemaBuilder.class);
    }
    
    public enum SchemaLanguage
    {
        XML("XML", 0, "xsd"), 
        RELAX("RELAX", 1, "rng");
        
        private String schemaFileExtension;
        
        private SchemaLanguage(final String name, final int ordinal, final String extension) {
            this.schemaFileExtension = extension;
        }
        
        public String getSchemaFileExtension() {
            return this.schemaFileExtension;
        }
    }
}
