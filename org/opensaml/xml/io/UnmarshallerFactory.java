// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import java.util.Collections;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import javax.xml.namespace.QName;
import java.util.Map;
import org.slf4j.Logger;

public class UnmarshallerFactory
{
    private final Logger log;
    private Map<QName, Unmarshaller> unmarshallers;
    
    public UnmarshallerFactory() {
        this.log = LoggerFactory.getLogger((Class)UnmarshallerFactory.class);
        this.unmarshallers = new ConcurrentHashMap<QName, Unmarshaller>();
    }
    
    public Unmarshaller getUnmarshaller(final QName key) {
        if (key == null) {
            return null;
        }
        return this.unmarshallers.get(key);
    }
    
    public Unmarshaller getUnmarshaller(final Element domElement) {
        Unmarshaller unmarshaller = this.getUnmarshaller(XMLHelper.getXSIType(domElement));
        if (unmarshaller == null) {
            unmarshaller = this.getUnmarshaller(XMLHelper.getNodeQName(domElement));
        }
        return unmarshaller;
    }
    
    public Map<QName, Unmarshaller> getUnmarshallers() {
        return Collections.unmodifiableMap((Map<? extends QName, ? extends Unmarshaller>)this.unmarshallers);
    }
    
    public void registerUnmarshaller(final QName key, final Unmarshaller unmarshaller) {
        this.log.debug("Registering unmarshaller, {}, for object type, {}", (Object)unmarshaller.getClass().getName(), (Object)key);
        if (key == null) {
            throw new IllegalArgumentException("Unmarshaller key may not be null");
        }
        this.unmarshallers.put(key, unmarshaller);
    }
    
    public Unmarshaller deregisterUnmarshaller(final QName key) {
        this.log.debug("Deregistering marshaller for object type {}", (Object)key);
        if (key != null) {
            return this.unmarshallers.remove(key);
        }
        return null;
    }
}
