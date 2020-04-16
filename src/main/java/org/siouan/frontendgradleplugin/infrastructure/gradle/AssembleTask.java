package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task assembles frontend artifacts.
 */
public class AssembleTask extends AbstractRunPredefinedCommandTask {

    @Input
    @Optional
    public Property<String> getAssembleScript() {
        return script;
    }
}
