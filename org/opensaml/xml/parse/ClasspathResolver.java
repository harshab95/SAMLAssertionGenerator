// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.parse;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedInputStream;
import org.w3c.dom.ls.LSInput;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.InputStream;
import org.xml.sax.InputSource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.EntityResolver;

public class ClasspathResolver implements EntityResolver, LSResourceResolver
{
    public static final String CLASSPATH_URI_SCHEME = "classpath:";
    private final Logger log;
    
    public ClasspathResolver() {
        this.log = LoggerFactory.getLogger((Class)ClasspathResolver.class);
    }
    
    public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {
        final InputStream resourceStream = this.resolver(publicId, systemId);
        if (resourceStream != null) {
            return new InputSource(resourceStream);
        }
        return null;
    }
    
    public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId, final String baseURI) {
        return new LSInputImpl(publicId, systemId, this.resolver(publicId, systemId));
    }
    
    protected InputStream resolver(final String publicId, final String systemId) {
        String resource = null;
        InputStream resourceIns = null;
        if (systemId.startsWith("classpath:")) {
            this.log.trace("Attempting to resolve, within the classpath, the entity with the following system id: {}", (Object)systemId);
            resource = systemId.replaceFirst("classpath:", "");
            resourceIns = this.getClass().getResourceAsStream(resource);
        }
        if (resourceIns == null && publicId != null && publicId.startsWith("classpath:")) {
            this.log.trace("Attempting to resolve, within the classpath, the entity with the following public id: {}", (Object)resource);
            resource = publicId.replaceFirst("classpath:", "");
            resourceIns = this.getClass().getResourceAsStream(resource);
        }
        if (resourceIns == null) {
            this.log.trace("Entity was not resolved from classpath");
            return null;
        }
        this.log.trace("Entity resolved from classpath");
        return resourceIns;
    }
    
    protected class LSInputImpl implements LSInput
    {
        private String publicId;
        private String systemId;
        private BufferedInputStream buffInput;
        
        public LSInputImpl(final String pubId, final String sysId, final InputStream input) {
            this.publicId = pubId;
            this.systemId = sysId;
            this.buffInput = new BufferedInputStream(input);
        }
        
        public String getBaseURI() {
            return null;
        }
        
        public InputStream getByteStream() {
            return this.buffInput;
        }
        
        public boolean getCertifiedText() {
            return false;
        }
        
        public Reader getCharacterStream() {
            return new InputStreamReader(this.buffInput);
        }
        
        public String getEncoding() {
            return null;
        }
        
        public String getPublicId() {
            return this.publicId;
        }
        
        public String getStringData() {
            synchronized (this.buffInput) {
                try {
                    this.buffInput.reset();
                    final byte[] input = new byte[this.buffInput.available()];
                    this.buffInput.read(input);
                    // monitorexit(this.buffInput)
                    return new String(input);
                }
                catch (IOException e) {
                    // monitorexit(this.buffInput)
                    return null;
                }
            }
        }
        
        public String getSystemId() {
            return this.systemId;
        }
        
        public void setBaseURI(final String uri) {
        }
        
        public void setByteStream(final InputStream byteStream) {
        }
        
        public void setCertifiedText(final boolean isCertifiedText) {
        }
        
        public void setCharacterStream(final Reader characterStream) {
        }
        
        public void setEncoding(final String encoding) {
        }
        
        public void setPublicId(final String id) {
        }
        
        public void setStringData(final String stringData) {
        }
        
        public void setSystemId(final String id) {
        }
    }
}
