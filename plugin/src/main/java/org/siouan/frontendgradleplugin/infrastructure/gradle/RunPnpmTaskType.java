package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code pnpm} command. To do so, the {@code script}
 * property must be defined, and custom task may depend on the {@code installNode} task or the {@code installFrontend}
 * task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType
 * tasks.register&lt;RunPnpmTaskType&gt;('mytask') {
 *     dependsOn(tasks.named('installPackageManager'))
 *     args.set('...')
 * }
 * </pre>
 *
 * @since 7.0.0
 */
public abstract class RunPnpmTaskType extends AbstractRunCommandTaskType {

    @Inject
    public RunPnpmTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.PNPM);
    }

    @Input
    @Option(option = "args", description = "Arguments to be appended to the pnpm executable name on the command line.")
    public Property<String> getArgs() {
        return executableArgs;
    }
}
