// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.signature;

import org.opensaml.xml.encryption.EncryptedKey;
import org.opensaml.xml.encryption.AgreementMethod;
import org.opensaml.xml.XMLObject;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface KeyInfoType extends ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "KeyInfoType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfoType", "ds");
    public static final String ID_ATTRIB_NAME = "Id";
    
    String getID();
    
    void setID(final String p0);
    
    List<XMLObject> getXMLObjects();
    
    List<XMLObject> getXMLObjects(final QName p0);
    
    List<KeyName> getKeyNames();
    
    List<KeyValue> getKeyValues();
    
    List<RetrievalMethod> getRetrievalMethods();
    
    List<X509Data> getX509Datas();
    
    List<PGPData> getPGPDatas();
    
    List<SPKIData> getSPKIDatas();
    
    List<MgmtData> getMgmtDatas();
    
    List<AgreementMethod> getAgreementMethods();
    
    List<EncryptedKey> getEncryptedKeys();
}
