package org.siouan.frontendgradleplugin.infrastructure.bean;

import lombok.Getter;

/**
 * Base class of exceptions thrown by the bean registry.
 *
 * @since 2.0.0
 */
@Getter
public abstract class BeanRegistryException extends Exception {

    private final Class<?> beanClass;

    protected BeanRegistryException(final Class<?> beanClass, final String message) {
        this(beanClass, message, null);
    }

    protected BeanRegistryException(final Class<?> beanClass, final String message, final Throwable cause) {
        super(message, cause);
        this.beanClass = beanClass;
    }
}
