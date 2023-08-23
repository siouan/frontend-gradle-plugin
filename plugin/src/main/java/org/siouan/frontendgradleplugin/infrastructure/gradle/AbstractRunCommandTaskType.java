package org.siouan.frontendgradleplugin.infrastructure.gradle;

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
        final FrontendExtension frontendExtension;
        try {
            frontendExtension = Beans.getBean(beanRegistryId, FrontendExtension.class);
        } catch (final BeanRegistryException e) {
            throw new GradleException(e.getClass().getName() + ": " + e.getMessage(), e);
        }
        this.packageJsonDirectory.set(frontendExtension.getPackageJsonDirectory().getAsFile());
        this.nodeInstallDirectory.set(frontendExtension.getNodeInstallDirectory().getAsFile());
    }
}
