package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.nio.file.Path;

import org.gradle.api.GradleException;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;

/**
 * This abstract class provides the required bindings for task types, to run a command with an executable. Sub-classes
 * must expose inputs and outputs.
 */
public abstract class AbstractRunCommandTaskType extends AbstractRunCommandTask {

    AbstractRunCommandTaskType(final ProjectLayout projectLayout, final ObjectFactory objectFactory,
        final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
        final TaskContext taskContext;
        final ResolveNodeInstallDirectoryPath resolveNodeInstallDirectoryPath;
        try {
            taskContext = Beans.getBean(beanRegistryId, TaskContext.class);
            resolveNodeInstallDirectoryPath = Beans.getBean(beanRegistryId, ResolveNodeInstallDirectoryPath.class);
        } catch (final BeanRegistryException e) {
            throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
        }
        final FrontendExtension extension = taskContext.getExtension();
        this.packageJsonDirectory.set(extension.getPackageJsonDirectory().getAsFile());
        this.nodeInstallDirectory.set(extension
            .getNodeDistributionProvided()
            .flatMap(nodeDistributionProvided -> resolveNodeInstallDirectoryPath.execute(
                ResolveNodeInstallDirectoryPathCommand
                    .builder()
                    .userPath(extension.getNodeInstallDirectory().getAsFile().map(File::toPath))
                    .nodeDistributionProvided(nodeDistributionProvided)
                    .environmentPath(taskContext.getNodeInstallDirectoryFromEnvironment())
                    .defaultPath(taskContext.getDefaultNodeInstallDirectoryPath())
                    .build()))
            .map(Path::toString));
    }
}
