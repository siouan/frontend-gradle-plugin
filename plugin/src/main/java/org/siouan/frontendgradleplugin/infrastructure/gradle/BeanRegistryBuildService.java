package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.Getter;
import org.gradle.api.Project;
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

    public static final String BEAN_REGISTRY_BUILD_SERVICE_NAME_PREFIX = "fgpBeanRegistryBuildService";

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

    /**
     * Gets the name of the build service instance registered in the given project.
     *
     * @param project Project.
     * @return Name.
     */
    public static String buildName(final Project project) {
        return BEAN_REGISTRY_BUILD_SERVICE_NAME_PREFIX + Base64
            .getEncoder()
            .encodeToString(project
                .getLayout()
                .getProjectDirectory()
                .getAsFile()
                .getAbsolutePath()
                .getBytes(StandardCharsets.UTF_8));
    }
}
