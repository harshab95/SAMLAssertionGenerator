// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.util.Iterator;
import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.util.DatatypeHelper;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateEncodingException;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.XMLObject;
import java.util.List;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.Configuration;
import org.slf4j.LoggerFactory;
import org.opensaml.xml.signature.impl.X509DataBuilder;
import org.opensaml.xml.signature.impl.KeyInfoBuilder;
import org.slf4j.Logger;
import java.util.Collection;
import org.opensaml.xml.util.LazySet;
import java.util.Set;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.BasicKeyInfoGeneratorFactory;

public class X509KeyInfoGeneratorFactory extends BasicKeyInfoGeneratorFactory
{
    private X509Options options;
    
    public X509KeyInfoGeneratorFactory() {
        this.options = (X509Options)super.getOptions();
    }
    
    @Override
    public Class<? extends Credential> getCredentialType() {
        return X509Credential.class;
    }
    
    @Override
    public boolean handles(final Credential credential) {
        return credential instanceof X509Credential;
    }
    
    @Override
    public KeyInfoGenerator newInstance() {
        final X509Options newOptions = this.options.clone();
        return new X509KeyInfoGenerator(newOptions);
    }
    
    public boolean emitCRLs() {
        return this.options.emitCRLs;
    }
    
    public void setEmitCRLs(final boolean newValue) {
        X509Options.access$14(this.options, newValue);
    }
    
    public boolean emitEntityCertificate() {
        return this.options.emitEntityCertificate;
    }
    
    public void setEmitEntityCertificate(final boolean newValue) {
        X509Options.access$15(this.options, newValue);
    }
    
    public boolean emitEntityCertificateChain() {
        return this.options.emitEntityCertificateChain;
    }
    
    public void setEmitEntityCertificateChain(final boolean newValue) {
        X509Options.access$16(this.options, newValue);
    }
    
    public boolean emitSubjectAltNamesAsKeyNames() {
        return this.options.emitSubjectAltNamesAsKeyNames;
    }
    
    public void setEmitSubjectAltNamesAsKeyNames(final boolean newValue) {
        X509Options.access$17(this.options, newValue);
    }
    
    public boolean emitSubjectCNAsKeyName() {
        return this.options.emitSubjectCNAsKeyName;
    }
    
    public void setEmitSubjectCNAsKeyName(final boolean newValue) {
        X509Options.access$18(this.options, newValue);
    }
    
    public boolean emitSubjectDNAsKeyName() {
        return this.options.emitSubjectDNAsKeyName;
    }
    
    public void setEmitSubjectDNAsKeyName(final boolean newValue) {
        X509Options.access$19(this.options, newValue);
    }
    
    public boolean emitX509IssuerSerial() {
        return this.options.emitX509IssuerSerial;
    }
    
    public void setEmitX509IssuerSerial(final boolean newValue) {
        X509Options.access$20(this.options, newValue);
    }
    
    public boolean emitX509SKI() {
        return this.options.emitX509SKI;
    }
    
    public void setEmitX509SKI(final boolean newValue) {
        X509Options.access$21(this.options, newValue);
    }
    
    public boolean emitX509SubjectName() {
        return this.options.emitX509SubjectName;
    }
    
    public void setEmitX509SubjectName(final boolean newValue) {
        X509Options.access$22(this.options, newValue);
    }
    
    public Set<Integer> getSubjectAltNames() {
        return this.options.subjectAltNames;
    }
    
    public X500DNHandler getX500DNHandler() {
        return this.options.x500DNHandler;
    }
    
    public void setX500DNHandler(final X500DNHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("X500DNHandler may not be null");
        }
        X509Options.access$23(this.options, handler);
    }
    
    public String getX500SubjectDNFormat() {
        return this.options.x500SubjectDNFormat;
    }
    
    public void setX500SubjectDNFormat(final String format) {
        X509Options.access$24(this.options, format);
    }
    
    public String getX500IssuerDNFormat() {
        return this.options.x500IssuerDNFormat;
    }
    
    public void setX500IssuerDNFormat(final String format) {
        X509Options.access$25(this.options, format);
    }
    
    @Override
    protected X509Options getOptions() {
        return this.options;
    }
    
    @Override
    protected X509Options newOptions() {
        return new X509Options();
    }
    
    protected class X509Options extends BasicOptions
    {
        private boolean emitEntityCertificate;
        private boolean emitEntityCertificateChain;
        private boolean emitCRLs;
        private boolean emitX509SubjectName;
        private boolean emitX509IssuerSerial;
        private boolean emitX509SKI;
        private boolean emitSubjectDNAsKeyName;
        private boolean emitSubjectCNAsKeyName;
        private boolean emitSubjectAltNamesAsKeyNames;
        private Set<Integer> subjectAltNames;
        private X500DNHandler x500DNHandler;
        private String x500SubjectDNFormat;
        private String x500IssuerDNFormat;
        
        protected X509Options() {
            this.subjectAltNames = new LazySet<Integer>();
            this.x500DNHandler = new InternalX500DNHandler();
            this.x500SubjectDNFormat = "RFC2253";
            this.x500IssuerDNFormat = "RFC2253";
        }
        
        @Override
        protected X509Options clone() {
            final X509Options clonedOptions = (X509Options)super.clone();
            (clonedOptions.subjectAltNames = new LazySet<Integer>()).addAll(this.subjectAltNames);
            clonedOptions.x500DNHandler = this.x500DNHandler.clone();
            return clonedOptions;
        }
        
        static /* synthetic */ void access$14(final X509Options x509Options, final boolean emitCRLs) {
            x509Options.emitCRLs = emitCRLs;
        }
        
        static /* synthetic */ void access$15(final X509Options x509Options, final boolean emitEntityCertificate) {
            x509Options.emitEntityCertificate = emitEntityCertificate;
        }
        
        static /* synthetic */ void access$16(final X509Options x509Options, final boolean emitEntityCertificateChain) {
            x509Options.emitEntityCertificateChain = emitEntityCertificateChain;
        }
        
        static /* synthetic */ void access$17(final X509Options x509Options, final boolean emitSubjectAltNamesAsKeyNames) {
            x509Options.emitSubjectAltNamesAsKeyNames = emitSubjectAltNamesAsKeyNames;
        }
        
        static /* synthetic */ void access$18(final X509Options x509Options, final boolean emitSubjectCNAsKeyName) {
            x509Options.emitSubjectCNAsKeyName = emitSubjectCNAsKeyName;
        }
        
        static /* synthetic */ void access$19(final X509Options x509Options, final boolean emitSubjectDNAsKeyName) {
            x509Options.emitSubjectDNAsKeyName = emitSubjectDNAsKeyName;
        }
        
        static /* synthetic */ void access$20(final X509Options x509Options, final boolean emitX509IssuerSerial) {
            x509Options.emitX509IssuerSerial = emitX509IssuerSerial;
        }
        
        static /* synthetic */ void access$21(final X509Options x509Options, final boolean emitX509SKI) {
            x509Options.emitX509SKI = emitX509SKI;
        }
        
        static /* synthetic */ void access$22(final X509Options x509Options, final boolean emitX509SubjectName) {
            x509Options.emitX509SubjectName = emitX509SubjectName;
        }
        
        static /* synthetic */ void access$23(final X509Options x509Options, final X500DNHandler x500DNHandler) {
            x509Options.x500DNHandler = x500DNHandler;
        }
        
        static /* synthetic */ void access$24(final X509Options x509Options, final String x500SubjectDNFormat) {
            x509Options.x500SubjectDNFormat = x500SubjectDNFormat;
        }
        
        static /* synthetic */ void access$25(final X509Options x509Options, final String x500IssuerDNFormat) {
            x509Options.x500IssuerDNFormat = x500IssuerDNFormat;
        }
    }
    
    public class X509KeyInfoGenerator extends BasicKeyInfoGenerator
    {
        private final Logger log;
        private X509Options options;
        private KeyInfoBuilder keyInfoBuilder;
        private X509DataBuilder x509DataBuilder;
        
        protected X509KeyInfoGenerator(final X509Options newOptions) {
            super(newOptions);
            this.log = LoggerFactory.getLogger((Class)X509KeyInfoGenerator.class);
            this.options = newOptions;
            this.keyInfoBuilder = (KeyInfoBuilder)Configuration.getBuilderFactory().getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
            this.x509DataBuilder = (X509DataBuilder)Configuration.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME);
        }
        
        @Override
        public KeyInfo generate(final Credential credential) throws SecurityException {
            if (!(credential instanceof X509Credential)) {
                this.log.warn("X509KeyInfoGenerator was passed a credential that was not an instance of X509Credential: {}", (Object)credential.getClass().getName());
                return null;
            }
            final X509Credential x509Credential = (X509Credential)credential;
            KeyInfo keyInfo = super.generate(credential);
            if (keyInfo == null) {
                keyInfo = this.keyInfoBuilder.buildObject();
            }
            final X509Data x509Data = this.x509DataBuilder.buildObject();
            this.processEntityCertificate(keyInfo, x509Data, x509Credential);
            this.processEntityCertificateChain(keyInfo, x509Data, x509Credential);
            this.processCRLs(keyInfo, x509Data, x509Credential);
            final List<XMLObject> x509DataChildren = x509Data.getOrderedChildren();
            if (x509DataChildren != null && x509DataChildren.size() > 0) {
                keyInfo.getX509Datas().add(x509Data);
            }
            final List<XMLObject> keyInfoChildren = keyInfo.getOrderedChildren();
            if (keyInfoChildren != null && keyInfoChildren.size() > 0) {
                return keyInfo;
            }
            return null;
        }
        
        protected void processEntityCertificate(final KeyInfo keyInfo, final X509Data x509Data, final X509Credential credential) throws SecurityException {
            if (credential.getEntityCertificate() == null) {
                return;
            }
            final X509Certificate javaCert = credential.getEntityCertificate();
            this.processCertX509DataOptions(x509Data, javaCert);
            this.processCertKeyNameOptions(keyInfo, javaCert);
            if (this.options.emitEntityCertificate && !this.options.emitEntityCertificateChain) {
                try {
                    final org.opensaml.xml.signature.X509Certificate xmlCert = KeyInfoHelper.buildX509Certificate(javaCert);
                    x509Data.getX509Certificates().add(xmlCert);
                }
                catch (CertificateEncodingException e) {
                    throw new SecurityException("Error generating X509Certificate element from credential's end-entity certificate", e);
                }
            }
        }
        
        protected void processCertX509DataOptions(final X509Data x509Data, final X509Certificate cert) {
            this.processCertX509SubjectName(x509Data, cert);
            this.processCertX509IssuerSerial(x509Data, cert);
            this.processCertX509SKI(x509Data, cert);
        }
        
        protected void processCertKeyNameOptions(final KeyInfo keyInfo, final X509Certificate cert) {
            this.processSubjectDNKeyName(keyInfo, cert);
            this.processSubjectCNKeyName(keyInfo, cert);
            this.processSubjectAltNameKeyNames(keyInfo, cert);
        }
        
        protected void processCertX509SubjectName(final X509Data x509Data, final X509Certificate cert) {
            if (this.options.emitX509SubjectName) {
                final String subjectNameValue = this.getSubjectName(cert);
                if (!DatatypeHelper.isEmpty(subjectNameValue)) {
                    x509Data.getX509SubjectNames().add(KeyInfoHelper.buildX509SubjectName(subjectNameValue));
                }
            }
        }
        
        protected void processCertX509IssuerSerial(final X509Data x509Data, final X509Certificate cert) {
            if (this.options.emitX509IssuerSerial) {
                final String issuerNameValue = this.getIssuerName(cert);
                if (!DatatypeHelper.isEmpty(issuerNameValue)) {
                    x509Data.getX509IssuerSerials().add(KeyInfoHelper.buildX509IssuerSerial(issuerNameValue, cert.getSerialNumber()));
                }
            }
        }
        
        protected void processCertX509SKI(final X509Data x509Data, final X509Certificate cert) {
            if (this.options.emitX509SKI) {
                final X509SKI xmlSKI = KeyInfoHelper.buildX509SKI(cert);
                if (xmlSKI != null) {
                    x509Data.getX509SKIs().add(xmlSKI);
                }
            }
        }
        
        protected String getSubjectName(final X509Certificate cert) {
            if (cert == null) {
                return null;
            }
            if (!DatatypeHelper.isEmpty(this.options.x500SubjectDNFormat)) {
                return this.options.x500DNHandler.getName(cert.getSubjectX500Principal(), this.options.x500SubjectDNFormat);
            }
            return this.options.x500DNHandler.getName(cert.getSubjectX500Principal());
        }
        
        protected String getIssuerName(final X509Certificate cert) {
            if (cert == null) {
                return null;
            }
            if (!DatatypeHelper.isEmpty(this.options.x500IssuerDNFormat)) {
                return this.options.x500DNHandler.getName(cert.getIssuerX500Principal(), this.options.x500IssuerDNFormat);
            }
            return this.options.x500DNHandler.getName(cert.getIssuerX500Principal());
        }
        
        protected void processSubjectDNKeyName(final KeyInfo keyInfo, final X509Certificate cert) {
            if (this.options.emitSubjectDNAsKeyName) {
                final String subjectNameValue = this.getSubjectName(cert);
                if (!DatatypeHelper.isEmpty(subjectNameValue)) {
                    KeyInfoHelper.addKeyName(keyInfo, subjectNameValue);
                }
            }
        }
        
        protected void processSubjectCNKeyName(final KeyInfo keyInfo, final X509Certificate cert) {
            if (this.options.emitSubjectCNAsKeyName) {
                for (final String name : X509Util.getCommonNames(cert.getSubjectX500Principal())) {
                    if (!DatatypeHelper.isEmpty(name)) {
                        KeyInfoHelper.addKeyName(keyInfo, name);
                    }
                }
            }
        }
        
        protected void processSubjectAltNameKeyNames(final KeyInfo keyInfo, final X509Certificate cert) {
            if (this.options.emitSubjectAltNamesAsKeyNames && this.options.subjectAltNames.size() > 0) {
                final Integer[] nameTypes = new Integer[this.options.subjectAltNames.size()];
                this.options.subjectAltNames.toArray(nameTypes);
                for (final Object altNameValue : X509Util.getAltNames(cert, nameTypes)) {
                    if (altNameValue instanceof String) {
                        KeyInfoHelper.addKeyName(keyInfo, (String)altNameValue);
                    }
                    else if (altNameValue instanceof byte[]) {
                        this.log.warn("Certificate contained an alt name value as a DER-encoded byte[] (not supported)");
                    }
                    else {
                        this.log.warn("Certificate contained an alt name value with an unexpected type: {}", (Object)altNameValue.getClass().getName());
                    }
                }
            }
        }
        
        protected void processEntityCertificateChain(final KeyInfo keyInfo, final X509Data x509Data, final X509Credential credential) throws SecurityException {
            if (this.options.emitEntityCertificateChain && credential.getEntityCertificateChain() != null) {
                for (final X509Certificate javaCert : credential.getEntityCertificateChain()) {
                    try {
                        final org.opensaml.xml.signature.X509Certificate xmlCert = KeyInfoHelper.buildX509Certificate(javaCert);
                        x509Data.getX509Certificates().add(xmlCert);
                    }
                    catch (CertificateEncodingException e) {
                        throw new SecurityException("Error generating X509Certificate element from a certificate in credential's certificate chain", e);
                    }
                }
            }
        }
        
        protected void processCRLs(final KeyInfo keyInfo, final X509Data x509Data, final X509Credential credential) throws SecurityException {
            if (this.options.emitCRLs && credential.getCRLs() != null) {
                for (final X509CRL javaCRL : credential.getCRLs()) {
                    try {
                        final org.opensaml.xml.signature.X509CRL xmlCRL = KeyInfoHelper.buildX509CRL(javaCRL);
                        x509Data.getX509CRLs().add(xmlCRL);
                    }
                    catch (CRLException e) {
                        throw new SecurityException("Error generating X509CRL element from a CRL in credential's CRL list", e);
                    }
                }
            }
        }
    }
}
