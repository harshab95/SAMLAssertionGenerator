// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.encryption;

import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObject;

public interface XMLEncryptionBuilder<XMLEncryptionType extends XMLObject> extends XMLObjectBuilder<XMLEncryptionType>
{
    XMLEncryptionType buildObject();
}
