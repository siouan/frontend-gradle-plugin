package org.siouan.frontendgradleplugin.infrastructure.bean;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class consists exclusively of static methods, that will delegate to a singleton bean registry instance. It
 * allows to access a bean registry where dependency injection is not possible.
 *
 * @since 2.0.0
 */
public final class Beans {

    private static final Beans INSTANCE = new Beans();

    private final Map<String, BeanRegistry> beanRegistryByIds;

    private Beans() {
        beanRegistryByIds = new ConcurrentHashMap<>();
    }

    /**
     * Instantiates and initializes a new bean registry. If a registry already exists with the given ID, it is reset.
     *
     * @param registryId Registry ID.
     */
    public static void initBeanRegistry(final String registryId) {
        INSTANCE
            .findBeanRegistryById(registryId)
            .ifPresentOrElse(BeanRegistry::init, () -> INSTANCE.addBeanRegistry(registryId, new BeanRegistry()));
    }

    public static String getBeanRegistryId(final String decodedId) {
        return Base64.getEncoder().encodeToString(decodedId.getBytes(StandardCharsets.UTF_8));
    }

    public static <T> T getBean(final String registryId, final Class<T> beanClass)
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        return INSTANCE.findBeanRegistryByIdOrFail(registryId).getBean(beanClass);
    }

    public static <T> void registerBean(final String registryId, final Class<T> beanClass) {
        INSTANCE.findBeanRegistryByIdOrFail(registryId).registerBean(beanClass);
    }

    public static <T> void registerBean(final String registryId, final T bean) {
        INSTANCE.findBeanRegistryByIdOrFail(registryId).registerBean(bean);
    }

    public void addBeanRegistry(final String registryId, final BeanRegistry beanRegistry) {
        beanRegistryByIds.put(registryId, beanRegistry);
    }

    public BeanRegistry findBeanRegistryByIdOrFail(final String registryId) {
        return findBeanRegistryById(registryId).orElseThrow(
            () -> new IllegalArgumentException("No registry was found with ID " + registryId));
    }

    public Optional<BeanRegistry> findBeanRegistryById(final String registryId) {
        return Optional.ofNullable(beanRegistryByIds.get(registryId));
    }
}
