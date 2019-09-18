// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.util.XMLConstants;
import java.util.Iterator;
import org.opensaml.xml.util.DatatypeHelper;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.util.IDIndex;
import org.opensaml.xml.schema.XSBooleanValue;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;
import org.slf4j.Logger;

public abstract class AbstractXMLObject implements XMLObject
{
    private final Logger log;
    private XMLObject parent;
    private QName elementQname;
    private String schemaLocation;
    private String noNamespaceSchemaLocation;
    private QName typeQname;
    private Element dom;
    private XSBooleanValue nil;
    private NamespaceManager nsManager;
    private final IDIndex idIndex;
    
    protected AbstractXMLObject(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        this.log = LoggerFactory.getLogger((Class)AbstractXMLObject.class);
        this.nsManager = new NamespaceManager(this);
        this.idIndex = new IDIndex(this);
        this.elementQname = XMLHelper.constructQName(namespaceURI, elementLocalName, namespacePrefix);
        if (namespaceURI != null) {
            this.setElementNamespacePrefix(namespacePrefix);
        }
    }
    
    public void addNamespace(final Namespace newNamespace) {
        this.getNamespaceManager().registerNamespace(newNamespace);
    }
    
    public void detach() {
        this.releaseParentDOM(true);
        this.parent = null;
    }
    
    public Element getDOM() {
        return this.dom;
    }
    
    public QName getElementQName() {
        return new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart(), this.elementQname.getPrefix());
    }
    
    public IDIndex getIDIndex() {
        return this.idIndex;
    }
    
    public NamespaceManager getNamespaceManager() {
        return this.nsManager;
    }
    
    public Set<Namespace> getNamespaces() {
        return Collections.unmodifiableSet((Set<? extends Namespace>)this.getNamespaceManager().getNamespaces());
    }
    
    public String getNoNamespaceSchemaLocation() {
        return this.noNamespaceSchemaLocation;
    }
    
    public XMLObject getParent() {
        return this.parent;
    }
    
    public String getSchemaLocation() {
        return this.schemaLocation;
    }
    
    public QName getSchemaType() {
        return this.typeQname;
    }
    
    public boolean hasChildren() {
        final List<? extends XMLObject> children = this.getOrderedChildren();
        return children != null && children.size() > 0;
    }
    
    public boolean hasParent() {
        return this.getParent() != null;
    }
    
    protected void manageQualifiedAttributeNamespace(final QName attributeName, final boolean hasValue) {
        if (hasValue) {
            this.getNamespaceManager().registerAttributeName(attributeName);
        }
        else {
            this.getNamespaceManager().deregisterAttributeName(attributeName);
        }
    }
    
    @Deprecated
    protected QName prepareForAssignment(final QName oldValue, final QName newValue) {
        if (oldValue != null) {
            if (!oldValue.equals(newValue)) {
                if (newValue != null) {
                    final Namespace newNamespace = new Namespace(newValue.getNamespaceURI(), newValue.getPrefix());
                    this.addNamespace(newNamespace);
                }
                this.releaseThisandParentDOM();
            }
            return newValue;
        }
        if (newValue != null) {
            final Namespace newNamespace = new Namespace(newValue.getNamespaceURI(), newValue.getPrefix());
            this.addNamespace(newNamespace);
            this.releaseThisandParentDOM();
            return newValue;
        }
        return null;
    }
    
    protected QName prepareElementContentForAssignment(final QName oldValue, final QName newValue) {
        if (oldValue != null) {
            this.getNamespaceManager().deregisterContentValue();
            if (!oldValue.equals(newValue)) {
                if (newValue != null) {
                    this.getNamespaceManager().registerContentValue(newValue);
                }
                this.releaseThisandParentDOM();
            }
            return newValue;
        }
        if (newValue != null) {
            this.getNamespaceManager().registerContentValue(newValue);
            this.releaseThisandParentDOM();
            return newValue;
        }
        return null;
    }
    
    protected QName prepareAttributeValueForAssignment(final String attributeID, final QName oldValue, final QName newValue) {
        if (oldValue != null) {
            this.getNamespaceManager().deregisterAttributeValue(attributeID);
            if (!oldValue.equals(newValue)) {
                if (newValue != null) {
                    this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
                }
                this.releaseThisandParentDOM();
            }
            return newValue;
        }
        if (newValue != null) {
            this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
            this.releaseThisandParentDOM();
            return newValue;
        }
        return null;
    }
    
    protected String prepareForAssignment(final String oldValue, final String newValue) {
        final String newString = DatatypeHelper.safeTrimOrNullString(newValue);
        if (!DatatypeHelper.safeEquals(oldValue, newString)) {
            this.releaseThisandParentDOM();
        }
        return newString;
    }
    
    protected <T> T prepareForAssignment(final T oldValue, final T newValue) {
        if (oldValue != null) {
            if (!oldValue.equals(newValue)) {
                this.releaseThisandParentDOM();
            }
            return newValue;
        }
        if (newValue != null) {
            this.releaseThisandParentDOM();
            return newValue;
        }
        return null;
    }
    
    protected <T extends XMLObject> T prepareForAssignment(final T oldValue, final T newValue) {
        if (newValue != null && newValue.hasParent()) {
            throw new IllegalArgumentException(String.valueOf(newValue.getClass().getName()) + " cannot be added - it is already the child of another SAML Object");
        }
        if (oldValue != null) {
            if (!oldValue.equals(newValue)) {
                oldValue.setParent(null);
                this.releaseThisandParentDOM();
                this.idIndex.deregisterIDMappings(oldValue.getIDIndex());
                if (newValue != null) {
                    newValue.setParent(this);
                    this.idIndex.registerIDMappings(newValue.getIDIndex());
                }
            }
            return newValue;
        }
        if (newValue != null) {
            this.releaseThisandParentDOM();
            newValue.setParent(this);
            this.idIndex.registerIDMappings(newValue.getIDIndex());
            return newValue;
        }
        return null;
    }
    
    protected void registerOwnID(final String oldID, final String newID) {
        final String newString = DatatypeHelper.safeTrimOrNullString(newID);
        if (!DatatypeHelper.safeEquals(oldID, newString)) {
            if (oldID != null) {
                this.idIndex.deregisterIDMapping(oldID);
            }
            if (newString != null) {
                this.idIndex.registerIDMapping(newString, this);
            }
        }
    }
    
    public void releaseChildrenDOM(final boolean propagateRelease) {
        this.log.trace("Releasing cached DOM reprsentation for children of {} with propagation set to {}", (Object)this.getElementQName(), (Object)propagateRelease);
        if (this.getOrderedChildren() != null) {
            for (final XMLObject child : this.getOrderedChildren()) {
                if (child != null) {
                    child.releaseDOM();
                    if (!propagateRelease) {
                        continue;
                    }
                    child.releaseChildrenDOM(propagateRelease);
                }
            }
        }
    }
    
    public void releaseDOM() {
        this.log.trace("Releasing cached DOM reprsentation for {}", (Object)this.getElementQName());
        this.setDOM(null);
    }
    
    public void releaseParentDOM(final boolean propagateRelease) {
        this.log.trace("Releasing cached DOM reprsentation for parent of {} with propagation set to {}", (Object)this.getElementQName(), (Object)propagateRelease);
        final XMLObject parentElement = this.getParent();
        if (parentElement != null) {
            this.parent.releaseDOM();
            if (propagateRelease) {
                this.parent.releaseParentDOM(propagateRelease);
            }
        }
    }
    
    public void releaseThisAndChildrenDOM() {
        if (this.getDOM() != null) {
            this.releaseDOM();
            this.releaseChildrenDOM(true);
        }
    }
    
    public void releaseThisandParentDOM() {
        if (this.getDOM() != null) {
            this.releaseDOM();
            this.releaseParentDOM(true);
        }
    }
    
    public void removeNamespace(final Namespace namespace) {
        this.getNamespaceManager().deregisterNamespace(namespace);
    }
    
    public XMLObject resolveID(final String id) {
        return this.idIndex.lookup(id);
    }
    
    public XMLObject resolveIDFromRoot(final String id) {
        XMLObject root;
        for (root = this; root.hasParent(); root = root.getParent()) {}
        return root.resolveID(id);
    }
    
    public void setDOM(final Element newDom) {
        this.dom = newDom;
    }
    
    public void setElementNamespacePrefix(final String prefix) {
        if (prefix == null) {
            this.elementQname = new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart());
        }
        else {
            this.elementQname = new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart(), prefix);
        }
        this.getNamespaceManager().registerElementName(this.elementQname);
    }
    
    protected void setElementQName(final QName elementQName) {
        this.elementQname = XMLHelper.constructQName(elementQName.getNamespaceURI(), elementQName.getLocalPart(), elementQName.getPrefix());
        this.getNamespaceManager().registerElementName(this.elementQname);
    }
    
    public void setNoNamespaceSchemaLocation(final String location) {
        this.noNamespaceSchemaLocation = DatatypeHelper.safeTrimOrNullString(location);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME, this.schemaLocation != null);
    }
    
    public void setParent(final XMLObject newParent) {
        this.parent = newParent;
    }
    
    public void setSchemaLocation(final String location) {
        this.schemaLocation = DatatypeHelper.safeTrimOrNullString(location);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_SCHEMA_LOCATION_ATTRIB_NAME, this.schemaLocation != null);
    }
    
    protected void setSchemaType(final QName type) {
        this.typeQname = type;
        this.getNamespaceManager().registerElementType(this.typeQname);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_TYPE_ATTRIB_NAME, this.typeQname != null);
    }
    
    public Boolean isNil() {
        if (this.nil != null) {
            return this.nil.getValue();
        }
        return Boolean.FALSE;
    }
    
    public XSBooleanValue isNilXSBoolean() {
        return this.nil;
    }
    
    public void setNil(final Boolean newNil) {
        if (newNil != null) {
            this.nil = this.prepareForAssignment(this.nil, new XSBooleanValue(newNil, false));
        }
        else {
            this.nil = this.prepareForAssignment(this.nil, (XSBooleanValue)null);
        }
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
    }
    
    public void setNil(final XSBooleanValue newNil) {
        this.nil = this.prepareForAssignment(this.nil, newNil);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
    }
}
