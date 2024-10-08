package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code node} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend either on the {@code installNode} task or on the
 * {@code installFrontend} task, depending on the user need.
 * <p>
 * A typical usage of this task type in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType
 * tasks.register&lt;RunNodeTaskType&gt;('mytask') {
 *     dependsOn(tasks.named('installNode'))
 *     args.set('...')
 * }
 * </pre>
 *
 * @since 1.2.0
 */
public abstract class RunNodeTaskType extends AbstractRunCommandTaskType {

    @Inject
    public RunNodeTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.NODE);
    }

    @Input
    @Option(option = "args", description = "Arguments to be appended to the node executable name on the command line.")
    public Property<String> getArgs() {
        return executableArgs;
    }
}
