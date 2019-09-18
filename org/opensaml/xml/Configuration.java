// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import org.slf4j.LoggerFactory;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.Marshaller;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ConcurrentHashMap;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.security.SecurityConfiguration;
import java.util.Set;
import org.opensaml.xml.validation.ValidatorSuite;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.MarshallerFactory;
import org.w3c.dom.Element;
import java.util.Map;
import javax.xml.namespace.QName;

public class Configuration
{
    private static QName defaultProvider;
    private static Map<QName, Element> configuredObjectProviders;
    private static Map<String, Element> validatorSuiteConfigurations;
    private static XMLObjectBuilderFactory builderFactory;
    private static MarshallerFactory marshallerFactory;
    private static UnmarshallerFactory unmarshallerFactory;
    private static Map<String, ValidatorSuite> validatorSuites;
    private static Set<QName> idAttributeNames;
    private static SecurityConfiguration globalSecurityConfig;
    private static ParserPool parserPool;
    
    static {
        Configuration.defaultProvider = new QName("http://www.opensaml.org/xmltooling-config", "DEFAULT");
        Configuration.configuredObjectProviders = new ConcurrentHashMap<QName, Element>(0);
        Configuration.validatorSuiteConfigurations = new ConcurrentHashMap<String, Element>(0);
        Configuration.builderFactory = new XMLObjectBuilderFactory();
        Configuration.marshallerFactory = new MarshallerFactory();
        Configuration.unmarshallerFactory = new UnmarshallerFactory();
        Configuration.validatorSuites = new ConcurrentHashMap<String, ValidatorSuite>(5);
        Configuration.idAttributeNames = new CopyOnWriteArraySet<QName>();
        validateJCEProviders();
        registerIDAttribute(new QName("http://www.w3.org/XML/1998/namespace", "id"));
    }
    
    protected Configuration() {
    }
    
    public static ParserPool getParserPool() {
        return Configuration.parserPool;
    }
    
    public static void setParserPool(final ParserPool newParserPool) {
        Configuration.parserPool = newParserPool;
    }
    
    public static QName getDefaultProviderQName() {
        return Configuration.defaultProvider;
    }
    
    public static void registerObjectProvider(final QName providerName, final XMLObjectBuilder builder, final Marshaller marshaller, final Unmarshaller unmarshaller) {
        final Logger log = getLogger();
        log.debug("Registering new builder, marshaller, and unmarshaller for {}", (Object)providerName);
        Configuration.builderFactory.registerBuilder(providerName, builder);
        Configuration.marshallerFactory.registerMarshaller(providerName, marshaller);
        Configuration.unmarshallerFactory.registerUnmarshaller(providerName, unmarshaller);
    }
    
    public static void deregisterObjectProvider(final QName key) {
        final Logger log = getLogger();
        log.debug("Unregistering builder, marshaller, and unmarshaller for {}", (Object)key);
        Configuration.configuredObjectProviders.remove(key);
        Configuration.builderFactory.deregisterBuilder(key);
        Configuration.marshallerFactory.deregisterMarshaller(key);
        Configuration.unmarshallerFactory.deregisterUnmarshaller(key);
    }
    
    public static XMLObjectBuilderFactory getBuilderFactory() {
        return Configuration.builderFactory;
    }
    
    public static MarshallerFactory getMarshallerFactory() {
        return Configuration.marshallerFactory;
    }
    
    public static UnmarshallerFactory getUnmarshallerFactory() {
        return Configuration.unmarshallerFactory;
    }
    
    public static void registerValidatorSuite(final String suiteId, final ValidatorSuite suite) {
        Configuration.validatorSuites.put(suiteId, suite);
    }
    
    public static void deregisterValidatorSuite(final String suiteId) {
        Configuration.validatorSuiteConfigurations.remove(suiteId);
        Configuration.validatorSuites.remove(suiteId);
    }
    
    public static ValidatorSuite getValidatorSuite(final String suiteId) {
        return Configuration.validatorSuites.get(suiteId);
    }
    
    public static void registerIDAttribute(final QName attributeName) {
        if (!Configuration.idAttributeNames.contains(attributeName)) {
            Configuration.idAttributeNames.add(attributeName);
        }
    }
    
    public static void deregisterIDAttribute(final QName attributeName) {
        if (Configuration.idAttributeNames.contains(attributeName)) {
            Configuration.idAttributeNames.remove(attributeName);
        }
    }
    
    public static boolean isIDAttribute(final QName attributeName) {
        return Configuration.idAttributeNames.contains(attributeName);
    }
    
    public static SecurityConfiguration getGlobalSecurityConfiguration() {
        return Configuration.globalSecurityConfig;
    }
    
    public static void setGlobalSecurityConfiguration(final SecurityConfiguration config) {
        Configuration.globalSecurityConfig = config;
    }
    
    public static void validateNonSunJAXP() {
        final Logger log = getLogger();
        final String builderFactoryClass = DocumentBuilderFactory.newInstance().getClass().getName();
        log.debug("VM using JAXP parser {}", (Object)builderFactoryClass);
        if (builderFactoryClass.startsWith("com.sun")) {
            final String errorMsg = "\n\n\nOpenSAML requires an xml parser that supports JAXP 1.3 and DOM3.\nThe JVM is currently configured to use the Sun XML parser, which is known\nto be buggy and can not be used with OpenSAML.  Please endorse a functional\nJAXP library(ies) such as Xerces and Xalan.  For instructions on how to endorse\na new parser see http://java.sun.com/j2se/1.5.0/docs/guide/standards/index.html\n\n\n";
            log.error(errorMsg);
            throw new Error(errorMsg);
        }
    }
    
    public static boolean validateJCEProviders() {
        final Logger log = getLogger();
        boolean ret = true;
        final String errorMsgAESPadding = "The JCE providers currently configured in the JVM do not support\nrequired capabilities for XML Encryption, either the 'AES' cipher algorithm\nor the 'ISO10126Padding' padding scheme\n";
        try {
            Cipher.getInstance("AES/CBC/ISO10126Padding");
        }
        catch (NoSuchAlgorithmException e) {
            log.warn(errorMsgAESPadding);
            ret = false;
        }
        catch (NoSuchPaddingException e2) {
            log.warn(errorMsgAESPadding);
            ret = false;
        }
        return ret;
    }
    
    @Deprecated
    public static void registerObjectProvider(final QName providerName, final XMLObjectBuilder builder, final Marshaller marshaller, final Unmarshaller unmarshaller, final Element configuration) {
        final Logger log = getLogger();
        log.debug("Registering new builder, marshaller, and unmarshaller for {}", (Object)providerName);
        if (configuration != null) {
            Configuration.configuredObjectProviders.put(providerName, configuration);
        }
        Configuration.builderFactory.registerBuilder(providerName, builder);
        Configuration.marshallerFactory.registerMarshaller(providerName, marshaller);
        Configuration.unmarshallerFactory.registerUnmarshaller(providerName, unmarshaller);
    }
    
    @Deprecated
    public static Element getObjectProviderConfiguration(final QName qualifedName) {
        final Element configElement = Configuration.configuredObjectProviders.get(qualifedName);
        if (configElement != null) {
            return (Element)configElement.cloneNode(true);
        }
        return null;
    }
    
    @Deprecated
    public static void registerValidatorSuite(final String suiteId, final ValidatorSuite suite, final Element configuration) {
        if (configuration != null) {
            Configuration.validatorSuiteConfigurations.put(suiteId, configuration);
        }
        Configuration.validatorSuites.put(suiteId, suite);
    }
    
    @Deprecated
    public static Element getValidatorSuiteConfiguration(final String suiteId) {
        final Element configElement = Configuration.validatorSuiteConfigurations.get(suiteId);
        if (configElement != null) {
            return (Element)configElement.cloneNode(true);
        }
        return null;
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)Configuration.class);
    }
}
