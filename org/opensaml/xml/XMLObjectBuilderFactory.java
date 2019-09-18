// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import java.util.Collections;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import javax.xml.namespace.QName;
import java.util.Map;
import org.slf4j.Logger;

public class XMLObjectBuilderFactory
{
    private final Logger log;
    private Map<QName, XMLObjectBuilder> builders;
    
    public XMLObjectBuilderFactory() {
        this.log = LoggerFactory.getLogger((Class)XMLObjectBuilderFactory.class);
        this.builders = new ConcurrentHashMap<QName, XMLObjectBuilder>();
    }
    
    public XMLObjectBuilder getBuilder(final QName key) {
        if (key == null) {
            return null;
        }
        return this.builders.get(key);
    }
    
    public XMLObjectBuilder getBuilder(final Element domElement) {
        XMLObjectBuilder builder = this.getBuilder(XMLHelper.getXSIType(domElement));
        if (builder == null) {
            builder = this.getBuilder(XMLHelper.getNodeQName(domElement));
        }
        return builder;
    }
    
    public Map<QName, XMLObjectBuilder> getBuilders() {
        return (Map<QName, XMLObjectBuilder>)Collections.unmodifiableMap((Map<? extends QName, ? extends XMLObjectBuilder>)this.builders);
    }
    
    public void registerBuilder(final QName builderKey, final XMLObjectBuilder builder) {
        this.log.debug("Registering builder, {} under key {}", (Object)builder.getClass().getName(), (Object)builderKey);
        if (builderKey == null) {
            throw new IllegalArgumentException("Builder key may not be null");
        }
        this.builders.put(builderKey, builder);
    }
    
    public XMLObjectBuilder deregisterBuilder(final QName builderKey) {
        this.log.debug("Deregistering builder for object type {}", (Object)builderKey);
        if (builderKey != null) {
            return this.builders.remove(builderKey);
        }
        return null;
    }
}
