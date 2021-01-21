package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * This task installs Yarn Classic 1.x distribution globally (by executing a {@code npm} command).
 *
 * @since 6.0.0
 */
public class YarnGlobalInstallTask extends AbstractRunCommandTask {

    @Override
    protected String getExecutableType() {
        return ExecutableType.NPM;
    }

    @Input
    public Property<String> getYarnGlobalInstallScript() {
        return script;
    }
}
