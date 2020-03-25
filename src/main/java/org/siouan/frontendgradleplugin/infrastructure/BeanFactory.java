package org.siouan.frontendgradleplugin.infrastructure;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import javax.annotation.Nonnull;

class BeanFactory {

    public <T> T getInstance(@Nonnull final Class<T> beanClass, final Object... params)
        throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return beanClass
            .getConstructor(Arrays.stream(params).map(Object::getClass).toArray(Class<?>[]::new))
            .newInstance(params);
    }
}
