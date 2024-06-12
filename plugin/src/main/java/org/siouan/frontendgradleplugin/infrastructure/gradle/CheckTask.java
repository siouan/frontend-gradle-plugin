package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.process.ExecOperations;

/**
 * This task checks the project.
 */
public class CheckTask extends AbstractRunCommandTask {

    @Inject
    public CheckTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
    }

    @Input
    @Optional
    public Property<String> getCheckScript() {
        return script;
    }
}
