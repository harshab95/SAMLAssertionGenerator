// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.schema.impl;

import org.joda.time.Chronology;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.DateTime;
import org.opensaml.xml.schema.XSDateTime;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.BaseXMLObjectUnmarshaller;

public class XSDateTimeUnmarshaller extends BaseXMLObjectUnmarshaller
{
    @Override
    protected void processElementContent(final XMLObject xmlObject, final String elementContent) {
        final XSDateTime xsDateTime = (XSDateTime)xmlObject;
        xsDateTime.setValue(new DateTime((Object)elementContent).withChronology((Chronology)ISOChronology.getInstanceUTC()));
    }
}
