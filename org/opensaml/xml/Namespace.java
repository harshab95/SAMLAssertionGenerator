// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.opensaml.xml.util.DatatypeHelper;

public class Namespace
{
    private String namespaceURI;
    private String namespacePrefix;
    private boolean alwaysDeclare;
    private String nsStr;
    
    public Namespace() {
    }
    
    public Namespace(final String uri, final String prefix) {
        this.namespaceURI = DatatypeHelper.safeTrimOrNullString(uri);
        this.namespacePrefix = DatatypeHelper.safeTrimOrNullString(prefix);
        this.nsStr = null;
    }
    
    public String getNamespacePrefix() {
        return this.namespacePrefix;
    }
    
    public void setNamespacePrefix(final String newPrefix) {
        this.namespacePrefix = DatatypeHelper.safeTrimOrNullString(newPrefix);
        this.nsStr = null;
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public void setNamespaceURI(final String newURI) {
        this.namespaceURI = DatatypeHelper.safeTrimOrNullString(newURI);
        this.nsStr = null;
    }
    
    @Deprecated
    public boolean alwaysDeclare() {
        return this.alwaysDeclare;
    }
    
    @Deprecated
    public void setAlwaysDeclare(final boolean shouldAlwaysDeclare) {
        this.alwaysDeclare = shouldAlwaysDeclare;
    }
    
    @Override
    public String toString() {
        if (this.nsStr == null) {
            this.constructStringRepresentation();
        }
        return this.nsStr;
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.toString().hashCode();
        hash = hash * 31 + (this.alwaysDeclare ? 0 : 1);
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Namespace) {
            final Namespace otherNamespace = (Namespace)obj;
            if (DatatypeHelper.safeEquals(otherNamespace.getNamespaceURI(), this.getNamespaceURI()) && DatatypeHelper.safeEquals(otherNamespace.getNamespacePrefix(), this.getNamespacePrefix())) {
                return otherNamespace.alwaysDeclare() == this.alwaysDeclare();
            }
        }
        return false;
    }
    
    protected void constructStringRepresentation() {
        final StringBuffer stringRep = new StringBuffer();
        stringRep.append("xmlns");
        if (this.namespacePrefix != null) {
            stringRep.append(":");
            stringRep.append(this.namespacePrefix);
        }
        stringRep.append("=\"");
        if (this.namespaceURI != null) {
            stringRep.append(this.namespaceURI);
        }
        stringRep.append("\"");
        this.nsStr = stringRep.toString();
    }
}
