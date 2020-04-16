package org.siouan.frontendgradleplugin.infrastructure;

import javax.annotation.Nonnull;

/**
 * This class consists exclusively of static methods, that will delegate to a singleton bean registry instance. It
 * allows to access a bean registry where dependency injection is not possible.
 *
 * @since 2.0.0
 */
public final class Beans {

    private static final BeanRegistry REGISTRY = new BeanRegistry();

    private Beans() {
    }

    public static <T> T getBean(@Nonnull final Class<T> beanClass)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        return REGISTRY.getBean(beanClass);
    }

    public static <T> void registerBean(@Nonnull final Class<T> beanClass) {
        REGISTRY.registerBean(beanClass);
    }

    public static <T> void registerBean(@Nonnull final T bean) {
        REGISTRY.registerBean(bean);
    }
}
