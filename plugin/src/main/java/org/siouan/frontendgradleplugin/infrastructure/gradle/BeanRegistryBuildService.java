package org.siouan.frontendgradleplugin.infrastructure.gradle;

import lombok.Getter;
import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistry;

/**
 * Build service providing the project bean registry to tasks.
 *
 * @since 8.1.0
 */
@Getter
public abstract class BeanRegistryBuildService implements BuildService<BeanRegistryBuildService.Params> {

    /**
     * The project bean registry.
     */
    private final BeanRegistry beanRegistry;

    public BeanRegistryBuildService() {
        beanRegistry = getParameters().getBeanRegistry().get();
    }

    public interface Params extends BuildServiceParameters {

        Property<BeanRegistry> getBeanRegistry();
    }
}
