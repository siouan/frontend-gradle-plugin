package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.process.ExecOperations;

/**
 * This task assembles frontend artifacts.
 */
public class AssembleTask extends AbstractRunCommandTask {

    @Inject
    public AssembleTask(final ProjectLayout projectLayout, final ObjectFactory objectFactory,
        final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    @Optional
    public Property<String> getAssembleScript() {
        return script;
    }
}
