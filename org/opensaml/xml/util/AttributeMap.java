// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.opensaml.xml.NamespaceManager;
import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import org.opensaml.xml.Configuration;
import org.slf4j.LoggerFactory;
import java.util.Set;
import org.opensaml.xml.XMLObject;
import org.slf4j.Logger;
import net.jcip.annotations.NotThreadSafe;
import javax.xml.namespace.QName;
import java.util.Map;

@NotThreadSafe
public class AttributeMap implements Map<QName, String>
{
    private final Logger log;
    private XMLObject attributeOwner;
    private Map<QName, String> attributes;
    private Set<QName> idAttribNames;
    private Set<QName> qnameAttribNames;
    private boolean inferQNameValues;
    
    public AttributeMap(final XMLObject newOwner) throws NullPointerException {
        this.log = LoggerFactory.getLogger((Class)AttributeMap.class);
        if (newOwner == null) {
            throw new NullPointerException("Attribute owner XMLObject may not be null");
        }
        this.attributeOwner = newOwner;
        this.attributes = new LazyMap<QName, String>();
        this.idAttribNames = new LazySet<QName>();
        this.qnameAttribNames = new LazySet<QName>();
    }
    
    public String put(final QName attributeName, final String value) {
        final String oldValue = this.get((Object)attributeName);
        if (value != oldValue) {
            this.releaseDOM();
            this.attributes.put(attributeName, value);
            if (this.isIDAttribute(attributeName) || Configuration.isIDAttribute(attributeName)) {
                this.attributeOwner.getIDIndex().deregisterIDMapping(oldValue);
                this.attributeOwner.getIDIndex().registerIDMapping(value, this.attributeOwner);
            }
            if (!DatatypeHelper.isEmpty(attributeName.getNamespaceURI())) {
                if (value == null) {
                    this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
                }
                else {
                    this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
                }
            }
            this.checkAndDeregisterQNameValue(attributeName, oldValue);
            this.checkAndRegisterQNameValue(attributeName, value);
        }
        return oldValue;
    }
    
    public QName put(final QName attributeName, final QName value) {
        final String oldValueString = this.get((Object)attributeName);
        QName oldValue = null;
        if (!DatatypeHelper.isEmpty(oldValueString)) {
            oldValue = this.resolveQName(oldValueString, true);
        }
        if (!DatatypeHelper.safeEquals(oldValue, value)) {
            this.releaseDOM();
            if (value != null) {
                final String newStringValue = this.constructAttributeValue(value);
                this.attributes.put(attributeName, newStringValue);
                this.registerQNameValue(attributeName, value);
                this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
            }
            else {
                this.deregisterQNameValue(attributeName);
                this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
            }
        }
        return oldValue;
    }
    
    public void clear() {
        final LazySet<QName> keys = new LazySet<QName>();
        keys.addAll(this.attributes.keySet());
        for (final QName attributeName : keys) {
            this.remove((Object)attributeName);
        }
    }
    
    public Set<QName> keySet() {
        return Collections.unmodifiableSet((Set<? extends QName>)this.attributes.keySet());
    }
    
    public int size() {
        return this.attributes.size();
    }
    
    public boolean isEmpty() {
        return this.attributes.isEmpty();
    }
    
    public boolean containsKey(final Object key) {
        return this.attributes.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        return this.attributes.containsValue(value);
    }
    
    public String get(final Object key) {
        return this.attributes.get(key);
    }
    
    public String remove(final Object key) {
        final String removedValue = this.attributes.remove(key);
        if (removedValue != null) {
            this.releaseDOM();
            final QName attributeName = (QName)key;
            if (this.isIDAttribute(attributeName) || Configuration.isIDAttribute(attributeName)) {
                this.attributeOwner.getIDIndex().deregisterIDMapping(removedValue);
            }
            this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
            this.checkAndDeregisterQNameValue(attributeName, removedValue);
        }
        return removedValue;
    }
    
    public void putAll(final Map<? extends QName, ? extends String> t) {
        if (t != null && t.size() > 0) {
            for (final Entry<? extends QName, ? extends String> entry : t.entrySet()) {
                this.put((QName)entry.getKey(), (String)entry.getValue());
            }
        }
    }
    
    public Collection<String> values() {
        return Collections.unmodifiableCollection((Collection<? extends String>)this.attributes.values());
    }
    
    public Set<Entry<QName, String>> entrySet() {
        return Collections.unmodifiableSet((Set<? extends Entry<QName, String>>)this.attributes.entrySet());
    }
    
    public void registerID(final QName attributeName) {
        if (!this.idAttribNames.contains(attributeName)) {
            this.idAttribNames.add(attributeName);
        }
        if (this.containsKey(attributeName)) {
            this.attributeOwner.getIDIndex().registerIDMapping(this.get((Object)attributeName), this.attributeOwner);
        }
    }
    
    public void deregisterID(final QName attributeName) {
        if (this.idAttribNames.contains(attributeName)) {
            this.idAttribNames.remove(attributeName);
        }
        if (this.containsKey(attributeName)) {
            this.attributeOwner.getIDIndex().deregisterIDMapping(this.get((Object)attributeName));
        }
    }
    
    public boolean isIDAttribute(final QName attributeName) {
        return this.idAttribNames.contains(attributeName);
    }
    
    public void registerQNameAttribute(final QName attributeName) {
        this.qnameAttribNames.add(attributeName);
    }
    
    public void deregisterQNameAttribute(final QName attributeName) {
        this.qnameAttribNames.remove(attributeName);
    }
    
    public boolean isQNameAttribute(final QName attributeName) {
        return this.qnameAttribNames.contains(attributeName);
    }
    
    public boolean isInferQNameValues() {
        return this.inferQNameValues;
    }
    
    public void setInferQNameValues(final boolean flag) {
        this.inferQNameValues = flag;
    }
    
    private void releaseDOM() {
        this.attributeOwner.releaseDOM();
        this.attributeOwner.releaseParentDOM(true);
    }
    
    private void checkAndRegisterQNameValue(final QName attributeName, final String attributeValue) {
        if (attributeValue == null) {
            return;
        }
        final QName qnameValue = this.checkQName(attributeName, attributeValue);
        if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
            this.registerQNameValue(attributeName, qnameValue);
        }
        else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
        }
    }
    
    private void registerQNameValue(final QName attributeName, final QName attributeValue) {
        if (attributeValue == null) {
            return;
        }
        final String attributeID = NamespaceManager.generateAttributeID(attributeName);
        this.log.trace("Registering QName attribute value '{}' under attibute ID '{}'", (Object)attributeValue, (Object)attributeID);
        this.attributeOwner.getNamespaceManager().registerAttributeValue(attributeID, attributeValue);
    }
    
    private void checkAndDeregisterQNameValue(final QName attributeName, final String attributeValue) {
        if (attributeValue == null) {
            return;
        }
        final QName qnameValue = this.checkQName(attributeName, attributeValue);
        if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
            this.deregisterQNameValue(attributeName);
        }
        else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
        }
    }
    
    private void deregisterQNameValue(final QName attributeName) {
        final String attributeID = NamespaceManager.generateAttributeID(attributeName);
        this.log.trace("Deregistering QName attribute with attibute ID '{}'", (Object)attributeID);
        this.attributeOwner.getNamespaceManager().deregisterAttributeValue(attributeID);
    }
    
    private QName checkQName(final QName attributeName, final String attributeValue) {
        this.log.trace("Checking whether attribute '{}' with value {} is a QName type", (Object)attributeName, (Object)attributeValue);
        if (attributeValue == null) {
            this.log.trace("Attribute value was null, returning null");
            return null;
        }
        if (this.isQNameAttribute(attributeName)) {
            this.log.trace("Configuration indicates attribute with name '{}' is a QName type, resolving value QName", (Object)attributeName);
            final QName valueName = this.resolveQName(attributeValue, true);
            if (valueName != null) {
                this.log.trace("Successfully resolved attribute value to QName: {}", (Object)valueName);
            }
            else {
                this.log.trace("Could not resolve attribute value to QName, returning null");
            }
            return valueName;
        }
        if (this.isInferQNameValues()) {
            this.log.trace("Attempting to infer whether attribute value is a QName");
            final QName valueName = this.resolveQName(attributeValue, false);
            if (valueName != null) {
                this.log.trace("Resolved attribute as a QName: '{}'", (Object)valueName);
            }
            else {
                this.log.trace("Attribute value was not resolveable to a QName, returning null");
            }
            return valueName;
        }
        this.log.trace("Attribute was not registered in configuration as a QName type and QName inference is disabled");
        return null;
    }
    
    private QName resolveQName(final String attributeValue, final boolean isDefaultNSOK) {
        if (attributeValue == null) {
            return null;
        }
        this.log.trace("Attemtping to resolve QName from attribute value '{}'", (Object)attributeValue);
        String candidatePrefix = null;
        String localPart = null;
        final int ci = attributeValue.indexOf(58);
        if (ci > -1) {
            candidatePrefix = attributeValue.substring(0, ci);
            this.log.trace("Evaluating candiate namespace prefix '{}'", (Object)candidatePrefix);
            localPart = attributeValue.substring(ci + 1);
        }
        else {
            if (!isDefaultNSOK) {
                this.log.trace("Value did not contain a colon, default namespace is disallowed, returning null");
                return null;
            }
            candidatePrefix = null;
            this.log.trace("Value did not contain a colon, evaluating as default namespace");
            localPart = attributeValue;
        }
        this.log.trace("Evaluated QName local part as '{}'", (Object)localPart);
        final String nsURI = XMLObjectHelper.lookupNamespaceURI(this.attributeOwner, candidatePrefix);
        this.log.trace("Resolved namespace URI '{}'", (Object)nsURI);
        if (nsURI != null) {
            final QName name = XMLHelper.constructQName(nsURI, localPart, candidatePrefix);
            this.log.trace("Resolved QName '{}'", (Object)name);
            return name;
        }
        this.log.trace("Namespace URI for candidate prefix '{}' could not be resolved", (Object)candidatePrefix);
        this.log.trace("Value was either not a QName, or namespace URI could not be resolved");
        return null;
    }
    
    private String constructAttributeValue(final QName attributeValue) {
        final String trimmedLocalName = DatatypeHelper.safeTrimOrNullString(attributeValue.getLocalPart());
        if (trimmedLocalName == null) {
            throw new IllegalArgumentException("Local name may not be null or empty");
        }
        final String trimmedPrefix = DatatypeHelper.safeTrimOrNullString(attributeValue.getPrefix());
        String qualifiedName;
        if (trimmedPrefix != null) {
            qualifiedName = String.valueOf(trimmedPrefix) + ":" + DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        else {
            qualifiedName = DatatypeHelper.safeTrimOrNullString(trimmedLocalName);
        }
        return qualifiedName;
    }
}
