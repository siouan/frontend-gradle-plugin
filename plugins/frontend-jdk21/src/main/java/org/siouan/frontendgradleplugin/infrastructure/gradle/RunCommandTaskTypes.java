package org.siouan.frontendgradleplugin.infrastructure.gradle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.provider.Provider;
import org.gradle.api.services.BuildServiceRegistration;

/**
 * This class helps configuring a task type running a Node.js-based executable by registering shared services and
 * binding task type properties to the extension and plugin properties.
 *
 * @since 9.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RunCommandTaskTypes {

    public static void configure(final AbstractRunCommandTask runCommandTask, final Project project) {
        final FrontendExtension frontendExtension = project.getExtensions().getByType(FrontendExtension.class);
		final BuildServiceRegistration<?, ?> beanRegistryBuildServiceRegistration = project
				.getGradle()
				.getSharedServices()
				.getRegistrations()
				.findByName(BeanRegistryBuildService.buildName(project));

		if (beanRegistryBuildServiceRegistration == null) {
			throw new GradleException("Bean registry build service was not registered.");
		}

		@SuppressWarnings("unchecked")
		final Provider<BeanRegistryBuildService> beanRegistryBuildServiceProvider =
				(Provider<BeanRegistryBuildService>) beanRegistryBuildServiceRegistration.getService();

		runCommandTask.beanRegistryBuildService.set(beanRegistryBuildServiceProvider);
		runCommandTask.packageJsonDirectory.set(frontendExtension.getPackageJsonDirectory().getAsFile());
		runCommandTask.nodeInstallDirectory.set(frontendExtension.getNodeInstallDirectory().getAsFile());
		runCommandTask.verboseModeEnabled.set(frontendExtension.getVerboseModeEnabled());
		runCommandTask.usesService(beanRegistryBuildServiceProvider);

		final SystemProviders systemProviders = new SystemProviders(project.getProviders());
		runCommandTask.systemJvmArch.set(systemProviders.getJvmArch());
		runCommandTask.systemOsName.set(systemProviders.getOsName());
    }
}
