package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;

/**
 * This task installs frontend dependencies (by executing a {@code npm/pnpm/yarn} command). Optionally, the command may
 * be customized to pass other parameter (e.g. {@code npm ci} command).
 */
public class InstallFrontendTask extends AbstractRunCommandTask {

    @Inject
    public InstallFrontendTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
    }

    @Input
    public Property<String> getInstallScript() {
        return script;
    }
}
