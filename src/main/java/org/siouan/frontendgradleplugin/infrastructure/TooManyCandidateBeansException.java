package org.siouan.frontendgradleplugin.infrastructure;

import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import javax.annotation.Nonnull;

/**
 * Exception thrown when multiple beans in the registry are valid candidates for a type of constructor parameter.
 *
 * @since 2.0.0
 */
public class TooManyCandidateBeansException extends BeanRegistryException {

    TooManyCandidateBeansException(@Nonnull final Class<?> beanClass, @Nonnull final Object... beans) {
        super(beanClass, "Multiple beans were found for type '" + beanClass + "': " + String.join(", ",
            Arrays.stream(beans).map(Object::getClass).map(Class::getName).collect(toSet())));
    }
}
