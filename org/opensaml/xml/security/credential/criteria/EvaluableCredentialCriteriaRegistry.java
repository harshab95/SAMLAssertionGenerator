// 
// Decompiled by Procyon v0.5.36
// 

package org.opensaml.xml.security.credential.criteria;

import org.slf4j.LoggerFactory;
import java.util.Iterator;
import org.opensaml.xml.Configuration;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import org.slf4j.Logger;
import java.lang.reflect.InvocationTargetException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.Criteria;
import java.util.Map;

public final class EvaluableCredentialCriteriaRegistry
{
    public static final String DEFAULT_MAPPINGS_FILE = "/credential-criteria-registry.properties";
    private static Map<Class<? extends Criteria>, Class<? extends EvaluableCredentialCriteria>> registry;
    private static boolean initialized;
    
    static {
        init();
    }
    
    private EvaluableCredentialCriteriaRegistry() {
    }
    
    public static EvaluableCredentialCriteria getEvaluator(final Criteria criteria) throws SecurityException {
        final Logger log = getLogger();
        final Class<? extends EvaluableCredentialCriteria> clazz = lookup(criteria.getClass());
        if (clazz != null) {
            log.debug("Registry located evaluable criteria class {} for criteria class {}", (Object)clazz.getName(), (Object)criteria.getClass().getName());
            try {
                final Constructor<? extends EvaluableCredentialCriteria> constructor = clazz.getConstructor(criteria.getClass());
                return (EvaluableCredentialCriteria)constructor.newInstance(criteria);
            }
            catch (java.lang.SecurityException e) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e);
            }
            catch (NoSuchMethodException e2) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e2);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e2);
            }
            catch (IllegalArgumentException e3) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e3);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e3);
            }
            catch (InstantiationException e4) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e4);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e4);
            }
            catch (IllegalAccessException e5) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e5);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e5);
            }
            catch (InvocationTargetException e6) {
                log.error("Error instantiating new EvaluableCredentialCriteria instance", (Throwable)e6);
                throw new SecurityException("Could not create new EvaluableCredentialCriteria", e6);
            }
        }
        log.debug("Registry could not locate evaluable criteria for criteria class {}", (Object)criteria.getClass().getName());
        return null;
    }
    
    public static synchronized Class<? extends EvaluableCredentialCriteria> lookup(final Class<? extends Criteria> clazz) {
        return EvaluableCredentialCriteriaRegistry.registry.get(clazz);
    }
    
    public static synchronized void register(final Class<? extends Criteria> criteriaClass, final Class<? extends EvaluableCredentialCriteria> evaluableClass) {
        final Logger log = getLogger();
        log.debug("Registering class {} as evaluator for class {}", (Object)evaluableClass.getName(), (Object)criteriaClass.getName());
        EvaluableCredentialCriteriaRegistry.registry.put(criteriaClass, evaluableClass);
    }
    
    public static synchronized void deregister(final Class<? extends Criteria> criteriaClass) {
        final Logger log = getLogger();
        log.debug("Deregistering evaluator for class {}", (Object)criteriaClass.getName());
        EvaluableCredentialCriteriaRegistry.registry.remove(criteriaClass);
    }
    
    public static synchronized void clearRegistry() {
        final Logger log = getLogger();
        log.debug("Clearing evaluable criteria registry");
        EvaluableCredentialCriteriaRegistry.registry.clear();
    }
    
    public static synchronized boolean isInitialized() {
        return EvaluableCredentialCriteriaRegistry.initialized;
    }
    
    public static synchronized void init() {
        if (isInitialized()) {
            return;
        }
        EvaluableCredentialCriteriaRegistry.registry = new HashMap<Class<? extends Criteria>, Class<? extends EvaluableCredentialCriteria>>();
        loadDefaultMappings();
        EvaluableCredentialCriteriaRegistry.initialized = true;
    }
    
    public static synchronized void loadDefaultMappings() {
        final Logger log = getLogger();
        log.debug("Loading default evaluable credential criteria mappings");
        final InputStream inStream = EvaluableCredentialCriteriaRegistry.class.getResourceAsStream("/credential-criteria-registry.properties");
        if (inStream == null) {
            log.error(String.format("Could not open resource stream from default mappings file '%s'", "/credential-criteria-registry.properties"));
            return;
        }
        final Properties defaultMappings = new Properties();
        try {
            defaultMappings.load(inStream);
        }
        catch (IOException e) {
            log.error("Error loading properties file from resource stream", (Throwable)e);
            return;
        }
        loadMappings(defaultMappings);
    }
    
    public static synchronized void loadMappings(final Properties mappings) {
        final Logger log = getLogger();
        for (final Object key : mappings.keySet()) {
            if (!(key instanceof String)) {
                log.error(String.format("Properties key was not an instance of String, was '%s', skipping...", key.getClass().getName()));
            }
            else {
                final String criteriaName = (String)key;
                final String evaluatorName = mappings.getProperty(criteriaName);
                final ClassLoader classLoader = Configuration.class.getClassLoader();
                Class criteriaClass = null;
                try {
                    criteriaClass = classLoader.loadClass(criteriaName);
                }
                catch (ClassNotFoundException e) {
                    log.error(String.format("Could not find criteria class name '%s', skipping registration", criteriaName), (Throwable)e);
                    return;
                }
                Class evaluableClass = null;
                try {
                    evaluableClass = classLoader.loadClass(evaluatorName);
                }
                catch (ClassNotFoundException e2) {
                    log.error(String.format("Could not find evaluator class name '%s', skipping registration", criteriaName), (Throwable)e2);
                    return;
                }
                register(criteriaClass, evaluableClass);
            }
        }
    }
    
    private static Logger getLogger() {
        return LoggerFactory.getLogger((Class)EvaluableCredentialCriteriaRegistry.class);
    }
}
