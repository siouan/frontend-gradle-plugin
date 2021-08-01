package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;

/**
 * This task installs frontend dependencies (by executing a {@code npm/yarn} command). Optionally, the command may be
 * customized to pass other parameter (e.g. {@code npm ci} command).
 */
public class InstallDependenciesTask extends AbstractRunPredefinedCommandTask {

    @Inject
    public InstallDependenciesTask(@Nonnull final ProjectLayout projectLayout,
        @Nonnull final ObjectFactory objectFactory, @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    public Property<String> getInstallScript() {
        return script;
    }
}
