package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * This task installs Yarn Classic 1.x distribution globally (by executing a {@code npm} command).
 *
 * @since 6.0.0
 */
public class YarnGlobalInstallTask extends AbstractRunCommandTask {

    @Inject
    public YarnGlobalInstallTask(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Override
    protected String getExecutableType() {
        return ExecutableType.NPM;
    }

    @Input
    public Property<String> getYarnGlobalInstallScript() {
        return script;
    }
}
