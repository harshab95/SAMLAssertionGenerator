// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import javax.xml.namespace.QName;

public class XMLConstants
{
    public static final String XMLTOOLING_CONFIG_NS = "http://www.opensaml.org/xmltooling-config";
    public static final String XMLTOOLING_CONFIG_PREFIX = "xt";
    public static final String XMLTOOLING_DEFAULT_OBJECT_PROVIDER = "DEFAULT";
    public static final String XMLTOOLING_SCHEMA_LOCATION = "/schema/xmltooling-config.xsd";
    public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    public static final String XML_PREFIX = "xml";
    public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
    public static final String XMLNS_PREFIX = "xmlns";
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD_PREFIX = "xs";
    public static final String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSI_PREFIX = "xsi";
    public static final String XMLSIG_NS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String XMLSIG_PREFIX = "ds";
    public static final String XMLENC_NS = "http://www.w3.org/2001/04/xmlenc#";
    public static final String XMLENC_PREFIX = "xenc";
    public static final QName XSI_TYPE_ATTRIB_NAME;
    public static final QName XSI_SCHEMA_LOCATION_ATTRIB_NAME;
    public static final QName XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME;
    public static final QName XSI_NIL_ATTRIB_NAME;
    
    static {
        XSI_TYPE_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");
        XSI_SCHEMA_LOCATION_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "xsi");
        XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", "xsi");
        XSI_NIL_ATTRIB_NAME = new QName("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi");
    }
}
