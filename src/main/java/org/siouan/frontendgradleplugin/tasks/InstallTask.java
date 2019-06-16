package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * This task installs frontend environment (by executing {@code npm/yarn install} command). Optionally, the command
 * may be customized to pass other parameter (e.g. {@code npm ci} command).
 */
public class InstallTask extends AbstractPredefinedRunScriptTask {

    private static final String INSTALL_SCRIPT = "install";

    public InstallTask() {
        super();
        script.set(INSTALL_SCRIPT);
    }

    @Input
    @Optional
    public Property<String> getInstallScript() {
        return script;
    }
}
