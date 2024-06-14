package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.process.ExecOperations;

/**
 * This task cleans project artifacts.
 */
public class CleanTask extends AbstractRunCommandTask {

    @Inject
    public CleanTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
    }

    @Input
    @Optional
    public Property<String> getCleanScript() {
        return script;
    }
}
