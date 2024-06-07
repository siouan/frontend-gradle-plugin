package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.process.ExecOperations;

/**
 * This task assembles project artifacts.
 * <p>
 * NOTE: this task will be renamed {@code AssembleFrontendTask} to conform with naming convention of other tasks.
 */
public class AssembleTask extends AbstractRunCommandTask {

    @Inject
    public AssembleTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
    }

    @Input
    @Optional
    public Property<String> getAssembleScript() {
        return script;
    }
}
