// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.util;

import org.opensaml.xml.SpaceBearing;
import org.opensaml.xml.BaseBearing;
import org.opensaml.xml.LangBearing;
import org.opensaml.xml.AttributeExtensibleXMLObject;
import org.opensaml.xml.IdBearing;
import org.opensaml.xml.XMLObject;

public final class XMLAttributeHelper
{
    private XMLAttributeHelper() {
    }
    
    public static void addXMLId(final XMLObject xmlObject, final String id) {
        if (xmlObject instanceof IdBearing) {
            ((IdBearing)xmlObject).setXMLId(id);
        }
        else {
            if (!(xmlObject instanceof AttributeExtensibleXMLObject)) {
                throw new IllegalArgumentException("Specified object was neither IdBearing nor AttributeExtensible");
            }
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(IdBearing.XML_ID_ATTR_NAME, id);
        }
    }
    
    public static String getXMLId(final XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof IdBearing) {
            value = DatatypeHelper.safeTrimOrNullString(((IdBearing)xmlObject).getXMLId());
            if (value != null) {
                return value;
            }
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = DatatypeHelper.safeTrimOrNullString(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get((Object)IdBearing.XML_ID_ATTR_NAME));
            return value;
        }
        return null;
    }
    
    public static void addXMLLang(final XMLObject xmlObject, final String lang) {
        if (xmlObject instanceof LangBearing) {
            ((LangBearing)xmlObject).setXMLLang(lang);
        }
        else {
            if (!(xmlObject instanceof AttributeExtensibleXMLObject)) {
                throw new IllegalArgumentException("Specified object was neither LangBearing nor AttributeExtensible");
            }
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(LangBearing.XML_LANG_ATTR_NAME, lang);
        }
    }
    
    public static String getXMLLang(final XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof LangBearing) {
            value = DatatypeHelper.safeTrimOrNullString(((LangBearing)xmlObject).getXMLLang());
            if (value != null) {
                return value;
            }
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = DatatypeHelper.safeTrimOrNullString(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get((Object)LangBearing.XML_LANG_ATTR_NAME));
            return value;
        }
        return null;
    }
    
    public static void addXMLBase(final XMLObject xmlObject, final String base) {
        if (xmlObject instanceof BaseBearing) {
            ((BaseBearing)xmlObject).setXMLBase(base);
        }
        else {
            if (!(xmlObject instanceof AttributeExtensibleXMLObject)) {
                throw new IllegalArgumentException("Specified object was neither BaseBearing nor AttributeExtensible");
            }
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(BaseBearing.XML_BASE_ATTR_NAME, base);
        }
    }
    
    public static String getXMLBase(final XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof BaseBearing) {
            value = DatatypeHelper.safeTrimOrNullString(((BaseBearing)xmlObject).getXMLBase());
            if (value != null) {
                return value;
            }
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = DatatypeHelper.safeTrimOrNullString(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get((Object)BaseBearing.XML_BASE_ATTR_NAME));
            return value;
        }
        return null;
    }
    
    public static void addXMLSpace(final XMLObject xmlObject, final SpaceBearing.XMLSpaceEnum space) {
        if (xmlObject instanceof SpaceBearing) {
            ((SpaceBearing)xmlObject).setXMLSpace(space);
        }
        else {
            if (!(xmlObject instanceof AttributeExtensibleXMLObject)) {
                throw new IllegalArgumentException("Specified object was neither SpaceBearing nor AttributeExtensible");
            }
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(SpaceBearing.XML_SPACE_ATTR_NAME, space.toString());
        }
    }
    
    public static SpaceBearing.XMLSpaceEnum getXMLSpace(final XMLObject xmlObject) {
        SpaceBearing.XMLSpaceEnum valueEnum = null;
        if (xmlObject instanceof SpaceBearing) {
            valueEnum = ((SpaceBearing)xmlObject).getXMLSpace();
            if (valueEnum != null) {
                return valueEnum;
            }
        }
        String valueString = null;
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            valueString = DatatypeHelper.safeTrimOrNullString(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get((Object)SpaceBearing.XML_SPACE_ATTR_NAME));
            if (valueString != null) {
                return SpaceBearing.XMLSpaceEnum.parseValue(valueString);
            }
        }
        return null;
    }
}
