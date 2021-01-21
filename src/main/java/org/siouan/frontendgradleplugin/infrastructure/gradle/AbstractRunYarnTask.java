package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.exception.NonRunnableTaskException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Base class to run a {@code yarn} command.
 *
 * @since 6.0.0
 */
public abstract class AbstractRunYarnTask extends AbstractRunCommandTask {

    /**
     * Directory where the 'package.json' file is located.
     */
    final Property<Boolean> yarnEnabled;

    AbstractRunYarnTask() {
        super();
        yarnEnabled = getProject().getObjects().property(Boolean.class);
    }

    @Input
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Override
    protected String getExecutableType() {
        return ExecutableType.YARN;
    }

    @Override
    protected void assertThatTaskIsRunnable() throws NonRunnableTaskException {
        super.assertThatTaskIsRunnable();

        if (!yarnEnabled.getOrElse(false)) {
            throw new NonRunnableTaskException("Yarn is not enabled (see 'yarnEnabled' property).");
        }
    }
}
