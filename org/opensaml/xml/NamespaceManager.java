// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.util.DatatypeHelper;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.opensaml.xml.util.LazyMap;
import org.opensaml.xml.util.LazySet;
import java.util.Map;
import java.util.Set;

public class NamespaceManager
{
    public static final String DEFAULT_NS_TOKEN = "#default";
    private static final Namespace XML_NAMESPACE;
    private static final Namespace XSI_NAMESPACE;
    private XMLObject owner;
    private Namespace elementName;
    private Namespace elementType;
    private Set<Namespace> decls;
    private Set<Namespace> usage;
    private Set<Namespace> attrNames;
    private Map<String, Namespace> attrValues;
    private Namespace contentValue;
    
    static {
        XML_NAMESPACE = new Namespace("http://www.w3.org/XML/1998/namespace", "xml");
        XSI_NAMESPACE = new Namespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
    }
    
    public NamespaceManager(final XMLObject owningObject) {
        this.owner = owningObject;
        this.decls = new LazySet<Namespace>();
        this.usage = new LazySet<Namespace>();
        this.attrNames = new LazySet<Namespace>();
        this.attrValues = new LazyMap<String, Namespace>();
    }
    
    public static String generateAttributeID(final QName name) {
        return name.toString();
    }
    
    public XMLObject getOwner() {
        return this.owner;
    }
    
    public Set<Namespace> getNamespaces() {
        final Set<Namespace> namespaces = this.mergeNamespaceCollections(this.decls, this.usage, this.attrNames, this.attrValues.values());
        this.addNamespace(namespaces, this.getElementNameNamespace());
        this.addNamespace(namespaces, this.getElementTypeNamespace());
        this.addNamespace(namespaces, this.contentValue);
        return namespaces;
    }
    
    public void registerNamespace(final Namespace namespace) {
        this.addNamespace(this.usage, namespace);
    }
    
    public void deregisterNamespace(final Namespace namespace) {
        this.removeNamespace(this.usage, namespace);
    }
    
    public void registerNamespaceDeclaration(final Namespace namespace) {
        namespace.setAlwaysDeclare(true);
        this.addNamespace(this.decls, namespace);
    }
    
    public void deregisterNamespaceDeclaration(final Namespace namespace) {
        this.removeNamespace(this.decls, namespace);
    }
    
    public void registerAttributeName(final QName attributeName) {
        if (this.checkQName(attributeName)) {
            this.addNamespace(this.attrNames, this.buildNamespace(attributeName));
        }
    }
    
    public void deregisterAttributeName(final QName attributeName) {
        if (this.checkQName(attributeName)) {
            this.removeNamespace(this.attrNames, this.buildNamespace(attributeName));
        }
    }
    
    public void registerAttributeValue(final String attributeID, final QName attributeValue) {
        if (this.checkQName(attributeValue)) {
            this.attrValues.put(attributeID, this.buildNamespace(attributeValue));
        }
    }
    
    public void deregisterAttributeValue(final String attributeID) {
        this.attrValues.remove(attributeID);
    }
    
    public void registerContentValue(final QName content) {
        if (this.checkQName(content)) {
            this.contentValue = this.buildNamespace(content);
        }
    }
    
    public void deregisterContentValue() {
        this.contentValue = null;
    }
    
    public Set<String> getNonVisibleNamespacePrefixes() {
        final LazySet<String> prefixes = new LazySet<String>();
        this.addPrefixes(prefixes, this.getNonVisibleNamespaces());
        return prefixes;
    }
    
    public Set<Namespace> getNonVisibleNamespaces() {
        final LazySet<Namespace> nonVisibleCandidates = new LazySet<Namespace>();
        final List<XMLObject> children = this.getOwner().getOrderedChildren();
        if (children != null) {
            for (final XMLObject child : this.getOwner().getOrderedChildren()) {
                if (child != null) {
                    final Set<Namespace> childNonVisibleNamespaces = child.getNamespaceManager().getNonVisibleNamespaces();
                    if (childNonVisibleNamespaces == null || childNonVisibleNamespaces.isEmpty()) {
                        continue;
                    }
                    nonVisibleCandidates.addAll(childNonVisibleNamespaces);
                }
            }
        }
        nonVisibleCandidates.addAll(this.getNonVisibleNamespaceCandidates());
        nonVisibleCandidates.removeAll(this.getVisibleNamespaces());
        nonVisibleCandidates.remove(NamespaceManager.XML_NAMESPACE);
        return nonVisibleCandidates;
    }
    
    public Set<Namespace> getAllNamespacesInSubtreeScope() {
        final LazySet<Namespace> namespaces = new LazySet<Namespace>();
        final List<XMLObject> children = this.getOwner().getOrderedChildren();
        if (children != null) {
            for (final XMLObject child : this.getOwner().getOrderedChildren()) {
                if (child != null) {
                    final Set<Namespace> childNamespaces = child.getNamespaceManager().getAllNamespacesInSubtreeScope();
                    if (childNamespaces == null || childNamespaces.isEmpty()) {
                        continue;
                    }
                    namespaces.addAll(childNamespaces);
                }
            }
        }
        for (final Namespace myNS : this.getNamespaces()) {
            namespaces.add(this.copyNamespace(myNS));
        }
        return namespaces;
    }
    
    public void registerElementName(final QName name) {
        if (this.checkQName(name)) {
            this.elementName = this.buildNamespace(name);
        }
    }
    
    public void registerElementType(final QName type) {
        if (type != null) {
            if (this.checkQName(type)) {
                this.elementType = this.buildNamespace(type);
            }
        }
        else {
            this.elementType = null;
        }
    }
    
    private Namespace getElementNameNamespace() {
        if (this.elementName == null && this.checkQName(this.owner.getElementQName())) {
            this.elementName = this.buildNamespace(this.owner.getElementQName());
        }
        return this.elementName;
    }
    
    private Namespace getElementTypeNamespace() {
        if (this.elementType == null) {
            final QName type = this.owner.getSchemaType();
            if (type != null && this.checkQName(type)) {
                this.elementType = this.buildNamespace(type);
            }
        }
        return this.elementType;
    }
    
    private Namespace buildNamespace(final QName name) {
        final String uri = DatatypeHelper.safeTrimOrNullString(name.getNamespaceURI());
        if (uri == null) {
            throw new IllegalArgumentException("A non-empty namespace URI must be supplied");
        }
        final String prefix = DatatypeHelper.safeTrimOrNullString(name.getPrefix());
        return new Namespace(uri, prefix);
    }
    
    private void addNamespace(final Set<Namespace> namespaces, final Namespace newNamespace) {
        if (newNamespace == null) {
            return;
        }
        if (namespaces.size() == 0) {
            namespaces.add(newNamespace);
            return;
        }
        for (final Namespace namespace : namespaces) {
            if (DatatypeHelper.safeEquals(namespace.getNamespaceURI(), newNamespace.getNamespaceURI()) && DatatypeHelper.safeEquals(namespace.getNamespacePrefix(), newNamespace.getNamespacePrefix())) {
                if (newNamespace.alwaysDeclare() && !namespace.alwaysDeclare()) {
                    namespaces.remove(namespace);
                    namespaces.add(newNamespace);
                }
                return;
            }
        }
        namespaces.add(newNamespace);
    }
    
    private void removeNamespace(final Set<Namespace> namespaces, final Namespace oldNamespace) {
        if (oldNamespace == null) {
            return;
        }
        final Iterator<Namespace> iter = namespaces.iterator();
        while (iter.hasNext()) {
            final Namespace namespace = iter.next();
            if (DatatypeHelper.safeEquals(namespace.getNamespaceURI(), oldNamespace.getNamespaceURI()) && DatatypeHelper.safeEquals(namespace.getNamespacePrefix(), oldNamespace.getNamespacePrefix())) {
                iter.remove();
            }
        }
    }
    
    private Set<Namespace> mergeNamespaceCollections(final Collection<Namespace>... namespaces) {
        final LazySet<Namespace> newNamespaces = new LazySet<Namespace>();
        for (final Collection<Namespace> nsCollection : namespaces) {
            for (final Namespace ns : nsCollection) {
                if (ns != null) {
                    this.addNamespace(newNamespaces, ns);
                }
            }
        }
        return newNamespaces;
    }
    
    private Set<Namespace> getVisibleNamespaces() {
        final LazySet<Namespace> namespaces = new LazySet<Namespace>();
        if (this.getElementNameNamespace() != null) {
            namespaces.add(this.copyNamespace(this.getElementNameNamespace()));
        }
        if (this.getElementTypeNamespace() != null) {
            namespaces.add(this.copyNamespace(NamespaceManager.XSI_NAMESPACE));
        }
        for (final Namespace attribName : this.attrNames) {
            if (attribName != null) {
                namespaces.add(this.copyNamespace(attribName));
            }
        }
        return namespaces;
    }
    
    private Set<Namespace> getNonVisibleNamespaceCandidates() {
        final LazySet<Namespace> namespaces = new LazySet<Namespace>();
        if (this.getElementTypeNamespace() != null) {
            namespaces.add(this.copyNamespace(this.getElementTypeNamespace()));
        }
        for (final Namespace attribValue : this.attrValues.values()) {
            if (attribValue != null) {
                namespaces.add(this.copyNamespace(attribValue));
            }
        }
        if (this.contentValue != null) {
            namespaces.add(this.copyNamespace(this.contentValue));
        }
        return namespaces;
    }
    
    private Namespace copyNamespace(final Namespace orig) {
        if (orig == null) {
            return null;
        }
        return new Namespace(orig.getNamespaceURI(), orig.getNamespacePrefix());
    }
    
    private void addPrefixes(final Set<String> prefixes, final Collection<Namespace> namespaces) {
        for (final Namespace ns : namespaces) {
            String prefix = DatatypeHelper.safeTrimOrNullString(ns.getNamespacePrefix());
            if (prefix == null) {
                prefix = "#default";
            }
            prefixes.add(prefix);
        }
    }
    
    private boolean checkQName(final QName name) {
        return !DatatypeHelper.isEmpty(name.getNamespaceURI());
    }
}
