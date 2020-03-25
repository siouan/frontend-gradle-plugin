package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task cleans frontend resources, using a custom script.
 */
public class CleanTask extends AbstractPredefinedRunScriptTask {

    public CleanTask() {
        super(false);
    }

    @Input
    @Optional
    public Property<String> getCleanScript() {
        return script;
    }
}
