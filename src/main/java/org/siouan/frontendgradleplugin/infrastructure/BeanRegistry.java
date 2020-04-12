package org.siouan.frontendgradleplugin.infrastructure;

import static java.util.stream.Collectors.toSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class is a simple implementation of Inversion of Control and dependency injection: it provides a minimal set of
 * features to ease bean instanciation and reusability, externalize instanciation flow, for the needs of the plugin
 * only. Its origin is due to the lack of a flexible dependency injection engine in Gradle, that would ease to design
 * plugins with modularity. Bean classes are instanciated when needed using the one and only one public constructor
 * available. The registry handles recursive instanciation of constructor parameters, using reflection. However, the
 * registry does not provide a package-scan feature, e.g. to search for implementations of non-instanciable classes.
 * Some implementations must be registered explicitly using the {@link #registerBean(Class)} method or the {@link
 * #registerBean(Object)} method. The registry handles singleton instances only.
 *
 * @since 2.0.0
 */
public class BeanRegistry {

    /**
     * Map of singleton instances, whose values may be {@code null} when a bean has been registered only, and not
     * retrieved yet.
     */
    private final Map<Class<?>, Object> singletons;

    public BeanRegistry() {
        this.singletons = new HashMap<>();
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
    @Nonnull
    public <T> T getBean(@Nonnull final Class<T> beanClass)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final T existingBean = (T) singletons.get(beanClass);
        if (existingBean != null) {
            return existingBean;
        }

        final Class<? extends T> assignableClass = getAssignableClass(beanClass);
        if (assignableClass != null) {
            return getBean(assignableClass);
        }

        assertBeanClassIsInstanciable(beanClass);
        final T newBean = createInstance(beanClass);
        registerBean(newBean);
        return newBean;
    }

    /**
     * Registers the given bean class. Contrary to the {@link #getBean(Class)} method, this method does not instanciate
     * the bean.
     *
     * @param beanClass Bean class.
     * @param <T> Type of bean.
     */
    public <T> void registerBean(@Nonnull final Class<T> beanClass) {
        assertBeanClassIsInstanciable(beanClass);
        registerBean(beanClass, null);
    }

    /**
     * Registers the given bean instance.
     *
     * @param bean Bean.
     * @param <T> Type of bean.
     */
    public <T> void registerBean(@Nonnull final T bean) {
        registerBean((Class<T>) bean.getClass(), bean);
    }

    /**
     * Registers a bean class, an optionally the singleton instance.
     *
     * @param beanClass Bean class.
     * @param bean Optional singleton instance.
     * @param <T> Type of bean.
     */
    private <T> void registerBean(@Nonnull final Class<T> beanClass, @Nullable final T bean) {
        singletons.put(beanClass, bean);
    }

    /**
     * Asserts the given class is instanciable, i.e. is neither an interface, nor an enumeration, nor an abstract
     * class.
     *
     * @param beanClass Bean class.
     */
    private void assertBeanClassIsInstanciable(@Nonnull final Class<?> beanClass) {
        if (beanClass.isInterface() || beanClass.isEnum() || ((beanClass.getModifiers() & Modifier.ABSTRACT) != 0)) {
            throw new IllegalArgumentException(
                "An interface, an enumeration, or an abstract class can not be registered or instanciated: " + beanClass
                    .getName());
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
    @Nullable
    private <T, C extends T> Class<C> getAssignableClass(@Nonnull final Class<T> beanClass)
        throws TooManyCandidateBeansException {
        final Set<Class<C>> assignableBeanClasses = singletons
            .keySet()
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
    private <T> T createInstance(@Nonnull final Class<T> beanClass)
        throws BeanInstanciationException, ZeroOrMultiplePublicConstructorsException, TooManyCandidateBeansException {
        final Constructor<?>[] constructors = beanClass.getConstructors();
        if (constructors.length != 1) {
            throw new ZeroOrMultiplePublicConstructorsException(beanClass);
        }

        final Class<?>[] parameterTypes = constructors[0].getParameterTypes();
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getBean(parameterTypes[i]);
        }
        try {
            return (T) beanClass.getConstructors()[0].newInstance(parameters);
        } catch (final InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeanInstanciationException(beanClass, e);
        }
    }
}
