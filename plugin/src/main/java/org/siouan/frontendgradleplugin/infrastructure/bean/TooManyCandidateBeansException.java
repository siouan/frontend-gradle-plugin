package org.siouan.frontendgradleplugin.infrastructure.bean;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;

/**
 * Exception thrown when multiple beans in the registry are valid candidates for a type of constructor parameter.
 *
 * @since 2.0.0
 */
public class TooManyCandidateBeansException extends BeanRegistryException {

    TooManyCandidateBeansException(final Class<?> beanClass, final Object... beans) {
        super(beanClass, "Multiple beans were found for type '" + beanClass + "': " + String.join(", ",
            Arrays.stream(beans).map(Object::getClass).map(Class::getName).collect(toSet())));
    }
}
