package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;

/**
 * This task installs Yarn Berry in the current project.
 *
 * @since 6.0.0
 */
public class EnableYarnBerryTask extends AbstractRunYarnTask {

    @Inject
    public EnableYarnBerryTask(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    public Property<String> getYarnBerryEnableScript() {
        return script;
    }
}
