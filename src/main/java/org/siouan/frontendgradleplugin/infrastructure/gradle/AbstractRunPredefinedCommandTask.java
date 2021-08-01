package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * This abstract class exposes the common I/O properties for ready-to-use tasks that run a frontend script.
 */
public abstract class AbstractRunPredefinedCommandTask extends AbstractRunCommandTask {

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    final Property<Boolean> yarnEnabled;

    AbstractRunPredefinedCommandTask() {
        super();
        yarnEnabled = getProject().getObjects().property(Boolean.class);
    }

    @Input
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Override
    protected String getExecutableType() {
        return yarnEnabled.get() ? ExecutableType.YARN : ExecutableType.NPM;
    }
}
