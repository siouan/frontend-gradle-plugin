package org.siouan.frontendgradleplugin.infrastructure;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base class of exceptions thrown by the bean registry.
 *
 * @since 2.0.0
 */
public abstract class BeanRegistryException extends Exception {

    private final Class<?> beanClass;

    protected BeanRegistryException(@Nonnull final Class<?> beanClass, @Nonnull final String message) {
        this(beanClass, message, null);
    }

    protected BeanRegistryException(@Nonnull final Class<?> beanClass, @Nonnull final String message,
        @Nullable final Throwable cause) {
        super(message, cause);
        this.beanClass = beanClass;
    }

    @Nonnull
    public Class<?> getBeanClass() {
        return beanClass;
    }
}
