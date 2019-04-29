package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task cleans frontend resources, using a custom script.
 */
public class CleanTask extends AbstractPredefinedRunScriptTask {

    @Input
    @Optional
    public Property<String> getCleanScript() {
        return script;
    }
}
