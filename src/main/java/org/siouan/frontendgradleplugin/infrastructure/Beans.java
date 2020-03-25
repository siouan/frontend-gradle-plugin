package org.siouan.frontendgradleplugin.infrastructure;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

public final class Beans {

    private static final BeanFactory FACTORY = new BeanFactory();

    private static final Map<Class<?>, Class<?>[]> PARAMETER_TYPES_BY_CLASS = new HashMap<>();

    private static final Map<Class<?>, Object> SINGLETONS = new HashMap<>();

    static {
        registerSingletonBean(new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName()));
    }

    private Beans() {
    }

    public static <T> T getBean(@Nonnull final Class<T> beanClass) throws BeanInstanciationException {
        final Optional<T> instanciatedBean = getInstanciatedBean(beanClass);
        if (instanciatedBean.isPresent()) {
            return instanciatedBean.get();
        } else {
            final T bean = buildBean(beanClass);
            registerSingletonBean(bean);
            return bean;
        }
    }

    private static <T> Optional<T> getInstanciatedBean(@Nonnull final Class<T> beanClass) {
        return Optional.ofNullable((T) SINGLETONS.get(beanClass));
    }

    private static <T> void registerSingletonBean(@Nonnull final T bean) {
        SINGLETONS.put(bean.getClass(), bean);
    }

    private static <T> void registerBeanClass(@Nonnull final Class<T> beanClass) throws NoSuchMethodException {
        PARAMETER_TYPES_BY_CLASS.put(beanClass, beanClass.getConstructor().getParameterTypes());
    }

    private static <T> T buildBean(@Nonnull final Class<T> beanClass) throws BeanInstanciationException {
        if (!PARAMETER_TYPES_BY_CLASS.containsKey(beanClass)) {
            throw new IllegalArgumentException("Unsupported type of bean: " + beanClass.getName());
        }

        final Class<?>[] paramTypes = PARAMETER_TYPES_BY_CLASS.get(beanClass);
        final Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            final Class<?> paramType = paramTypes[i];
            final Optional<?> param = getInstanciatedBean(paramType);
            if (param.isPresent()) {
                params[i] = param;
            } else {
                params[i] = getBean(paramType);
            }
        }

        try {
            return FACTORY.getInstance(beanClass, params);
        } catch (final InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new BeanInstanciationException(e);
        }
    }
}
