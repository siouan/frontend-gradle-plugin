package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;

/**
 * This task installs a Yarn distribution for the current project.
 *
 * @since 6.0.0
 */
public class InstallYarnTask extends AbstractRunYarnTask {

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    @Inject
    public InstallYarnTask(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
        this.yarnVersion = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @Input
    public Property<String> getYarnInstallScript() {
        return script;
    }
}
