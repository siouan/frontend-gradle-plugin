package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task executes frontend tests.
 */
public class CheckTask extends AbstractPredefinedRunScriptTask {

    @Input
    @Optional
    public Property<String> getCheckScript() {
        return script;
    }
}
