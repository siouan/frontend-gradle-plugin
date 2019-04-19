package org.siouan.frontendgradleplugin;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task cleans frontend resources, using a custom script.
 */
public class CleanTask extends AbstractPredefinedRunScriptTask {

    /**
     * Default task name.
     */
    public static final String DEFAULT_NAME = "cleanFrontend";

    @Input
    @Optional
    public Property<String> getCleanScript() {
        return script;
    }
}
