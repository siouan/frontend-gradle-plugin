package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * This task runs a command with the {@code node} executable. The {@code args} property must be defined on the command
 * line.
 * <p>
 * Example:
 * <pre>
 * /project&gt; gradle runNode "--args=-v"
 *
 * > Task :runNode
 * 20.17.0
 * </pre>
 * <p>
 * This task should not be extended or used to register another task.
 *
 * @since 9.0.0
 */
public class RunNodeTask extends AbstractRunCommandTask {

    @Inject
    public RunNodeTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.NODE);
    }

    @Input
    @Option(option = "args", description = "Arguments to be appended to the node executable name on the command line.")
    public Property<String> getArgs() {
        return executableArgs;
    }
}
