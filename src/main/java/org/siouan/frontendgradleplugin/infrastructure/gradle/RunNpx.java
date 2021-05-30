package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;

/**
 * Task type allowing developers to implement custom task and run a {@code npx} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend either on the {@code installNode} task or on the {@code
 * installFrontend} task, depending on the user need. Running a custom task with this type fails if Yarn is enabled.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
 * tasks.register('mytask', RunNpx) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 *
 * @since 1.2.0
 */
public class RunNpx extends AbstractRunCommandTask {

    @Inject
    public RunNpx(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected String getExecutableType() {
        return ExecutableType.NPX;
    }

    @Override
    public void execute() throws BeanRegistryException, ExecutableNotFoundException {
        if (yarnEnabled.get()) {
            throw new UnsupportedOperationException("Running a npx command is not supported when Yarn is enabled"
                + " to avoid mixing dependencies from both package managers. An equivalent command with Yarn may be"
                + " 'yarn create', runnable with the '" + RunNpmYarn.class.getSimpleName() + "' task type.");
        }

        super.execute();
    }
}
