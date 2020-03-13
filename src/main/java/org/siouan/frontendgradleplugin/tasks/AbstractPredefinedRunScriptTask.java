package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

/**
 * This abstract class exposes the common I/O properties for ready-to-use tasks that run a frontend script.
 */
public abstract class AbstractPredefinedRunScriptTask extends AbstractRunScriptTask {

    AbstractPredefinedRunScriptTask(boolean failOnMissingScriptEnabled) {
        super(failOnMissingScriptEnabled);
    }

    @Input
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Internal
    @Optional
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Internal
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }
}
