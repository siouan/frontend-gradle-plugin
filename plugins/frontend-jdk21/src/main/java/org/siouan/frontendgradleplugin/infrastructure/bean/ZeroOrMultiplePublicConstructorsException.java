package org.siouan.frontendgradleplugin.infrastructure.bean;

/**
 * Exception thrown when a bean has zero or multiple public constructors. Such bean can not be instanciated because the
 * registry does not know how to select the relevant constructor.
 *
 * @since 2.0.0
 */
public class ZeroOrMultiplePublicConstructorsException extends BeanRegistryException {

    ZeroOrMultiplePublicConstructorsException(final Class<?> beanClass) {
        super(beanClass, "Class '" + beanClass.getName() + "' must declare a single public constructor.");
    }
}
