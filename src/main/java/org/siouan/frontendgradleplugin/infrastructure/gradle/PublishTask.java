package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.siouan.frontendgradleplugin.infrastructure.gradle.AbstractPredefinedRunScriptTask;

/**
 * This task publishes frontend artifacts.
 *
 * @since 1.4.0
 */
public class PublishTask extends AbstractPredefinedRunScriptTask {

    public PublishTask() {
        super(false);
    }

    @Input
    @Optional
    public Property<String> getPublishScript() {
        return script;
    }
}
