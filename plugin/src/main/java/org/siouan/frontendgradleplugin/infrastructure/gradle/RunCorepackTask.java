package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * This task runs a command with the {@code corepack} executable. The {@code args} property must be defined on the
 * command line.
 * <p>
 * Example:
 * <pre>
 * /project&gt; gradle runCorepack "--args=-v"
 *
 * > Task :runCorepack
 * 0.29.3
 * </pre>
 * <p>
 * This task should not be extended or used to register another task.
 *
 * @since 9.0.0
 */
public class RunCorepackTask extends AbstractRunCommandTask {

    @Inject
    public RunCorepackTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        this.executableType.set(ExecutableType.COREPACK);
    }

    @Input
    @Option(option = "args",
        description = "Arguments to be appended to the corepack executable name on the command line.")
    public Property<String> getArgs() {
        return executableArgs;
    }
}
