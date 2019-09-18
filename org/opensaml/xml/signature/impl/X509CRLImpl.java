// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.IndexingObjectStore;
import org.opensaml.xml.signature.X509CRL;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class X509CRLImpl extends AbstractValidatingXMLObject implements X509CRL
{
    private static final IndexingObjectStore<String> B64_CRL_STORE;
    private String b64CRLIndex;
    
    static {
        B64_CRL_STORE = new IndexingObjectStore<String>();
    }
    
    protected X509CRLImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getValue() {
        return X509CRLImpl.B64_CRL_STORE.get(this.b64CRLIndex);
    }
    
    public void setValue(final String newValue) {
        final String currentCert = X509CRLImpl.B64_CRL_STORE.get(this.b64CRLIndex);
        final String b64Cert = this.prepareForAssignment(currentCert, newValue);
        if (!DatatypeHelper.safeEquals(currentCert, b64Cert)) {
            X509CRLImpl.B64_CRL_STORE.remove(this.b64CRLIndex);
            this.b64CRLIndex = X509CRLImpl.B64_CRL_STORE.put(b64Cert);
        }
    }
    
    public List<XMLObject> getOrderedChildren() {
        return (List<XMLObject>)Collections.EMPTY_LIST;
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        X509CRLImpl.B64_CRL_STORE.remove(this.b64CRLIndex);
    }
}
