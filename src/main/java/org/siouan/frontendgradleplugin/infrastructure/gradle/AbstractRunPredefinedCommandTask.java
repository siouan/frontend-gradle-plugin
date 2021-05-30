package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * This abstract class exposes the common I/O properties for ready-to-use tasks that run a frontend script.
 */
public abstract class AbstractRunPredefinedCommandTask extends AbstractRunCommandTask {

    AbstractRunPredefinedCommandTask(@Nonnull final ProjectLayout projectLayout,
        @Nonnull final ObjectFactory objectFactory, @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Override
    protected String getExecutableType() {
        return yarnEnabled.get() ? ExecutableType.YARN : ExecutableType.NPM;
    }
}
