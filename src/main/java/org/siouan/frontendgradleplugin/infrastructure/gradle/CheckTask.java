package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task executes frontend tests.
 */
public class CheckTask extends AbstractPredefinedRunScriptTask {

    public CheckTask() {
        super(false);
    }

    @Input
    @Optional
    public Property<String> getCheckScript() {
        return script;
    }
}
