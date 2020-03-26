package org.siouan.frontendgradleplugin.infrastructure;

import javax.annotation.Nonnull;

/**
 * This class consists exclusively of static methods, that will delegate to a singleton bean registry instance. It
 * allows to access a bean registry without knowing how to retrieve it.
 *
 * @since 2.0.0
 */
public final class Beans {

    private static final BeanRegistry REGISTRY = new BeanRegistry();

    private Beans() {
    }

    /**
     * @see BeanRegistry#getBean(Class)
     */
    public static <T> T getBean(@Nonnull final Class<T> beanClass)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        return REGISTRY.getBean(beanClass);
    }

    /**
     * @see BeanRegistry#registerBean(Class)
     */
    public static <T> void registerBean(@Nonnull final Class<T> beanClass) {
        REGISTRY.registerBean(beanClass);
    }

    /**
     * @see BeanRegistry#registerBean(Object)
     */
    public static <T> void registerBean(@Nonnull final T bean) {
        REGISTRY.registerBean(bean);
    }
}
