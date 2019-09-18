// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.io;

import java.util.Collections;
import org.opensaml.xml.XMLObject;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import javax.xml.namespace.QName;
import java.util.Map;
import org.slf4j.Logger;

public class MarshallerFactory
{
    private final Logger log;
    private Map<QName, Marshaller> marshallers;
    
    public MarshallerFactory() {
        this.log = LoggerFactory.getLogger((Class)MarshallerFactory.class);
        this.marshallers = new ConcurrentHashMap<QName, Marshaller>();
    }
    
    public Marshaller getMarshaller(final QName key) {
        if (key == null) {
            return null;
        }
        return this.marshallers.get(key);
    }
    
    public Marshaller getMarshaller(final XMLObject xmlObject) {
        Marshaller marshaller = this.getMarshaller(xmlObject.getSchemaType());
        if (marshaller == null) {
            marshaller = this.getMarshaller(xmlObject.getElementQName());
        }
        return marshaller;
    }
    
    public Map<QName, Marshaller> getMarshallers() {
        return Collections.unmodifiableMap((Map<? extends QName, ? extends Marshaller>)this.marshallers);
    }
    
    public void registerMarshaller(final QName key, final Marshaller marshaller) {
        this.log.debug("Registering marshaller, {}, for object type {}", (Object)marshaller.getClass().getName(), (Object)key);
        if (key == null) {
            throw new IllegalArgumentException("Marshaller key may not be null");
        }
        this.marshallers.put(key, marshaller);
    }
    
    public Marshaller deregisterMarshaller(final QName key) {
        this.log.debug("Deregistering marshaller for object type {}", (Object)key);
        if (key != null) {
            return this.marshallers.remove(key);
        }
        return null;
    }
}
