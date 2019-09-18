// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml;

import java.io.IOException;
import javax.xml.transform.dom.DOMSource;
import java.lang.reflect.Constructor;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.Validator;
import org.opensaml.xml.validation.ValidatorSuite;
import javax.xml.namespace.QName;
import org.w3c.dom.Attr;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.Marshaller;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.opensaml.xml.parse.XMLParserException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import javax.xml.transform.Source;
import org.xml.sax.SAXException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.slf4j.LoggerFactory;
import javax.xml.validation.Schema;
import org.opensaml.xml.parse.BasicParserPool;
import org.slf4j.Logger;

public class XMLConfigurator
{
    private final Logger log;
    private boolean retainXMLConfiguration;
    private BasicParserPool parserPool;
    private Schema configurationSchema;
    
    public XMLConfigurator() throws ConfigurationException {
        this(false);
    }
    
    @Deprecated
    public XMLConfigurator(final boolean retainXML) throws ConfigurationException {
        this.log = LoggerFactory.getLogger((Class)XMLConfigurator.class);
        this.retainXMLConfiguration = retainXML;
        this.parserPool = new BasicParserPool();
        final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        final Source schemaSource = new StreamSource(XMLConfigurator.class.getResourceAsStream("/schema/xmltooling-config.xsd"));
        try {
            this.configurationSchema = factory.newSchema(schemaSource);
            this.parserPool.setIgnoreComments(true);
            this.parserPool.setIgnoreElementContentWhitespace(true);
            this.parserPool.setSchema(this.configurationSchema);
        }
        catch (SAXException e) {
            throw new ConfigurationException("Unable to read XMLTooling configuration schema", e);
        }
    }
    
    public void load(final File configurationFile) throws ConfigurationException {
        if (configurationFile == null || !configurationFile.canRead()) {
            this.log.error("Unable to read configuration file {}", (Object)configurationFile);
        }
        try {
            if (configurationFile.isDirectory()) {
                final File[] configurations = configurationFile.listFiles();
                for (int i = 0; i < configurations.length; ++i) {
                    this.log.debug("Parsing configuration file {}", (Object)configurations[i].getAbsolutePath());
                    this.load(new FileInputStream(configurations[i]));
                }
            }
            else {
                this.log.debug("Parsing configuration file {}", (Object)configurationFile.getAbsolutePath());
                this.load(new FileInputStream(configurationFile));
            }
        }
        catch (FileNotFoundException ex) {}
    }
    
    public void load(final InputStream configurationStream) throws ConfigurationException {
        try {
            final Document configuration = this.parserPool.parse(configurationStream);
            this.load(configuration);
        }
        catch (XMLParserException e) {
            this.log.error("Invalid configuration file", (Throwable)e);
            throw new ConfigurationException("Unable to create DocumentBuilder", e);
        }
    }
    
    public void load(final Document configuration) throws ConfigurationException {
        this.log.debug("Loading configuration from XML Document");
        this.log.trace("{}", (Object)XMLHelper.nodeToString(configuration.getDocumentElement()));
        this.log.debug("Schema validating configuration Document");
        this.validateConfiguration(configuration);
        this.log.debug("Configuration document validated");
        this.load(configuration.getDocumentElement());
    }
    
    protected void load(final Element configurationRoot) throws ConfigurationException {
        final NodeList objectProviders = configurationRoot.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ObjectProviders");
        if (objectProviders.getLength() > 0) {
            this.log.debug("Preparing to load ObjectProviders");
            this.initializeObjectProviders((Element)objectProviders.item(0));
            this.log.debug("ObjectProviders load complete");
        }
        final NodeList validatorSuitesNodes = configurationRoot.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ValidatorSuites");
        if (validatorSuitesNodes.getLength() > 0) {
            this.log.debug("Preparing to load ValidatorSuites");
            this.initializeValidatorSuites((Element)validatorSuitesNodes.item(0));
            this.log.debug("ValidatorSuites load complete");
        }
        final NodeList idAttributesNodes = configurationRoot.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "IDAttributes");
        if (idAttributesNodes.getLength() > 0) {
            this.log.debug("Preparing to load IDAttributes");
            this.initializeIDAttributes((Element)idAttributesNodes.item(0));
            this.log.debug("IDAttributes load complete");
        }
    }
    
    protected void initializeObjectProviders(final Element objectProviders) throws ConfigurationException {
        final NodeList providerList = objectProviders.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ObjectProvider");
        for (int i = 0; i < providerList.getLength(); ++i) {
            final Element objectProvider = (Element)providerList.item(i);
            final Attr qNameAttrib = objectProvider.getAttributeNodeNS(null, "qualifiedName");
            final QName objectProviderName = XMLHelper.getAttributeValueAsQName(qNameAttrib);
            this.log.debug("Initializing object provider {}", (Object)objectProviderName);
            try {
                Element configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "BuilderClass").item(0);
                final XMLObjectBuilder builder = (XMLObjectBuilder)this.createClassInstance(configuration);
                configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "MarshallingClass").item(0);
                final Marshaller marshaller = (Marshaller)this.createClassInstance(configuration);
                configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "UnmarshallingClass").item(0);
                final Unmarshaller unmarshaller = (Unmarshaller)this.createClassInstance(configuration);
                if (this.retainXMLConfiguration) {
                    Configuration.registerObjectProvider(objectProviderName, builder, marshaller, unmarshaller, objectProvider);
                }
                else {
                    Configuration.registerObjectProvider(objectProviderName, builder, marshaller, unmarshaller);
                }
                this.log.debug("{} intialized and configuration cached", (Object)objectProviderName);
            }
            catch (ConfigurationException e) {
                this.log.error("Error initializing object provier " + objectProvider, (Throwable)e);
                Configuration.deregisterObjectProvider(objectProviderName);
                throw e;
            }
        }
    }
    
    protected void initializeValidatorSuites(final Element validatorSuitesElement) throws ConfigurationException {
        final NodeList validatorSuiteList = validatorSuitesElement.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ValidatorSuite");
        for (int i = 0; i < validatorSuiteList.getLength(); ++i) {
            final Element validatorSuiteElement = (Element)validatorSuiteList.item(i);
            final String validatorSuiteId = validatorSuiteElement.getAttributeNS(null, "id");
            final ValidatorSuite validatorSuite = new ValidatorSuite(validatorSuiteId);
            this.log.debug("Initializing ValidatorSuite {}", (Object)validatorSuiteId);
            this.log.trace(XMLHelper.nodeToString(validatorSuiteElement));
            final NodeList validatorList = validatorSuiteElement.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "Validator");
            for (int j = 0; j < validatorList.getLength(); ++j) {
                final Element validatorElement = (Element)validatorList.item(j);
                final QName validatorQName = XMLHelper.getAttributeValueAsQName(validatorElement.getAttributeNodeNS(null, "qualifiedName"));
                final Validator validator = (Validator)this.createClassInstance(validatorElement);
                validatorSuite.registerValidator(validatorQName, validator);
            }
            this.log.debug("ValidtorSuite {} has been initialized", (Object)validatorSuiteId);
            if (this.retainXMLConfiguration) {
                Configuration.registerValidatorSuite(validatorSuiteId, validatorSuite, validatorSuiteElement);
            }
            else {
                Configuration.registerValidatorSuite(validatorSuiteId, validatorSuite);
            }
        }
    }
    
    protected void initializeIDAttributes(final Element idAttributesElement) throws ConfigurationException {
        final NodeList idAttributeList = idAttributesElement.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "IDAttribute");
        for (int i = 0; i < idAttributeList.getLength(); ++i) {
            final Element idAttributeElement = (Element)idAttributeList.item(i);
            final QName attributeQName = XMLHelper.getElementContentAsQName(idAttributeElement);
            if (attributeQName == null) {
                this.log.debug("IDAttribute element was empty, no registration performed");
            }
            else {
                Configuration.registerIDAttribute(attributeQName);
                this.log.debug("IDAttribute {} has been registered", (Object)attributeQName);
            }
        }
    }
    
    protected Object createClassInstance(final Element configuration) throws ConfigurationException {
        String className = configuration.getAttributeNS(null, "className");
        className = DatatypeHelper.safeTrimOrNullString(className);
        if (className == null) {
            return null;
        }
        try {
            this.log.trace("Creating instance of {}", (Object)className);
            final ClassLoader classLoader = this.getClass().getClassLoader();
            final Class clazz = classLoader.loadClass(className);
            final Constructor constructor = clazz.getConstructor((Class[])new Class[0]);
            return constructor.newInstance(new Object[0]);
        }
        catch (Exception e) {
            this.log.error("Can not create instance of " + className, (Throwable)e);
            throw new ConfigurationException("Can not create instance of " + className, e);
        }
    }
    
    protected void validateConfiguration(final Document configuration) throws ConfigurationException {
        try {
            final javax.xml.validation.Validator schemaValidator = this.configurationSchema.newValidator();
            schemaValidator.validate(new DOMSource(configuration));
        }
        catch (IOException e) {
            final String errorMsg = "Unable to read configuration file DOM";
            this.log.error(errorMsg, (Throwable)e);
            throw new ConfigurationException(errorMsg, e);
        }
        catch (SAXException e2) {
            final String errorMsg = "Configuration file does not validate against schema";
            this.log.error(errorMsg, (Throwable)e2);
            throw new ConfigurationException(errorMsg, e2);
        }
    }
}
