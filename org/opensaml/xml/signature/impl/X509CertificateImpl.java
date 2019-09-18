// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature.impl;

import java.util.Collections;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.IndexingObjectStore;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

public class X509CertificateImpl extends AbstractValidatingXMLObject implements X509Certificate
{
    private static final IndexingObjectStore<String> B64_CERT_STORE;
    private String b64CertIndex;
    
    static {
        B64_CERT_STORE = new IndexingObjectStore<String>();
    }
    
    protected X509CertificateImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }
    
    public String getValue() {
        return X509CertificateImpl.B64_CERT_STORE.get(this.b64CertIndex);
    }
    
    public void setValue(final String newValue) {
        final String currentCert = X509CertificateImpl.B64_CERT_STORE.get(this.b64CertIndex);
        final String b64Cert = this.prepareForAssignment(currentCert, newValue);
        if (!DatatypeHelper.safeEquals(currentCert, b64Cert)) {
            X509CertificateImpl.B64_CERT_STORE.remove(this.b64CertIndex);
            this.b64CertIndex = X509CertificateImpl.B64_CERT_STORE.put(b64Cert);
        }
    }
    
    public List<XMLObject> getOrderedChildren() {
        return (List<XMLObject>)Collections.EMPTY_LIST;
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        X509CertificateImpl.B64_CERT_STORE.remove(this.b64CertIndex);
    }
}
