// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.parse;

import java.io.File;
import org.w3c.dom.DOMImplementation;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Iterator;
import java.util.Collections;
import org.xml.sax.InputSource;
import java.io.Reader;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.opensaml.xml.util.LazyMap;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.EntityResolver;
import javax.xml.validation.Schema;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import java.lang.ref.SoftReference;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;

public class BasicParserPool implements ParserPool
{
    private final Logger log;
    private long poolVersion;
    private boolean dirtyBuilderConfiguration;
    private DocumentBuilderFactory builderFactory;
    private Stack<SoftReference<DocumentBuilder>> builderPool;
    private int maxPoolSize;
    private Map<String, Object> builderAttributes;
    private boolean coalescing;
    private boolean expandEntityReferences;
    private Map<String, Boolean> builderFeatures;
    private boolean ignoreComments;
    private boolean ignoreElementContentWhitespace;
    private boolean namespaceAware;
    private Schema schema;
    private boolean dtdValidating;
    private boolean xincludeAware;
    private EntityResolver entityResolver;
    private ErrorHandler errorHandler;
    
    public BasicParserPool() {
        this.log = LoggerFactory.getLogger((Class)BasicParserPool.class);
        this.maxPoolSize = 5;
        this.builderPool = new Stack<SoftReference<DocumentBuilder>>();
        this.builderAttributes = new LazyMap<String, Object>();
        this.coalescing = true;
        this.expandEntityReferences = true;
        this.builderFeatures = new LazyMap<String, Boolean>();
        this.ignoreComments = true;
        this.ignoreElementContentWhitespace = true;
        this.namespaceAware = true;
        this.schema = null;
        this.dtdValidating = false;
        this.xincludeAware = false;
        this.errorHandler = new LoggingErrorHandler(this.log);
        try {
            this.dirtyBuilderConfiguration = true;
            this.initializePool();
        }
        catch (XMLParserException ex) {}
    }
    
    public DocumentBuilder getBuilder() throws XMLParserException {
        DocumentBuilder builder = null;
        long version = 0L;
        if (this.dirtyBuilderConfiguration) {
            this.initializePool();
        }
        synchronized (this) {
            version = this.getPoolVersion();
            if (!this.builderPool.isEmpty()) {
                builder = this.builderPool.pop().get();
            }
            if (builder == null) {
                builder = this.createBuilder();
            }
        }
        if (builder != null) {
            return new DocumentBuilderProxy(builder, this, version);
        }
        return null;
    }
    
    public void returnBuilder(final DocumentBuilder builder) {
        if (!(builder instanceof DocumentBuilderProxy)) {
            return;
        }
        final DocumentBuilderProxy proxiedBuilder = (DocumentBuilderProxy)builder;
        if (proxiedBuilder.getOwningPool() != this) {
            return;
        }
        synchronized (this) {
            if (proxiedBuilder.isReturned()) {
                // monitorexit(this)
                return;
            }
            if (proxiedBuilder.getPoolVersion() != this.poolVersion) {
                // monitorexit(this)
                return;
            }
            final DocumentBuilder unwrappedBuilder = proxiedBuilder.getProxiedBuilder();
            unwrappedBuilder.reset();
            final SoftReference<DocumentBuilder> builderReference = new SoftReference<DocumentBuilder>(unwrappedBuilder);
            if (this.builderPool.size() < this.maxPoolSize) {
                proxiedBuilder.setReturned(true);
                this.builderPool.push(builderReference);
            }
        }
    }
    
    public Document newDocument() throws XMLParserException {
        final DocumentBuilder builder = this.getBuilder();
        final Document document = builder.newDocument();
        this.returnBuilder(builder);
        return document;
    }
    
    public Document parse(final InputStream input) throws XMLParserException {
        final DocumentBuilder builder = this.getBuilder();
        try {
            final Document document = builder.parse(input);
            return document;
        }
        catch (SAXException e) {
            throw new XMLParserException("Invalid XML", e);
        }
        catch (IOException e2) {
            throw new XMLParserException("Unable to read XML from input stream", e2);
        }
        finally {
            this.returnBuilder(builder);
        }
    }
    
    public Document parse(final Reader input) throws XMLParserException {
        final DocumentBuilder builder = this.getBuilder();
        try {
            final Document document = builder.parse(new InputSource(input));
            return document;
        }
        catch (SAXException e) {
            throw new XMLParserException("Invalid XML", e);
        }
        catch (IOException e2) {
            throw new XMLParserException("Unable to read XML from input stream", e2);
        }
        finally {
            this.returnBuilder(builder);
        }
    }
    
    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }
    
    public void setMaxPoolSize(final int newSize) {
        this.maxPoolSize = newSize;
    }
    
    @Deprecated
    public boolean getCreateBuildersAtPoolLimit() {
        return true;
    }
    
    @Deprecated
    public void setCreateBuildersAtPoolLimit(final boolean createBuilders) {
    }
    
    public Map<String, Object> getBuilderAttributes() {
        return Collections.unmodifiableMap((Map<? extends String, ?>)this.builderAttributes);
    }
    
    public synchronized void setBuilderAttributes(final Map<String, Object> newAttributes) {
        this.builderAttributes = newAttributes;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isCoalescing() {
        return this.coalescing;
    }
    
    public synchronized void setCoalescing(final boolean isCoalescing) {
        this.coalescing = isCoalescing;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isExpandEntityReferences() {
        return this.expandEntityReferences;
    }
    
    public synchronized void setExpandEntityReferences(final boolean expand) {
        this.expandEntityReferences = expand;
        this.dirtyBuilderConfiguration = true;
    }
    
    public Map<String, Boolean> getBuilderFeatures() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends Boolean>)this.builderFeatures);
    }
    
    public synchronized void setBuilderFeatures(final Map<String, Boolean> newFeatures) {
        this.builderFeatures = newFeatures;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean getIgnoreComments() {
        return this.ignoreComments;
    }
    
    public synchronized void setIgnoreComments(final boolean ignore) {
        this.ignoreComments = ignore;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isIgnoreElementContentWhitespace() {
        return this.ignoreElementContentWhitespace;
    }
    
    public synchronized void setIgnoreElementContentWhitespace(final boolean ignore) {
        this.ignoreElementContentWhitespace = ignore;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }
    
    public synchronized void setNamespaceAware(final boolean isNamespaceAware) {
        this.namespaceAware = isNamespaceAware;
        this.dirtyBuilderConfiguration = true;
    }
    
    public Schema getSchema() {
        return this.schema;
    }
    
    public synchronized void setSchema(final Schema newSchema) {
        this.schema = newSchema;
        if (this.schema != null) {
            this.setNamespaceAware(true);
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaSource");
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        }
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isDTDValidating() {
        return this.dtdValidating;
    }
    
    public synchronized void setDTDValidating(final boolean isValidating) {
        this.dtdValidating = isValidating;
        this.dirtyBuilderConfiguration = true;
    }
    
    public boolean isXincludeAware() {
        return this.xincludeAware;
    }
    
    public synchronized void setXincludeAware(final boolean isXIncludeAware) {
        this.xincludeAware = isXIncludeAware;
        this.dirtyBuilderConfiguration = true;
    }
    
    protected long getPoolVersion() {
        return this.poolVersion;
    }
    
    protected int getPoolSize() {
        return this.builderPool.size();
    }
    
    protected synchronized void initializePool() throws XMLParserException {
        if (!this.dirtyBuilderConfiguration) {
            return;
        }
        final DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
        this.setAttributes(newFactory, this.builderAttributes);
        this.setFeatures(newFactory, this.builderFeatures);
        newFactory.setCoalescing(this.coalescing);
        newFactory.setExpandEntityReferences(this.expandEntityReferences);
        newFactory.setIgnoringComments(this.ignoreComments);
        newFactory.setIgnoringElementContentWhitespace(this.ignoreElementContentWhitespace);
        newFactory.setNamespaceAware(this.namespaceAware);
        newFactory.setSchema(this.schema);
        newFactory.setValidating(this.dtdValidating);
        newFactory.setXIncludeAware(this.xincludeAware);
        ++this.poolVersion;
        this.dirtyBuilderConfiguration = false;
        this.builderFactory = newFactory;
        this.builderPool.clear();
    }
    
    protected void setAttributes(final DocumentBuilderFactory factory, final Map<String, Object> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, Object> attribute : attributes.entrySet()) {
            try {
                this.log.debug("Setting DocumentBuilderFactory attribute '{}'", (Object)attribute.getKey());
                factory.setAttribute(attribute.getKey(), attribute.getValue());
            }
            catch (IllegalArgumentException e) {
                this.log.warn("DocumentBuilderFactory attribute '{}' is not supported", (Object)attribute.getKey());
            }
        }
    }
    
    protected void setFeatures(final DocumentBuilderFactory factory, final Map<String, Boolean> features) {
        if (features == null || features.isEmpty()) {
            return;
        }
        for (final Map.Entry<String, Boolean> feature : features.entrySet()) {
            try {
                this.log.debug("Setting DocumentBuilderFactory attribute '{}'", (Object)feature.getKey());
                factory.setFeature(feature.getKey(), feature.getValue());
            }
            catch (ParserConfigurationException e) {
                this.log.warn("DocumentBuilderFactory feature '{}' is not supported", (Object)feature.getKey());
            }
        }
    }
    
    protected DocumentBuilder createBuilder() throws XMLParserException {
        try {
            final DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
            if (this.entityResolver != null) {
                builder.setEntityResolver(this.entityResolver);
            }
            if (this.errorHandler != null) {
                builder.setErrorHandler(this.errorHandler);
            }
            return builder;
        }
        catch (ParserConfigurationException e) {
            this.log.error("Unable to create new document builder", (Throwable)e);
            throw new XMLParserException("Unable to create new document builder", e);
        }
    }
    
    protected class DocumentBuilderProxy extends DocumentBuilder
    {
        private DocumentBuilder builder;
        private ParserPool owningPool;
        private long owningPoolVersion;
        private boolean returned;
        
        public DocumentBuilderProxy(final DocumentBuilder target, final BasicParserPool owner, final long version) {
            this.owningPoolVersion = version;
            this.owningPool = owner;
            this.builder = target;
            this.returned = false;
        }
        
        @Override
        public DOMImplementation getDOMImplementation() {
            this.checkValidState();
            return this.builder.getDOMImplementation();
        }
        
        @Override
        public Schema getSchema() {
            this.checkValidState();
            return this.builder.getSchema();
        }
        
        @Override
        public boolean isNamespaceAware() {
            this.checkValidState();
            return this.builder.isNamespaceAware();
        }
        
        @Override
        public boolean isValidating() {
            this.checkValidState();
            return this.builder.isValidating();
        }
        
        @Override
        public boolean isXIncludeAware() {
            this.checkValidState();
            return this.builder.isXIncludeAware();
        }
        
        @Override
        public Document newDocument() {
            this.checkValidState();
            return this.builder.newDocument();
        }
        
        @Override
        public Document parse(final File f) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(f);
        }
        
        @Override
        public Document parse(final InputSource is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }
        
        @Override
        public Document parse(final InputStream is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }
        
        @Override
        public Document parse(final InputStream is, final String systemId) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is, systemId);
        }
        
        @Override
        public Document parse(final String uri) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(uri);
        }
        
        @Override
        public void reset() {
        }
        
        @Override
        public void setEntityResolver(final EntityResolver er) {
            this.checkValidState();
        }
        
        @Override
        public void setErrorHandler(final ErrorHandler eh) {
            this.checkValidState();
        }
        
        protected ParserPool getOwningPool() {
            return this.owningPool;
        }
        
        protected long getPoolVersion() {
            return this.owningPoolVersion;
        }
        
        protected DocumentBuilder getProxiedBuilder() {
            return this.builder;
        }
        
        protected boolean isReturned() {
            return this.returned;
        }
        
        protected void setReturned(final boolean isReturned) {
            this.returned = isReturned;
        }
        
        protected void checkValidState() throws IllegalStateException {
            if (this.isReturned()) {
                throw new IllegalStateException("DocumentBuilderProxy has already been returned to its owning pool");
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            this.owningPool.returnBuilder(this);
        }
    }
}
