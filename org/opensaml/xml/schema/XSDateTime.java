// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import javax.xml.namespace.QName;
import org.opensaml.xml.validation.ValidatingXMLObject;

public interface XSDateTime extends ValidatingXMLObject
{
    public static final String TYPE_LOCAL_NAME = "dateTime";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime", "xs");
    
    DateTime getValue();
    
    void setValue(final DateTime p0);
    
    DateTimeFormatter getDateTimeFormatter();
    
    void setDateTimeFormatter(final DateTimeFormatter p0);
}
