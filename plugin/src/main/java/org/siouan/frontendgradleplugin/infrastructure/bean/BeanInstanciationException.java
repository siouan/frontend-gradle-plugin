package org.siouan.frontendgradleplugin.infrastructure.bean;

/**
 * Exception thrown when a bean instanciation fails.
 *
 * @since 2.0.0
 */
public class BeanInstanciationException extends BeanRegistryException {

    BeanInstanciationException(final Class<?> beanClass, final Throwable cause) {
        super(beanClass, "Cannot create instance of bean '" + beanClass.getName() + '\'', cause);
    }
}
