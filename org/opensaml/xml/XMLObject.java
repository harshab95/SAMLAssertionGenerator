// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.schema.XSBooleanValue;
import java.util.List;
import java.util.Set;
import org.opensaml.xml.util.IDIndex;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public interface XMLObject
{
    @Deprecated
    void addNamespace(final Namespace p0);
    
    void detach();
    
    Element getDOM();
    
    QName getElementQName();
    
    IDIndex getIDIndex();
    
    NamespaceManager getNamespaceManager();
    
    Set<Namespace> getNamespaces();
    
    String getNoNamespaceSchemaLocation();
    
    List<XMLObject> getOrderedChildren();
    
    XMLObject getParent();
    
    String getSchemaLocation();
    
    QName getSchemaType();
    
    boolean hasChildren();
    
    boolean hasParent();
    
    void releaseChildrenDOM(final boolean p0);
    
    void releaseDOM();
    
    void releaseParentDOM(final boolean p0);
    
    @Deprecated
    void removeNamespace(final Namespace p0);
    
    XMLObject resolveID(final String p0);
    
    XMLObject resolveIDFromRoot(final String p0);
    
    void setDOM(final Element p0);
    
    void setNoNamespaceSchemaLocation(final String p0);
    
    void setParent(final XMLObject p0);
    
    void setSchemaLocation(final String p0);
    
    Boolean isNil();
    
    XSBooleanValue isNilXSBoolean();
    
    void setNil(final Boolean p0);
    
    void setNil(final XSBooleanValue p0);
}
