// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.keyinfo;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.security.spec.InvalidKeySpecException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import org.opensaml.xml.util.DatatypeHelper;
import java.security.spec.KeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.interfaces.DSAParams;
import java.security.spec.DSAParameterSpec;
import java.security.KeyException;
import org.opensaml.xml.signature.Q;
import org.opensaml.xml.signature.P;
import org.opensaml.xml.signature.G;
import org.opensaml.xml.signature.Y;
import org.opensaml.xml.signature.DSAKeyValue;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.signature.Exponent;
import org.opensaml.xml.signature.Modulus;
import org.opensaml.xml.signature.RSAKeyValue;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import org.opensaml.xml.signature.KeyValue;
import java.security.PublicKey;
import org.opensaml.xml.signature.X509SKI;
import org.opensaml.xml.signature.X509SerialNumber;
import org.opensaml.xml.signature.X509IssuerName;
import org.opensaml.xml.signature.X509IssuerSerial;
import java.math.BigInteger;
import org.opensaml.xml.signature.X509SubjectName;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import org.opensaml.xml.security.x509.X509Util;
import org.opensaml.xml.util.Base64;
import java.security.cert.CertificateException;
import java.util.Collection;
import org.opensaml.xml.signature.X509Data;
import java.security.cert.X509Certificate;
import org.opensaml.xml.Configuration;
import java.util.Iterator;
import org.opensaml.xml.signature.KeyName;
import java.util.LinkedList;
import java.util.List;
import org.opensaml.xml.signature.KeyInfo;
import java.security.cert.CertificateFactory;

public class KeyInfoHelper
{
    private static CertificateFactory x509CertFactory;
    
    protected KeyInfoHelper() {
    }
    
    public static List<String> getKeyNames(final KeyInfo keyInfo) {
        final List<String> keynameList = new LinkedList<String>();
        if (keyInfo == null) {
            return keynameList;
        }
        final List<KeyName> keyNames = keyInfo.getKeyNames();
        for (final KeyName keyName : keyNames) {
            if (keyName.getValue() != null) {
                keynameList.add(keyName.getValue());
            }
        }
        return keynameList;
    }
    
    public static void addKeyName(final KeyInfo keyInfo, final String keyNameValue) {
        final KeyName keyName = Configuration.getBuilderFactory().getBuilder(KeyName.DEFAULT_ELEMENT_NAME).buildObject(KeyName.DEFAULT_ELEMENT_NAME);
        keyName.setValue(keyNameValue);
        keyInfo.getKeyNames().add(keyName);
    }
    
    public static List<X509Certificate> getCertificates(final KeyInfo keyInfo) throws CertificateException {
        final List<X509Certificate> certList = new LinkedList<X509Certificate>();
        if (keyInfo == null) {
            return certList;
        }
        final List<X509Data> x509Datas = keyInfo.getX509Datas();
        for (final X509Data x509Data : x509Datas) {
            if (x509Data != null) {
                certList.addAll(getCertificates(x509Data));
            }
        }
        return certList;
    }
    
    public static List<X509Certificate> getCertificates(final X509Data x509Data) throws CertificateException {
        final List<X509Certificate> certList = new LinkedList<X509Certificate>();
        if (x509Data == null) {
            return certList;
        }
        for (final org.opensaml.xml.signature.X509Certificate xmlCert : x509Data.getX509Certificates()) {
            if (xmlCert != null && xmlCert.getValue() != null) {
                final X509Certificate newCert = getCertificate(xmlCert);
                certList.add(newCert);
            }
        }
        return certList;
    }
    
    public static X509Certificate getCertificate(final org.opensaml.xml.signature.X509Certificate xmlCert) throws CertificateException {
        if (xmlCert == null || xmlCert.getValue() == null) {
            return null;
        }
        final Collection<X509Certificate> certs = X509Util.decodeCertificate(Base64.decode(xmlCert.getValue()));
        if (certs != null && certs.iterator().hasNext()) {
            return certs.iterator().next();
        }
        return null;
    }
    
    public static List<X509CRL> getCRLs(final KeyInfo keyInfo) throws CRLException {
        final List<X509CRL> crlList = new LinkedList<X509CRL>();
        if (keyInfo == null) {
            return crlList;
        }
        final List<X509Data> x509Datas = keyInfo.getX509Datas();
        for (final X509Data x509Data : x509Datas) {
            if (x509Data != null) {
                crlList.addAll(getCRLs(x509Data));
            }
        }
        return crlList;
    }
    
    public static List<X509CRL> getCRLs(final X509Data x509Data) throws CRLException {
        final List<X509CRL> crlList = new LinkedList<X509CRL>();
        if (x509Data == null) {
            return crlList;
        }
        for (final org.opensaml.xml.signature.X509CRL xmlCRL : x509Data.getX509CRLs()) {
            if (xmlCRL != null && xmlCRL.getValue() != null) {
                final X509CRL newCRL = getCRL(xmlCRL);
                crlList.add(newCRL);
            }
        }
        return crlList;
    }
    
    public static X509CRL getCRL(final org.opensaml.xml.signature.X509CRL xmlCRL) throws CRLException {
        if (xmlCRL == null || xmlCRL.getValue() == null) {
            return null;
        }
        final Collection<X509CRL> crls = X509Util.decodeCRLs(Base64.decode(xmlCRL.getValue()));
        return crls.iterator().next();
    }
    
    public static void addCertificate(final KeyInfo keyInfo, final X509Certificate cert) throws CertificateEncodingException {
        X509Data x509Data;
        if (keyInfo.getX509Datas().size() == 0) {
            x509Data = Configuration.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
            keyInfo.getX509Datas().add(x509Data);
        }
        else {
            x509Data = keyInfo.getX509Datas().get(0);
        }
        x509Data.getX509Certificates().add(buildX509Certificate(cert));
    }
    
    public static void addCRL(final KeyInfo keyInfo, final X509CRL crl) throws CRLException {
        X509Data x509Data;
        if (keyInfo.getX509Datas().size() == 0) {
            x509Data = Configuration.getBuilderFactory().getBuilder(X509Data.DEFAULT_ELEMENT_NAME).buildObject(X509Data.DEFAULT_ELEMENT_NAME);
            keyInfo.getX509Datas().add(x509Data);
        }
        else {
            x509Data = keyInfo.getX509Datas().get(0);
        }
        x509Data.getX509CRLs().add(buildX509CRL(crl));
    }
    
    public static org.opensaml.xml.signature.X509Certificate buildX509Certificate(final X509Certificate cert) throws CertificateEncodingException {
        final org.opensaml.xml.signature.X509Certificate xmlCert = Configuration.getBuilderFactory().getBuilder(org.opensaml.xml.signature.X509Certificate.DEFAULT_ELEMENT_NAME).buildObject(org.opensaml.xml.signature.X509Certificate.DEFAULT_ELEMENT_NAME);
        xmlCert.setValue(Base64.encodeBytes(cert.getEncoded()));
        return xmlCert;
    }
    
    public static org.opensaml.xml.signature.X509CRL buildX509CRL(final X509CRL crl) throws CRLException {
        final org.opensaml.xml.signature.X509CRL xmlCRL = Configuration.getBuilderFactory().getBuilder(org.opensaml.xml.signature.X509CRL.DEFAULT_ELEMENT_NAME).buildObject(org.opensaml.xml.signature.X509CRL.DEFAULT_ELEMENT_NAME);
        xmlCRL.setValue(Base64.encodeBytes(crl.getEncoded()));
        return xmlCRL;
    }
    
    public static X509SubjectName buildX509SubjectName(final String subjectName) {
        final X509SubjectName xmlSubjectName = Configuration.getBuilderFactory().getBuilder(X509SubjectName.DEFAULT_ELEMENT_NAME).buildObject(X509SubjectName.DEFAULT_ELEMENT_NAME);
        xmlSubjectName.setValue(subjectName);
        return xmlSubjectName;
    }
    
    public static X509IssuerSerial buildX509IssuerSerial(final String issuerName, final BigInteger serialNumber) {
        final X509IssuerName xmlIssuerName = Configuration.getBuilderFactory().getBuilder(X509IssuerName.DEFAULT_ELEMENT_NAME).buildObject(X509IssuerName.DEFAULT_ELEMENT_NAME);
        xmlIssuerName.setValue(issuerName);
        final X509SerialNumber xmlSerialNumber = Configuration.getBuilderFactory().getBuilder(X509SerialNumber.DEFAULT_ELEMENT_NAME).buildObject(X509SerialNumber.DEFAULT_ELEMENT_NAME);
        xmlSerialNumber.setValue(serialNumber);
        final X509IssuerSerial xmlIssuerSerial = Configuration.getBuilderFactory().getBuilder(X509IssuerSerial.DEFAULT_ELEMENT_NAME).buildObject(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
        xmlIssuerSerial.setX509IssuerName(xmlIssuerName);
        xmlIssuerSerial.setX509SerialNumber(xmlSerialNumber);
        return xmlIssuerSerial;
    }
    
    public static X509SKI buildX509SKI(final X509Certificate javaCert) {
        final byte[] skiPlainValue = X509Util.getSubjectKeyIdentifier(javaCert);
        if (skiPlainValue == null || skiPlainValue.length == 0) {
            return null;
        }
        final X509SKI xmlSKI = Configuration.getBuilderFactory().getBuilder(X509SKI.DEFAULT_ELEMENT_NAME).buildObject(X509SKI.DEFAULT_ELEMENT_NAME);
        xmlSKI.setValue(Base64.encodeBytes(skiPlainValue));
        return xmlSKI;
    }
    
    public static void addPublicKey(final KeyInfo keyInfo, final PublicKey pk) throws IllegalArgumentException {
        final KeyValue keyValue = Configuration.getBuilderFactory().getBuilder(KeyValue.DEFAULT_ELEMENT_NAME).buildObject(KeyValue.DEFAULT_ELEMENT_NAME);
        if (pk instanceof RSAPublicKey) {
            keyValue.setRSAKeyValue(buildRSAKeyValue((RSAPublicKey)pk));
        }
        else {
            if (!(pk instanceof DSAPublicKey)) {
                throw new IllegalArgumentException("Only RSAPublicKey and DSAPublicKey are supported");
            }
            keyValue.setDSAKeyValue(buildDSAKeyValue((DSAPublicKey)pk));
        }
        keyInfo.getKeyValues().add(keyValue);
    }
    
    public static RSAKeyValue buildRSAKeyValue(final RSAPublicKey rsaPubKey) {
        final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        final RSAKeyValue rsaKeyValue = builderFactory.getBuilder(RSAKeyValue.DEFAULT_ELEMENT_NAME).buildObject(RSAKeyValue.DEFAULT_ELEMENT_NAME);
        final Modulus modulus = builderFactory.getBuilder(Modulus.DEFAULT_ELEMENT_NAME).buildObject(Modulus.DEFAULT_ELEMENT_NAME);
        final Exponent exponent = builderFactory.getBuilder(Exponent.DEFAULT_ELEMENT_NAME).buildObject(Exponent.DEFAULT_ELEMENT_NAME);
        modulus.setValueBigInt(rsaPubKey.getModulus());
        rsaKeyValue.setModulus(modulus);
        exponent.setValueBigInt(rsaPubKey.getPublicExponent());
        rsaKeyValue.setExponent(exponent);
        return rsaKeyValue;
    }
    
    public static DSAKeyValue buildDSAKeyValue(final DSAPublicKey dsaPubKey) {
        final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
        final DSAKeyValue dsaKeyValue = builderFactory.getBuilder(DSAKeyValue.DEFAULT_ELEMENT_NAME).buildObject(DSAKeyValue.DEFAULT_ELEMENT_NAME);
        final Y y = builderFactory.getBuilder(Y.DEFAULT_ELEMENT_NAME).buildObject(Y.DEFAULT_ELEMENT_NAME);
        final G g = builderFactory.getBuilder(G.DEFAULT_ELEMENT_NAME).buildObject(G.DEFAULT_ELEMENT_NAME);
        final P p = builderFactory.getBuilder(P.DEFAULT_ELEMENT_NAME).buildObject(P.DEFAULT_ELEMENT_NAME);
        final Q q = builderFactory.getBuilder(Q.DEFAULT_ELEMENT_NAME).buildObject(Q.DEFAULT_ELEMENT_NAME);
        y.setValueBigInt(dsaPubKey.getY());
        dsaKeyValue.setY(y);
        g.setValueBigInt(dsaPubKey.getParams().getG());
        dsaKeyValue.setG(g);
        p.setValueBigInt(dsaPubKey.getParams().getP());
        dsaKeyValue.setP(p);
        q.setValueBigInt(dsaPubKey.getParams().getQ());
        dsaKeyValue.setQ(q);
        return dsaKeyValue;
    }
    
    public static List<PublicKey> getPublicKeys(final KeyInfo keyInfo) throws KeyException {
        final List<PublicKey> keys = new LinkedList<PublicKey>();
        if (keyInfo == null || keyInfo.getKeyValues() == null) {
            return keys;
        }
        for (final KeyValue keyDescriptor : keyInfo.getKeyValues()) {
            keys.add(getKey(keyDescriptor));
        }
        return keys;
    }
    
    public static PublicKey getKey(final KeyValue keyValue) throws KeyException {
        if (keyValue.getDSAKeyValue() != null) {
            return getDSAKey(keyValue.getDSAKeyValue());
        }
        if (keyValue.getRSAKeyValue() != null) {
            return getRSAKey(keyValue.getRSAKeyValue());
        }
        return null;
    }
    
    public static PublicKey getDSAKey(final DSAKeyValue keyDescriptor) throws KeyException {
        if (!hasCompleteDSAParams(keyDescriptor)) {
            throw new KeyException("DSAKeyValue element did not contain at least one of DSA parameters P, Q or G");
        }
        final BigInteger gComponent = keyDescriptor.getG().getValueBigInt();
        final BigInteger pComponent = keyDescriptor.getP().getValueBigInt();
        final BigInteger qComponent = keyDescriptor.getQ().getValueBigInt();
        final DSAParams dsaParams = new DSAParameterSpec(pComponent, qComponent, gComponent);
        return getDSAKey(keyDescriptor, dsaParams);
    }
    
    public static PublicKey getDSAKey(final DSAKeyValue keyDescriptor, final DSAParams dsaParams) throws KeyException {
        final BigInteger yComponent = keyDescriptor.getY().getValueBigInt();
        final DSAPublicKeySpec keySpec = new DSAPublicKeySpec(yComponent, dsaParams.getP(), dsaParams.getQ(), dsaParams.getG());
        return buildKey(keySpec, "DSA");
    }
    
    public static boolean hasCompleteDSAParams(final DSAKeyValue keyDescriptor) {
        return keyDescriptor.getG() != null && !DatatypeHelper.isEmpty(keyDescriptor.getG().getValue()) && keyDescriptor.getP() != null && !DatatypeHelper.isEmpty(keyDescriptor.getP().getValue()) && keyDescriptor.getQ() != null && !DatatypeHelper.isEmpty(keyDescriptor.getQ().getValue());
    }
    
    public static PublicKey getRSAKey(final RSAKeyValue keyDescriptor) throws KeyException {
        final BigInteger modulus = keyDescriptor.getModulus().getValueBigInt();
        final BigInteger exponent = keyDescriptor.getExponent().getValueBigInt();
        final RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
        return buildKey(keySpec, "RSA");
    }
    
    public static final BigInteger decodeBigIntegerFromCryptoBinary(final String base64Value) {
        return new BigInteger(1, Base64.decode(base64Value));
    }
    
    public static final String encodeCryptoBinaryFromBigInteger(final BigInteger bigInt) {
        final byte[] bigIntBytes = org.apache.xml.security.utils.Base64.encode(bigInt, bigInt.bitLength());
        return Base64.encodeBytes(bigIntBytes);
    }
    
    protected static PublicKey buildKey(final KeySpec keySpec, final String keyAlgorithm) throws KeyException {
        final Logger log = getLogger();
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            return keyFactory.generatePublic(keySpec);
        }
        catch (NoSuchAlgorithmException e) {
            log.error(String.valueOf(keyAlgorithm) + " algorithm is not supported by this VM", (Throwable)e);
            throw new KeyException(String.valueOf(keyAlgorithm) + "algorithm is not supported by the JCE", e);
        }
        catch (InvalidKeySpecException e2) {
            log.error("Invalid key information", (Throwable)e2);
            throw new KeyException("Invalid key information", e2);
        }
    }
    
    protected static CertificateFactory getX509CertFactory() throws CertificateException {
        if (KeyInfoHelper.x509CertFactory == null) {
            KeyInfoHelper.x509CertFactory = CertificateFactory.getInstance("X.509");
        }
        return KeyInfoHelper.x509CertFactory;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)KeyInfoHelper.class);
    }
}
