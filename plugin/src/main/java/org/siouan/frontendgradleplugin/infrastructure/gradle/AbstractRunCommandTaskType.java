package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Provider;
import org.gradle.api.services.BuildServiceRegistration;
import org.gradle.process.ExecOperations;

/**
 * This abstract class provides the required bindings for task types, to run a command with an executable. Sub-classes
 * must expose inputs and outputs.
 */
public abstract class AbstractRunCommandTaskType extends AbstractRunCommandTask {

    AbstractRunCommandTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        final Project project = getProject();
        final FrontendExtension frontendExtension = project.getExtensions().getByType(FrontendExtension.class);
        final Provider<BeanRegistryBuildService> beanRegistryBuildServiceProvider = (Provider<BeanRegistryBuildService>) project
            .getGradle()
            .getSharedServices()
            .getRegistrations()
            .named(BeanRegistryBuildService.buildName(project))
            .flatMap(BuildServiceRegistration::getService);
        beanRegistryBuildService.set(beanRegistryBuildServiceProvider);
        packageJsonDirectory.set(frontendExtension.getPackageJsonDirectory().getAsFile());
        nodeInstallDirectory.set(frontendExtension.getNodeInstallDirectory().getAsFile());
        verboseModeEnabled.set(frontendExtension.getVerboseModeEnabled());
        final SystemProviders systemProviders = new SystemProviders(project.getProviders());
        systemJvmArch.set(systemProviders.getJvmArch());
        systemOsName.set(systemProviders.getOsName());
    }
}
