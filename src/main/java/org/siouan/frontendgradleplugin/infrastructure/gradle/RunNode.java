package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code node} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend either on the {@code installNode} task or on the {@code
 * installFrontend} task, depending on the user need.
 * <p>
 * A typical usage of this task type in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
 * tasks.register('mytask', RunNode) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 *
 * @since 1.2.0
 */
public class RunNode extends AbstractRunCommandTask {

    @Inject
    public RunNode(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        super(projectLayout, objectFactory, execOperations);
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected String getExecutableType() {
        return ExecutableType.NODE;
    }
}
