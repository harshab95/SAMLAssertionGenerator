import java.util.HashMap;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.opensaml.xml.XMLObjectBuilder;
import javax.xml.namespace.QName;
import java.util.List;
import org.opensaml.xml.signature.Signer;
import org.apache.xml.security.Init;
import java.util.ArrayList;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.Issuer;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.Element;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.w3c.dom.Node;
import java.io.OutputStream;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import org.opensaml.saml2.core.Attribute;
import java.util.Iterator;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.schema.XSString;
import org.opensaml.Configuration;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.impl.ConditionsBuilder;
import org.opensaml.saml2.core.impl.AudienceBuilder;
import org.opensaml.saml2.core.impl.AudienceRestrictionBuilder;
import org.opensaml.saml2.core.impl.AuthnContextClassRefBuilder;
import org.opensaml.saml2.core.impl.AuthnContextBuilder;
import org.opensaml.saml2.core.impl.AuthnStatementBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationDataBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.impl.AssertionBuilder;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import java.net.URLEncoder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.XMLObject;
import java.security.KeyStore;
import java.util.Random;
import java.util.Map;

// 
// Decompiled by Procyon v0.5.36
// 

public class Main
{
    private static String username;
    private static String id;
    private static String issuer;
    private static String recipient;
    private static String[] requestedAudiences;
    private static boolean doAssertionSigning;
    private static Map<String, String> claims;
    private static String keyStoreFile;
    private static String keyStorePassword;
    private static String alias;
    private static String privateKeyPassword;
    private static Random random;
    private static final char[] charMapping;
    private static KeyStore keyStore;
    
    public static void main(final String[] args) throws Exception {
        System.setProperty("java.endorsed.dirs", "/home/johann/Desktop/SAML2AssertionCreator/lib");
        if (args.length < 4) {
            throw new Exception("Invalid number of arguments. Atleast 8 arguments required.");
        }
        System.out.println(System.getProperty("user.dir"));
        Main.id = Integer.toHexString(new Double(Math.random()).intValue());
        Main.issuer = args[0];
        Main.username = args[1];
        Main.recipient = args[2];
        Main.requestedAudiences = args[3].split(",");
        Main.keyStoreFile = "/Users/macbug/eclipse-workspace/SAML/src/wso2carbon.jks";//args[4];
        Main.keyStorePassword = args[5];
        Main.alias = args[6];
        Main.privateKeyPassword = args[7];
        if (args.length > 4) {
            final String[] arr$;
            final String[] claimsArray = arr$ = args[4].split(",");
            for (final String claim : arr$) {
                final String[] keyValue = claim.split("|");
                Main.claims.put(keyValue[0], keyValue[1]);
            }
        }
        Main.doAssertionSigning = true;
        final Assertion samlAssertion = buildSAMLAssertion();
        System.out.println("\nAssertion String: " + marshall((XMLObject)samlAssertion) + "\n");
        final String assertionString = URLEncoder.encode(Base64.encodeBytes(marshall((XMLObject)samlAssertion).getBytes()), "UTF-8");
        System.out.println("base64-url Encoded Assertion String: " + assertionString + "\n");
    }
    
    private static Assertion buildSAMLAssertion() throws Exception {
        DefaultBootstrap.bootstrap();
        final Assertion samlAssertion = new AssertionBuilder().buildObject();
        try {
            final DateTime currentTime = new DateTime();
            final DateTime notOnOrAfter = new DateTime(currentTime.getMillis() + 300000L);
            samlAssertion.setID(createID());
            samlAssertion.setVersion(SAMLVersion.VERSION_20);
            samlAssertion.setIssuer(getIssuer());
            samlAssertion.setIssueInstant(currentTime);
            final Subject subject = new SubjectBuilder().buildObject();
            final NameID nameId = new NameIDBuilder().buildObject();
            nameId.setValue(Main.username);
            nameId.setFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress");
            subject.setNameID(nameId);
            final SubjectConfirmation subjectConfirmation = new SubjectConfirmationBuilder().buildObject();
            subjectConfirmation.setMethod("urn:oasis:names:tc:SAML:2.0:cm:bearer");
            final SubjectConfirmationData scData = new SubjectConfirmationDataBuilder().buildObject();
            scData.setRecipient(Main.recipient);
            scData.setNotOnOrAfter(notOnOrAfter);
            scData.setInResponseTo(Main.id);
            subjectConfirmation.setSubjectConfirmationData(scData);
            subject.getSubjectConfirmations().add(subjectConfirmation);
            samlAssertion.setSubject(subject);
            final AuthnStatement authStmt = new AuthnStatementBuilder().buildObject();
            authStmt.setAuthnInstant(new DateTime());
            final AuthnContext authContext = new AuthnContextBuilder().buildObject();
            final AuthnContextClassRef authCtxClassRef = new AuthnContextClassRefBuilder().buildObject();
            authCtxClassRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
            authContext.setAuthnContextClassRef(authCtxClassRef);
            authStmt.setAuthnContext(authContext);
            samlAssertion.getAuthnStatements().add(authStmt);
            if (Main.claims != null) {
                samlAssertion.getAttributeStatements().add(buildAttributeStatement(Main.claims));
            }
            final AudienceRestriction audienceRestriction = new AudienceRestrictionBuilder().buildObject();
            if (Main.requestedAudiences != null) {
                for (final String requestedAudience : Main.requestedAudiences) {
                    final Audience audience = new AudienceBuilder().buildObject();
                    audience.setAudienceURI(requestedAudience);
                    audienceRestriction.getAudiences().add(audience);
                }
            }
            final Conditions conditions = new ConditionsBuilder().buildObject();
            conditions.setNotBefore(currentTime);
            conditions.setNotOnOrAfter(notOnOrAfter);
            conditions.getAudienceRestrictions().add(audienceRestriction);
            samlAssertion.setConditions(conditions);
            if (Main.doAssertionSigning) {
                setSignature(samlAssertion, "http://www.w3.org/2000/09/xmldsig#rsa-sha1", getCredential());
                System.out.println("HELLO");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return samlAssertion;
    }
    
    private static AttributeStatement buildAttributeStatement(final Map<String, String> claims) {
        AttributeStatement attStmt = null;
        if (claims != null) {
            attStmt = new AttributeStatementBuilder().buildObject();
            final Iterator<String> ite = claims.keySet().iterator();
            for (int i = 0; i < claims.size(); ++i) {
                final Attribute attrib = new AttributeBuilder().buildObject();
                final String claimUri = ite.next();
                attrib.setName(claimUri);
                final XSStringBuilder stringBuilder = (XSStringBuilder)Configuration.getBuilderFactory().getBuilder(XSString.TYPE_NAME);
                final XSString stringValue = (XSString)stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
                stringValue.setValue((String)claims.get(claimUri));
                attrib.getAttributeValues().add(stringValue);
                attStmt.getAttributes().add(attrib);
            }
        }
        return attStmt;
    }
    
    private static String createID() {
        final byte[] bytes = new byte[20];
        Main.random.nextBytes(bytes);
        final char[] chars = new char[40];
        for (int i = 0; i < bytes.length; ++i) {
            final int left = bytes[i] >> 4 & 0xF;
            final int right = bytes[i] & 0xF;
            chars[i * 2] = Main.charMapping[left];
            chars[i * 2 + 1] = Main.charMapping[right];
        }
        return String.valueOf(chars);
    }
    
    public static String marshall(final XMLObject xmlObject) throws Exception {
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
        final MarshallerFactory marshallerFactory = org.opensaml.xml.Configuration.getMarshallerFactory();
        final Marshaller marshaller = marshallerFactory.getMarshaller(xmlObject);
        final Element element = marshaller.marshall(xmlObject);
        final ByteArrayOutputStream byteArrayOutputStrm = new ByteArrayOutputStream();
        final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        final DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
        final LSSerializer writer = impl.createLSSerializer();
        final LSOutput output = impl.createLSOutput();
        output.setByteStream(byteArrayOutputStrm);
        writer.write(element, output);
        return byteArrayOutputStrm.toString();
    }
    
    public static String encode(final String xmlString) throws Exception {
        final Deflater deflater = new Deflater(8, true);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
        deflaterOutputStream.write(xmlString.getBytes());
        deflaterOutputStream.close();
        final String encodedRequestMessage = Base64.encodeBytes(byteArrayOutputStream.toByteArray(), 8);
        return encodedRequestMessage.trim();
    }
    
    public static Issuer getIssuer() {
        final Issuer issuer = new IssuerBuilder().buildObject();
        issuer.setValue(Main.issuer);
        issuer.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
        return issuer;
    }
    
    public static Assertion setSignature(final Assertion assertion, final String signatureAlgorithm, final X509Credential cred) throws Exception {
    	final Signature signature = (Signature)buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
        //THIS CODE BELOW
    	signature.setSigningCredential((Credential)cred);
        signature.setSignatureAlgorithm(signatureAlgorithm);
        signature.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
        final KeyInfo keyInfo = (KeyInfo)buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME);
        final X509Data data = (X509Data)buildXMLObject(X509Data.DEFAULT_ELEMENT_NAME);
        final X509Certificate cert = (X509Certificate)buildXMLObject(X509Certificate.DEFAULT_ELEMENT_NAME);
        //final String value = org.apache.xml.security.utils.Base64.encode(cred.getEntityCertificate().getEncoded());
        final String value = "MIIDWTCCAkGgAwIBAgIEBXHy+TANBgkqhkiG9w0BAQsFADBdMQswCQYDVQQGEwJJ\n" + 
        		"TjELMAkGA1UECBMCTUgxCzAJBgNVBAcTAk5NMQwwCgYDVQQKEwNSSUwxCzAJBgNV\n" + 
        		"BAsTAklUMRkwFwYDVQQDExBwZHdzbzJtMS5yaWwuY29tMB4XDTE5MDkxNjA1NTMy\n" + 
        		"MloXDTQ3MDIwMTA1NTMyMlowXTELMAkGA1UEBhMCSU4xCzAJBgNVBAgTAk1IMQsw\n" + 
        		"CQYDVQQHEwJOTTEMMAoGA1UEChMDUklMMQswCQYDVQQLEwJJVDEZMBcGA1UEAxMQ\n" + 
        		"cGR3c28ybTEucmlsLmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" + 
        		"AKi5qVpL/gigvmrF36aObRjLfcdLla+c0vect33dxPqLN361SGHt4H0DfrShZ80M\n" + 
        		"1qJ8L3bn0cSIq9INYA8mnvda5PPdw8sDEdZnASmAnMz0py8MfDcSjfqjg5RcwJ6Z\n" + 
        		"WaBVmo4aZXYwFoqODVCNfDw3sHCxrVkiC4w6orDgj+QgI9rKmXwzKKg2kyvj1DTZ\n" + 
        		"//LSuIkEDXISumlcdkrsy7bOdYXPsV/O5ph57DH9yCeq1Vq+PXREkSiiulDkwr8D\n" + 
        		"IgbT4m0PzSNnCtyEjyyrIr8b0J2REWs1yAiyEdGPrs+XB2RbSHgI6QVLV/A5ImxW\n" + 
        		"a3SHPkgfetQXbV1Pg+vmudMCAwEAAaMhMB8wHQYDVR0OBBYEFC3imQgM/VC50W+S\n" + 
        		"hgQx0uvNq/2eMA0GCSqGSIb3DQEBCwUAA4IBAQA+8yi1Sa7Zd5F5IiYOjH3cH1RF\n" + 
        		"+vfMP53OMNGyG90lcW//hCx0cyxzDcihtQGThzXLH8f/Eg3OL3gdw9cByCpFx8sZ\n" + 
        		"8l6Oo0pn09wxYwjaTjhNTD9QV5Z6SR3FJm1GLuW2HOprpw75Q1o6GE6eci9f/dH/\n" + 
        		"xecFfMlGR2s5M599ZtgPvrQWeO9YCI6zvF2y74I0TfMe/klovn5F5eQUxSrtGAb6\n" + 
        		"+5ayp9zIflsG05RlmRhTaMhUIG/3MdleUmKWMn2+32vmsFgyZZts+ZR9dRQCaTXT\n" + 
        		"JOQmKvk3di38omJ0zdApCufp5nT1H/G+wlPKU9YEokncEQACSfWWXjv4tzjy";
        cert.setValue(value);
        data.getX509Certificates().add(cert);
        keyInfo.getX509Datas().add(data);
        signature.setKeyInfo(keyInfo);
        assertion.setSignature(signature);
        final List<Signature> signatureList = new ArrayList<Signature>();
        signatureList.add(signature);
        final MarshallerFactory marshallerFactory = org.opensaml.xml.Configuration.getMarshallerFactory();
        final Marshaller marshaller = marshallerFactory.getMarshaller((XMLObject)assertion);
        marshaller.marshall((XMLObject)assertion);
        Init.init();
        System.out.println(signatureList);
        Signer.signObjects((List)signatureList);
        return assertion;
    }
    
    private static XMLObject buildXMLObject(final QName objectQName) throws Exception {
        final XMLObjectBuilder builder = org.opensaml.xml.Configuration.getBuilderFactory().getBuilder(objectQName);
        if (builder == null) {
            throw new Exception("Unable to retrieve builder for object QName " + objectQName);
        }
        return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
    }
    
    private static X509Credential getCredential() throws Exception {
        Main.keyStore = KeyStore.getInstance("JKS");
        final char[] storePass = Main.keyStorePassword.toCharArray();
        final FileInputStream fileInputStream = new FileInputStream(Main.keyStoreFile);
        Main.keyStore.load(fileInputStream, storePass);
        String y = getFileContent(fileInputStream,"UTF-8");
        fileInputStream.close();
        X509Credential x = (X509Credential)new X509CredentialImpl(Main.keyStore, Main.keyStorePassword, Main.alias, Main.privateKeyPassword);
        return x;
        //return (X509Credential)new X509CredentialImpl(Main.keyStore, Main.keyStorePassword, Main.alias, Main.privateKeyPassword);
    }
    
    public static String getFileContent(
    		   FileInputStream fis,
    		   String          encoding ) throws IOException
    		 {
    		   try( BufferedReader br =
    		           new BufferedReader( new InputStreamReader(fis, encoding )))
    		   {
    		      StringBuilder sb = new StringBuilder();
    		      String line;
    		      while(( line = br.readLine()) != null ) {
    		         sb.append( line );
    		         sb.append( '\n' );
    		      }
    		      return sb.toString();
    		   }
    		}
    
    static {
        Main.claims = new HashMap<String, String>();
        Main.keyStoreFile = null;
        Main.keyStorePassword = null;
        Main.alias = null;
        Main.privateKeyPassword = null;
        Main.random = new Random();
        charMapping = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };
    }
}
