// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

public class DocumentInternalIDContentReference extends URIContentReference
{
    public DocumentInternalIDContentReference(final String referenceID) {
        super("#" + referenceID);
    }
}
