package org.siouan.frontendgradleplugin;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task executes frontend tests.
 */
public class CheckTask extends AbstractPredefinedRunScriptTask {

    /**
     * Default task name.
     */
    public static final String DEFAULT_NAME = "checkFrontend";

    @Input
    @Optional
    public Property<String> getCheckScript() {
        return script;
    }
}
