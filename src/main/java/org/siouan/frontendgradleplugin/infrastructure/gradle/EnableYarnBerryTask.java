package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * This task installs Yarn Berry in the current project.
 *
 * @since 6.0.0
 */
public class EnableYarnBerryTask extends AbstractRunYarnTask {

    @Input
    public Property<String> getYarnBerryEnableScript() {
        return script;
    }
}
