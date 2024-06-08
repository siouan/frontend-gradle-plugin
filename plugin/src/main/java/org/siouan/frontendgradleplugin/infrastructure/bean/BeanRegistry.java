package org.siouan.frontendgradleplugin.infrastructure.bean;

import static java.util.stream.Collectors.toSet;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is a simple implementation of Inversion of Control and dependency injection: it provides a minimal set of
 * features to ease bean instanciation and reusability, externalize instanciation flow, for the needs of the plugin
 * only. Its origin is due to the lack of a flexible dependency injection engine in Gradle, that would ease to design
 * plugins with modularity. Bean classes are instanciated when needed using the one and only one public constructor
 * available. The registry handles recursive instanciation of constructor parameters, using reflection. However, the
 * registry does not provide a package-scan feature, e.g. to search for implementations of non-instanciable classes.
 * Some implementations must be registered explicitly using the {@link #registerBeanClass(Class)} method or the
 * {@link #registerBean(Object)} method. The registry handles singleton instances only.
 *
 * @since 2.0.0
 */
@Slf4j
public class BeanRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 8882478289468756368L;

    /**
     * Set of types available for injection. When a bean is instanciated, it is placed in the map of {@link #singletons}
     * below, for future injections.
     */
    private final Set<Class<?>> registeredBeanTypes;

    /**
     * Map of singleton instances, whose values are never {@code null}. If a bean type is present in the
     * {@link #registeredBeanTypes} set and is not a key in this map, it means the type has not been instanciated yet.
     * If a bean type is present in the map, an instance is available for injection.
     */
    private final Map<Class<?>, Object> singletons;

    /**
     * Builds an initializes the registry with {@link #init()}.
     */
    public BeanRegistry() {
        // Do not use method "ConcurrentHashMap.newKeySet()" since serialization of class KeySetView requires
        // '--add-opens' JVM arg.
        this.registeredBeanTypes = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.singletons = new ConcurrentHashMap<>();
        init();
    }

    public void clear() {
        registeredBeanTypes.clear();
        singletons.clear();
    }

    /**
     * Initializes the registry by removing all registered beans, and registering the registry itself.
     */
    public void init() {
        clear();
        // The registry itself is available for injection.
        registerBean(BeanRegistry.class, this);
    }

    /**
     * Gets an instance of a bean of the given class. If no instance is found, the class is registered and a new
     * instance is created and returned. If no bean with the exact same class is registered, this method looks up for an
     * assignable child class registered.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     * @return Bean.
     * @throws ZeroOrMultiplePublicConstructorsException If the bean class does not contain one and only one public
     * constructor, or if a parameter class in the public constructor does not match the same requirement.
     * @throws TooManyCandidateBeansException If multiple instances of the bean class are available in the registry,
     * generally because these instances are child classes of the bean class.
     * @throws BeanInstanciationException If the bean cannot be instanciated.
     */
    public <T> T getBean(final Class<T> beanClass)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        LOGGER.debug("Requesting: {}", beanClass.getSimpleName());
        return getBean(beanClass, 0);
    }

    /**
     * Gets an instance of a bean of the given class. If no instance is found, the class is registered and a new
     * instance is created and returned. If no bean with the exact same class is registered, this method looks up for an
     * assignable child class registered.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     * @return Bean.
     * @throws ZeroOrMultiplePublicConstructorsException If the bean class does not contain one and only one public
     * constructor, or if a parameter class in the public constructor does not match the same requirement.
     * @throws TooManyCandidateBeansException If multiple instances of the bean class are available in the registry,
     * generally because these instances are child classes of the bean class.
     * @throws BeanInstanciationException If the bean cannot be instanciated.
     */
    private <T> T getBean(final Class<T> beanClass, final int nestedLevel)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final String logEnteringPrefix = "  ".repeat(nestedLevel + 1);
        final T existingBean = (T) singletons.get(beanClass);
        if (existingBean != null) {
            LOGGER.debug("{}< Found: {}", logEnteringPrefix, beanClass.getSimpleName());
            return existingBean;
        }

        final Class<? extends T> assignableClass = getAssignableClass(beanClass);
        if (assignableClass != null) {
            return getBean(assignableClass, nestedLevel);
        }

        LOGGER.debug("{}+ Instantiating: {}", logEnteringPrefix, beanClass.getSimpleName());
        assertBeanClassIsInstanciable(beanClass);
        final T newBean = createInstance(beanClass, nestedLevel);
        registerBean(beanClass, newBean);
        return newBean;
    }

    /**
     * Registers the given bean class. If the class is already registered, this method has no effect. Contrary to the
     * {@link #getBean(Class)} method, this method does not create an instance of the bean.  For security reasons,
     * trying to register a {@link BeanRegistry} assignable class has no effect.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     */
    public <T> void registerBeanClass(final Class<T> beanClass) {
        LOGGER.debug("Registering class: {}", beanClass.getSimpleName());
        if (isBeanClassRegistered(beanClass)) {
            return;
        }
        assertBeanClassIsInstanciable(beanClass);
        registerBean(beanClass, null);
    }

    /**
     * Registers the given bean instances. If an instance of the same class is already registered, this method has no
     * effect. For security reasons, trying to register an instance of a {@link BeanRegistry} has no effect.
     *
     * @param bean Bean.
     * @param <T> Type of bean.
     */
    public <T> void registerBean(final T bean) {
        final Class<T> beanClass = (Class<T>) bean.getClass();
        LOGGER.debug("Registering bean: {}", beanClass.getSimpleName());
        if (isBeanClassRegistered(beanClass)) {
            return;
        }
        registerBean(beanClass, bean);
    }

    /**
     * Registers a bean class, an optionally the singleton instance.
     *
     * @param beanClass Bean class.
     * @param bean Optional singleton instance.
     * @param <T> Type of bean.
     */
    private <T> void registerBean(final Class<T> beanClass, final T bean) {
        registeredBeanTypes.add(beanClass);
        if (bean != null) {
            singletons.put(beanClass, bean);
        }
    }

    /**
     * Whether a bean class is already registered. For security reasons, it is not possible to replace the registry
     * instance in the registry, i.e. this method returns always {@code true} when checking a {@link BeanRegistry}
     * class.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     * @return {@code true} if the bean class is already registered, or is an instance of a bean registry.
     */
    private <T> boolean isBeanClassRegistered(final Class<T> beanClass) {
        return getClass().isAssignableFrom(beanClass) || registeredBeanTypes.contains(beanClass);
    }

    /**
     * Asserts the given class is instanciable, i.e. is neither an interface, nor an enumeration, nor an abstract
     * class.
     *
     * @param beanClass Bean class.
     */
    private void assertBeanClassIsInstanciable(final Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.isEnum() || ((beanClass.getModifiers() & Modifier.ABSTRACT) != 0)) {
            throw new IllegalArgumentException(
                "An interface, an enumeration, or an abstract class can not be registered or instanciated: "
                    + beanClass.getName());
        }
    }

    /**
     * Gets a registered assignable class.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     * @param <C> Type of the assignable class.
     * @return A registered assignable class, or {@code null} if not found.
     * @throws TooManyCandidateBeansException If two or more classes are registered and valid assignable classes.
     */
    private <T, C extends T> Class<C> getAssignableClass(final Class<T> beanClass)
        throws TooManyCandidateBeansException {
        final Set<Class<C>> assignableBeanClasses = registeredBeanTypes
            .stream()
            .filter(clazz -> !clazz.equals(beanClass))
            .filter(beanClass::isAssignableFrom)
            .map(clazz -> (Class<C>) clazz)
            .collect(toSet());
        if (assignableBeanClasses.isEmpty()) {
            return null;
        }
        if (assignableBeanClasses.size() > 1) {
            throw new TooManyCandidateBeansException(beanClass, assignableBeanClasses);
        }

        return assignableBeanClasses.iterator().next();
    }

    /**
     * Create an instance of the given class, by calling the one and single public constructor. If needed, the method
     * instanciates recursively beans needed for the construction.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     * @return Bean instance
     * @throws BeanInstanciationException If instanciation with the constructor fails. The cause gives the exact error.
     * @throws ZeroOrMultiplePublicConstructorsException If zero or multiple public constructors were found.
     * @throws TooManyCandidateBeansException If multiple valid child candidates were found in the registry for a
     * constructor parameter.
     */
    private <T> T createInstance(final Class<T> beanClass, final int nestedLevel)
        throws BeanInstanciationException, ZeroOrMultiplePublicConstructorsException, TooManyCandidateBeansException {
        final Constructor<?>[] constructors = beanClass.getConstructors();
        if (constructors.length != 1) {
            throw new ZeroOrMultiplePublicConstructorsException(beanClass);
        }

        final Class<?>[] parameterTypes = constructors[0].getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getBean(parameterTypes[i], nestedLevel + 1);
        }
        try {
            return (T) beanClass.getConstructors()[0].newInstance(parameters);
        } catch (final InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeanInstanciationException(beanClass, e);
        }
    }

    @Override
    public String toString() {
        return BeanRegistry.class.getName() + " {\n" + "registeredBeanTypes=" + Arrays.toString(
            registeredBeanTypes.stream().map(Class::getSimpleName).toArray()) + ", singletons=" + Arrays.toString(
            singletons.keySet().stream().map(Class::getSimpleName).toArray()) + "}";
    }
}
