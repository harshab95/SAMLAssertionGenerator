// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.x509;

import org.slf4j.LoggerFactory;
import org.opensaml.xml.util.IPAddressHelper;
import java.security.GeneralSecurityException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import org.apache.commons.ssl.TrustMaterial;
import org.opensaml.xml.util.DatatypeHelper;
import java.security.cert.CertificateException;
import java.io.File;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;
import org.bouncycastle.asn1.x509.X509Extensions;
import java.security.cert.CertificateParsingException;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.bouncycastle.asn1.DERObject;
import org.slf4j.Logger;
import java.io.IOException;
import org.bouncycastle.asn1.DERString;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.ASN1InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.security.auth.x500.X500Principal;
import org.opensaml.xml.security.SecurityException;
import java.util.Iterator;
import org.opensaml.xml.security.SecurityHelper;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class X509Util
{
    public static final String CN_OID = "2.5.4.3";
    public static final Integer OTHER_ALT_NAME;
    public static final Integer RFC822_ALT_NAME;
    public static final Integer DNS_ALT_NAME;
    public static final Integer X400ADDRESS_ALT_NAME;
    public static final Integer DIRECTORY_ALT_NAME;
    public static final Integer EDI_PARTY_ALT_NAME;
    public static final Integer URI_ALT_NAME;
    public static final Integer IP_ADDRESS_ALT_NAME;
    public static final Integer REGISTERED_ID_ALT_NAME;
    
    static {
        OTHER_ALT_NAME = new Integer(0);
        RFC822_ALT_NAME = new Integer(1);
        DNS_ALT_NAME = new Integer(2);
        X400ADDRESS_ALT_NAME = new Integer(3);
        DIRECTORY_ALT_NAME = new Integer(4);
        EDI_PARTY_ALT_NAME = new Integer(5);
        URI_ALT_NAME = new Integer(6);
        IP_ADDRESS_ALT_NAME = new Integer(7);
        REGISTERED_ID_ALT_NAME = new Integer(8);
    }
    
    protected X509Util() {
    }
    
    public static X509Certificate determineEntityCertificate(final Collection<X509Certificate> certs, final PrivateKey privateKey) throws SecurityException {
        if (certs == null || privateKey == null) {
            return null;
        }
        for (final X509Certificate certificate : certs) {
            if (SecurityHelper.matchKeyPair(certificate.getPublicKey(), privateKey)) {
                return certificate;
            }
        }
        return null;
    }
    
    public static List<String> getCommonNames(final X500Principal dn) {
        final Logger log = getLogger();
        if (dn == null) {
            return null;
        }
        log.debug("Extracting CNs from the following DN: {}", (Object)dn.toString());
        final List<String> commonNames = new LinkedList<String>();
        try {
            final ASN1InputStream asn1Stream = new ASN1InputStream(dn.getEncoded());
            final DERObject parent = asn1Stream.readObject();
            String cn = null;
            for (int i = 0; i < ((DERSequence)parent).size(); ++i) {
                final DERObject dnComponent = ((DERSequence)parent).getObjectAt(i).getDERObject();
                if (!(dnComponent instanceof DERSet)) {
                    log.debug("No DN components.");
                }
                else {
                    for (int j = 0; j < ((DERSet)dnComponent).size(); ++j) {
                        final DERSequence grandChild = (DERSequence)((DERSet)dnComponent).getObjectAt(j).getDERObject();
                        if (grandChild.getObjectAt(0) != null && grandChild.getObjectAt(0).getDERObject() instanceof DERObjectIdentifier) {
                            final DERObjectIdentifier componentId = (DERObjectIdentifier)grandChild.getObjectAt(0).getDERObject();
                            if ("2.5.4.3".equals(componentId.getId()) && grandChild.getObjectAt(1) != null && grandChild.getObjectAt(1).getDERObject() instanceof DERString) {
                                cn = ((DERString)grandChild.getObjectAt(1).getDERObject()).getString();
                                commonNames.add(cn);
                            }
                        }
                    }
                }
            }
            asn1Stream.close();
            return commonNames;
        }
        catch (IOException e) {
            log.error("Unable to extract common names from DN: ASN.1 parsing failed: " + e);
            return null;
        }
    }
    
    public static List getAltNames(final X509Certificate certificate, final Integer[] nameTypes) {
        final Logger log = getLogger();
        if (certificate == null) {
            return null;
        }
        final List<Object> names = new LinkedList<Object>();
        Collection<List<?>> altNames = null;
        try {
            altNames = (Collection<List<?>>)X509ExtensionUtil.getSubjectAlternativeNames(certificate);
        }
        catch (CertificateParsingException e) {
            log.error("Encountered an problem trying to extract Subject Alternate Name from supplied certificate: " + e);
            return names;
        }
        if (altNames != null) {
            for (final List altName : altNames) {
                for (final Integer nameType : nameTypes) {
                    if (altName.get(0).equals(nameType)) {
                        names.add(convertAltNameType(nameType, altName.get(1)));
                        break;
                    }
                }
            }
        }
        return names;
    }
    
    public static List getSubjectNames(final X509Certificate certificate, final Integer[] altNameTypes) {
        final List issuerNames = new LinkedList();
        final List<String> entityCertCNs = getCommonNames(certificate.getSubjectX500Principal());
        issuerNames.add(entityCertCNs.get(0));
        issuerNames.addAll(getAltNames(certificate, altNameTypes));
        return issuerNames;
    }
    
    public static byte[] getSubjectKeyIdentifier(final X509Certificate certificate) {
        final Logger log = getLogger();
        final byte[] derValue = certificate.getExtensionValue(X509Extensions.SubjectKeyIdentifier.getId());
        if (derValue == null || derValue.length == 0) {
            return null;
        }
        SubjectKeyIdentifier ski = null;
        try {
            ski = (SubjectKeyIdentifier)new SubjectKeyIdentifierStructure(derValue);
        }
        catch (IOException e) {
            log.error("Unable to extract subject key identifier from certificate: ASN.1 parsing failed: " + e);
            return null;
        }
        return ski.getKeyIdentifier();
    }
    
    public static Collection<X509Certificate> decodeCertificate(final File certs) throws CertificateException {
        if (!certs.exists()) {
            throw new CertificateException("Certificate file " + certs.getAbsolutePath() + " does not exist");
        }
        if (!certs.canRead()) {
            throw new CertificateException("Certificate file " + certs.getAbsolutePath() + " is not readable");
        }
        try {
            return decodeCertificate(DatatypeHelper.fileToByteArray(certs));
        }
        catch (IOException e) {
            throw new CertificateException("Error reading certificate file " + certs.getAbsolutePath(), e);
        }
    }
    
    public static Collection<X509Certificate> decodeCertificate(final byte[] certs) throws CertificateException {
        try {
            final TrustMaterial tm = new TrustMaterial(certs);
            return (Collection<X509Certificate>)tm.getCertificates();
        }
        catch (Exception e) {
            throw new CertificateException("Unable to decode X.509 certificates", e);
        }
    }
    
    public static Collection<X509CRL> decodeCRLs(final File crls) throws CRLException {
        if (!crls.exists()) {
            throw new CRLException("CRL file " + crls.getAbsolutePath() + " does not exist");
        }
        if (!crls.canRead()) {
            throw new CRLException("CRL file " + crls.getAbsolutePath() + " is not readable");
        }
        try {
            return decodeCRLs(DatatypeHelper.fileToByteArray(crls));
        }
        catch (IOException e) {
            throw new CRLException("Error reading CRL file " + crls.getAbsolutePath(), e);
        }
    }
    
    public static Collection<X509CRL> decodeCRLs(final byte[] crls) throws CRLException {
        try {
            final CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (Collection<X509CRL>)cf.generateCRLs(new ByteArrayInputStream(crls));
        }
        catch (GeneralSecurityException e) {
            throw new CRLException("Unable to decode X.509 certificates");
        }
    }
    
    public static String getIdentifiersToken(final X509Credential credential, final X500DNHandler handler) {
        X500DNHandler x500DNHandler;
        if (handler != null) {
            x500DNHandler = handler;
        }
        else {
            x500DNHandler = new InternalX500DNHandler();
        }
        final X500Principal x500Principal = credential.getEntityCertificate().getSubjectX500Principal();
        final StringBuilder builder = new StringBuilder();
        builder.append('[');
        builder.append(String.format("subjectName='%s'", x500DNHandler.getName(x500Principal)));
        if (!DatatypeHelper.isEmpty(credential.getEntityId())) {
            builder.append(String.format(" |credential entityID='%s'", DatatypeHelper.safeTrimOrNullString(credential.getEntityId())));
        }
        builder.append(']');
        return builder.toString();
    }
    
    private static Object convertAltNameType(final Integer nameType, final Object nameValue) {
        final Logger log = getLogger();
        if (X509Util.DIRECTORY_ALT_NAME.equals(nameType) || X509Util.DNS_ALT_NAME.equals(nameType) || X509Util.RFC822_ALT_NAME.equals(nameType) || X509Util.URI_ALT_NAME.equals(nameType) || X509Util.REGISTERED_ID_ALT_NAME.equals(nameType)) {
            return nameValue;
        }
        if (X509Util.IP_ADDRESS_ALT_NAME.equals(nameType)) {
            return IPAddressHelper.addressToString((byte[])nameValue);
        }
        if (X509Util.EDI_PARTY_ALT_NAME.equals(nameType) || X509Util.X400ADDRESS_ALT_NAME.equals(nameType) || X509Util.OTHER_ALT_NAME.equals(nameType)) {
            return ((DERObject)nameValue).getDEREncoded();
        }
        log.warn("Encountered unknown alt name type '{}', adding as-is", (Object)nameType);
        return nameValue;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)X509Util.class);
    }
    
    public enum ENCODING_FORMAT
    {
        PEM("PEM", 0), 
        DER("DER", 1);
        
        private ENCODING_FORMAT(final String name, final int ordinal) {
        }
    }
}
