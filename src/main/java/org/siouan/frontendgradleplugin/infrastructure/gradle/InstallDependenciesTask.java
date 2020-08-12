package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * This task installs frontend dependencies (by executing a {@code npm/yarn} command). Optionally, the command may be
 * customized to pass other parameter (e.g. {@code npm ci} command).
 */
public class InstallDependenciesTask extends AbstractRunPredefinedCommandTask {

    @Input
    public Property<String> getInstallScript() {
        return script;
    }
}
