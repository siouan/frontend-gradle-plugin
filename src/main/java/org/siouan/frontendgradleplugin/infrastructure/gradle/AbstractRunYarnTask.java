package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.exception.NonRunnableTaskException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Base class to run a {@code yarn} command.
 *
 * @since 6.0.0
 */
public abstract class AbstractRunYarnTask extends AbstractRunCommandTask {

    AbstractRunYarnTask(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
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
