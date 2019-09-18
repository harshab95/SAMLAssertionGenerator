// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security;

import org.opensaml.xml.util.DatatypeHelper;
import java.security.Key;
import org.opensaml.xml.security.credential.Credential;
import java.util.HashMap;
import org.slf4j.LoggerFactory;
import java.security.interfaces.DSAParams;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.NamedKeyInfoGeneratorManager;
import java.util.Map;
import org.slf4j.Logger;

public class BasicSecurityConfiguration implements SecurityConfiguration
{
    public static final String KEYINFO_RESOLVER_DEFAULT_CONFIG = "_KEYINFO_RESOLVER_DEFAULT_";
    private final Logger log;
    private Map<String, String> signatureAlgorithms;
    private String signatureCanonicalization;
    private String signatureReferenceDigestMethod;
    private Integer signatureHMACOutputLength;
    private Map<DataEncryptionIndex, String> dataEncryptionAlgorithms;
    private Map<KeyTransportEncryptionIndex, String> keyTransportEncryptionAlgorithms;
    private String autoGenEncryptionURI;
    private NamedKeyInfoGeneratorManager keyInfoGeneratorManager;
    private Map<String, KeyInfoCredentialResolver> keyInfoCredentialResolvers;
    private Map<Integer, DSAParams> dsaParams;
    
    public BasicSecurityConfiguration() {
        this.log = LoggerFactory.getLogger((Class)BasicSecurityConfiguration.class);
        this.signatureAlgorithms = new HashMap<String, String>();
        this.dataEncryptionAlgorithms = new HashMap<DataEncryptionIndex, String>();
        this.keyTransportEncryptionAlgorithms = new HashMap<KeyTransportEncryptionIndex, String>();
        this.keyInfoCredentialResolvers = new HashMap<String, KeyInfoCredentialResolver>();
        this.dsaParams = new HashMap<Integer, DSAParams>();
    }
    
    public String getSignatureAlgorithmURI(final String jcaAlgorithmName) {
        return this.signatureAlgorithms.get(jcaAlgorithmName);
    }
    
    public String getSignatureAlgorithmURI(final Credential credential) {
        final Key key = SecurityHelper.extractSigningKey(credential);
        if (key == null) {
            this.log.debug("Could not extract signing key from credential, unable to map to algorithm URI");
            return null;
        }
        if (key.getAlgorithm() == null) {
            this.log.debug("Signing key algorithm value was not available, unable to map to algorithm URI");
            return null;
        }
        return this.getSignatureAlgorithmURI(key.getAlgorithm());
    }
    
    public void registerSignatureAlgorithmURI(final String jcaAlgorithmName, final String algorithmURI) {
        this.signatureAlgorithms.put(jcaAlgorithmName, algorithmURI);
    }
    
    public void deregisterSignatureAlgorithmURI(final String jcaAlgorithmName) {
        this.signatureAlgorithms.remove(jcaAlgorithmName);
    }
    
    public String getSignatureCanonicalizationAlgorithm() {
        return this.signatureCanonicalization;
    }
    
    public void setSignatureCanonicalizationAlgorithm(final String algorithmURI) {
        this.signatureCanonicalization = algorithmURI;
    }
    
    public String getSignatureReferenceDigestMethod() {
        return this.signatureReferenceDigestMethod;
    }
    
    public void setSignatureReferenceDigestMethod(final String algorithmURI) {
        this.signatureReferenceDigestMethod = algorithmURI;
    }
    
    public Integer getSignatureHMACOutputLength() {
        return this.signatureHMACOutputLength;
    }
    
    public void setSignatureHMACOutputLength(final Integer length) {
        this.signatureHMACOutputLength = length;
    }
    
    public String getDataEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength) {
        DataEncryptionIndex index = new DataEncryptionIndex(jcaAlgorithmName, keyLength);
        final String algorithmURI = this.dataEncryptionAlgorithms.get(index);
        if (algorithmURI != null) {
            return algorithmURI;
        }
        if (keyLength != null) {
            this.log.debug("No data encryption algorithm mapping available for JCA name + key length, trying JCA name alone");
            index = new DataEncryptionIndex(jcaAlgorithmName, null);
            return this.dataEncryptionAlgorithms.get(index);
        }
        return null;
    }
    
    public String getDataEncryptionAlgorithmURI(final Credential credential) {
        final Key key = SecurityHelper.extractEncryptionKey(credential);
        if (key == null) {
            this.log.debug("Could not extract data encryption key from credential, unable to map to algorithm URI");
            return null;
        }
        if (key.getAlgorithm() == null) {
            this.log.debug("Data encryption key algorithm value was not available, unable to map to algorithm URI");
            return null;
        }
        final Integer length = SecurityHelper.getKeyLength(key);
        return this.getDataEncryptionAlgorithmURI(key.getAlgorithm(), length);
    }
    
    public void registerDataEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength, final String algorithmURI) {
        final DataEncryptionIndex index = new DataEncryptionIndex(jcaAlgorithmName, keyLength);
        this.dataEncryptionAlgorithms.put(index, algorithmURI);
    }
    
    public void deregisterDataEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength) {
        final DataEncryptionIndex index = new DataEncryptionIndex(jcaAlgorithmName, keyLength);
        this.dataEncryptionAlgorithms.remove(index);
    }
    
    public String getKeyTransportEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength, final String wrappedKeyAlgorithm) {
        KeyTransportEncryptionIndex index = new KeyTransportEncryptionIndex(jcaAlgorithmName, keyLength, wrappedKeyAlgorithm);
        String algorithmURI = this.keyTransportEncryptionAlgorithms.get(index);
        if (algorithmURI != null) {
            return algorithmURI;
        }
        if (wrappedKeyAlgorithm != null) {
            this.log.debug("No data encryption algorithm mapping available for JCA name + key length + wrapped algorithm, trying JCA name + key length");
            index = new KeyTransportEncryptionIndex(jcaAlgorithmName, keyLength, null);
            algorithmURI = this.keyTransportEncryptionAlgorithms.get(index);
            if (algorithmURI != null) {
                return algorithmURI;
            }
        }
        if (keyLength != null) {
            this.log.debug("No data encryption algorithm mapping available for JCA name + key length + wrapped algorithm, trying JCA name + wrapped algorithm");
            index = new KeyTransportEncryptionIndex(jcaAlgorithmName, null, wrappedKeyAlgorithm);
            algorithmURI = this.keyTransportEncryptionAlgorithms.get(index);
            if (algorithmURI != null) {
                return algorithmURI;
            }
        }
        this.log.debug("No data encryption algorithm mapping available for JCA name + key length + wrapped algorithm, trying JCA name alone");
        index = new KeyTransportEncryptionIndex(jcaAlgorithmName, null, null);
        return this.keyTransportEncryptionAlgorithms.get(index);
    }
    
    public String getKeyTransportEncryptionAlgorithmURI(final Credential credential, final String wrappedKeyAlgorithm) {
        final Key key = SecurityHelper.extractEncryptionKey(credential);
        if (key == null) {
            this.log.debug("Could not extract key transport encryption key from credential, unable to map to algorithm URI");
            return null;
        }
        if (key.getAlgorithm() == null) {
            this.log.debug("Key transport encryption key algorithm value was not available, unable to map to algorithm URI");
            return null;
        }
        final Integer length = SecurityHelper.getKeyLength(key);
        return this.getKeyTransportEncryptionAlgorithmURI(key.getAlgorithm(), length, wrappedKeyAlgorithm);
    }
    
    public void registerKeyTransportEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength, final String wrappedKeyAlgorithm, final String algorithmURI) {
        final KeyTransportEncryptionIndex index = new KeyTransportEncryptionIndex(jcaAlgorithmName, keyLength, wrappedKeyAlgorithm);
        this.keyTransportEncryptionAlgorithms.put(index, algorithmURI);
    }
    
    public void deregisterKeyTransportEncryptionAlgorithmURI(final String jcaAlgorithmName, final Integer keyLength, final String wrappedKeyAlgorithm) {
        final KeyTransportEncryptionIndex index = new KeyTransportEncryptionIndex(jcaAlgorithmName, keyLength, wrappedKeyAlgorithm);
        this.keyTransportEncryptionAlgorithms.remove(index);
    }
    
    public String getAutoGeneratedDataEncryptionKeyAlgorithmURI() {
        return this.autoGenEncryptionURI;
    }
    
    public void setAutoGeneratedDataEncryptionKeyAlgorithmURI(final String algorithmURI) {
        this.autoGenEncryptionURI = algorithmURI;
    }
    
    public NamedKeyInfoGeneratorManager getKeyInfoGeneratorManager() {
        return this.keyInfoGeneratorManager;
    }
    
    public void setKeyInfoGeneratorManager(final NamedKeyInfoGeneratorManager keyInfoManager) {
        this.keyInfoGeneratorManager = keyInfoManager;
    }
    
    public KeyInfoCredentialResolver getDefaultKeyInfoCredentialResolver() {
        return this.keyInfoCredentialResolvers.get("_KEYINFO_RESOLVER_DEFAULT_");
    }
    
    public void setDefaultKeyInfoCredentialResolver(final KeyInfoCredentialResolver resolver) {
        this.keyInfoCredentialResolvers.put("_KEYINFO_RESOLVER_DEFAULT_", resolver);
    }
    
    public KeyInfoCredentialResolver getKeyInfoCredentialResolver(final String name) {
        return this.keyInfoCredentialResolvers.get(name);
    }
    
    public void registerKeyInfoCredentialResolver(final String name, final KeyInfoCredentialResolver resolver) {
        this.keyInfoCredentialResolvers.put(name, resolver);
    }
    
    public void deregisterKeyInfoCredentialResolver(final String name) {
        this.keyInfoCredentialResolvers.remove(name);
    }
    
    public DSAParams getDSAParams(final int keyLength) {
        return this.dsaParams.get(keyLength);
    }
    
    public void setDSAParams(final int keyLength, final DSAParams params) {
        this.dsaParams.put(keyLength, params);
    }
    
    protected class DataEncryptionIndex
    {
        private String keyAlgorithm;
        private Integer keyLength;
        
        protected DataEncryptionIndex(final String jcaAlgorithmName, final Integer length) {
            if (DatatypeHelper.isEmpty(jcaAlgorithmName)) {
                throw new IllegalArgumentException("JCA Algorithm name may not be null or empty");
            }
            this.keyAlgorithm = DatatypeHelper.safeTrimOrNullString(jcaAlgorithmName);
            this.keyLength = length;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DataEncryptionIndex)) {
                return false;
            }
            final DataEncryptionIndex other = (DataEncryptionIndex)obj;
            if (!this.keyAlgorithm.equals(other.keyAlgorithm)) {
                return false;
            }
            if (this.keyLength == null) {
                return other.keyLength == null;
            }
            return this.keyLength.equals(other.keyLength);
        }
        
        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + this.keyAlgorithm.hashCode();
            if (this.keyLength != null) {
                result = 37 * result + this.keyLength.hashCode();
            }
            return result;
        }
        
        @Override
        public String toString() {
            return String.format("[%s,%s]", this.keyAlgorithm, this.keyLength);
        }
    }
    
    protected class KeyTransportEncryptionIndex
    {
        private String keyAlgorithm;
        private Integer keyLength;
        private String wrappedAlgorithm;
        
        protected KeyTransportEncryptionIndex(final String jcaAlgorithmName, final Integer length, final String wrappedKeyAlgorithm) {
            if (DatatypeHelper.isEmpty(jcaAlgorithmName)) {
                throw new IllegalArgumentException("JCA Algorithm name may not be null or empty");
            }
            this.keyAlgorithm = DatatypeHelper.safeTrimOrNullString(jcaAlgorithmName);
            this.keyLength = length;
            this.wrappedAlgorithm = DatatypeHelper.safeTrimOrNullString(wrappedKeyAlgorithm);
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof KeyTransportEncryptionIndex)) {
                return false;
            }
            final KeyTransportEncryptionIndex other = (KeyTransportEncryptionIndex)obj;
            if (!this.keyAlgorithm.equals(other.keyAlgorithm)) {
                return false;
            }
            if (this.keyLength == null) {
                if (other.keyLength != null) {
                    return false;
                }
            }
            else if (!this.keyLength.equals(other.keyLength)) {
                return false;
            }
            if (this.wrappedAlgorithm == null) {
                return other.wrappedAlgorithm == null;
            }
            return this.wrappedAlgorithm.equals(other.wrappedAlgorithm);
        }
        
        @Override
        public int hashCode() {
            int result = 17;
            result = 37 * result + this.keyAlgorithm.hashCode();
            if (this.keyLength != null) {
                result = 37 * result + this.keyLength.hashCode();
            }
            if (this.wrappedAlgorithm != null) {
                result = 37 * result + this.wrappedAlgorithm.hashCode();
            }
            return result;
        }
        
        @Override
        public String toString() {
            return String.format("[%s,%s,%s]", this.keyAlgorithm, this.keyLength, this.wrappedAlgorithm);
        }
    }
}
