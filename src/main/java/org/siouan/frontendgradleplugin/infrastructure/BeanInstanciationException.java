package org.siouan.frontendgradleplugin.infrastructure;

import javax.annotation.Nonnull;

/**
 * Exception thrown when a bean instanciation fails.
 *
 * @since 2.0.0
 */
public class BeanInstanciationException extends BeanRegistryException {

    BeanInstanciationException(@Nonnull final Class<?> beanClass, @Nonnull final Throwable cause) {
        super(beanClass, "Cannot create instance of bean '" + beanClass.getName() + '\'', cause);
    }
}
