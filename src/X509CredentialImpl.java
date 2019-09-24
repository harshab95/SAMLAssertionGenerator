import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.CredentialContextSet;
import org.opensaml.xml.security.credential.UsageType;
import java.security.cert.X509CRL;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import org.opensaml.xml.security.x509.X509Credential;

// 
// Decompiled by Procyon v0.5.36
// 

public class X509CredentialImpl implements X509Credential
{
    private KeyStore keyStore;
    private String keyStorePass;
    private String alias;
    private String privateKeyPassword;
    
    public X509CredentialImpl(final KeyStore keyStore, final String keyStorePass, final String alias, final String privateKeyPassword) {
        this.keyStore = null;
        this.keyStorePass = null;
        this.alias = null;
        this.privateKeyPassword = null;
        this.keyStore = keyStore;
        this.keyStorePass = keyStorePass;
        this.alias = alias;
        this.privateKeyPassword = privateKeyPassword;
    }
    
    public X509Certificate getEntityCertificate() {
        try {
            return (X509Certificate)this.keyStore.getCertificate(this.alias);
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Collection<X509Certificate> getEntityCertificateChain() {
        try {
            Arrays.asList(this.keyStore.getCertificateChain(this.alias));
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public PublicKey getPublicKey() {
        try {
            this.keyStore.getCertificate(this.alias).getPublicKey();
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public PrivateKey getPrivateKey() {
//        try {
//            return (PrivateKey)this.keyStore.getKey(this.alias, this.privateKeyPassword.toCharArray());
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    private static final String PKCS_8_PEM_HEADER = "-----BEGIN PRIVATE KEY-----";
    private static final String PKCS_8_PEM_FOOTER = "-----END PRIVATE KEY-----";

    public PrivateKey getPrivateKey() {
        byte[] keyDataBytes = null;
		try {
			keyDataBytes = Files.readAllBytes(Main.privateKeyPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String keyDataString = new String(keyDataBytes, StandardCharsets.UTF_8);

       
        if (keyDataString.contains(PKCS_8_PEM_HEADER)) {
            // PKCS#8 Base64 PEM encoded file
            keyDataString = keyDataString.replace(PKCS_8_PEM_HEADER, "");
            keyDataString = keyDataString.replace(PKCS_8_PEM_FOOTER, "");
            byte [] barr = Base64.getMimeDecoder().decode(keyDataString);
            //return barr;
            return readPkcs8PrivateKey(barr);
        } 

        // We assume it's a PKCS#8 DER encoded binary file
        try {
			//return readPkcs8PrivateKey(Files.readAllBytes(Paths.get("/Users/macbug/eclipse-workspace/SAML/private-file.pem")));
        	return readPkcs8PrivateKey(Files.readAllBytes(Main.privateKeyPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    private static PrivateKey readPkcs8PrivateKey(byte[] pkcs8Bytes) {
        KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8Bytes);
        try {
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            //throw new IllegalArgumentException("Unexpected key format!", e);
        	
        	e.printStackTrace();
        }
		return null;
        
    }
    
//    public PrivateKey getPrivateKey() {
//        try {
//             //PrivateKey x = (PrivateKey)this.keyStore.getKey(this.alias, this.privateKeyPassword.toCharArray());
//             
//             File f = new File("/Users/macbug/eclipse-workspace/SAML/private-file.pem");
//             FileInputStream fis = new FileInputStream(f);
//             DataInputStream dis = new DataInputStream(fis);
//             byte[] keyBytes = new byte[(int) f.length()];
//             dis.readFully(keyBytes);
//             dis.close();
//
//             PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//             KeyFactory kf = KeyFactory.getInstance("RSA");
//             return kf.generatePrivate(spec);
//             
//             //return x;
//            		 
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    public SecretKey getSecretKey() {
        try {
        	SecretKey x = (SecretKey)this.keyStore.getKey(this.alias, this.keyStorePass.toCharArray());
            return x;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Collection<X509CRL> getCRLs() {
        return null;
    }
    
    public String getEntityId() {
        return null;
    }
    
    public UsageType getUsageType() {
        return null;
    }
    
    public Collection<String> getKeyNames() {
        return null;
    }
    
    public CredentialContextSet getCredentalContextSet() {
        return null;
    }
    
    public Class<? extends Credential> getCredentialType() {
        return null;
    }
}
