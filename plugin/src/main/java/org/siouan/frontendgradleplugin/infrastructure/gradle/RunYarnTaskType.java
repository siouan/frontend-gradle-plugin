package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.options.Option;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code yarn} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend on the {@code installYarn} task or the {@code installFrontend}
 * task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType
 * tasks.register&lt;RunYarnTaskType&gt;('mytask') {
 *     dependsOn(tasks.named('installPackageManager'))
 *     args.set('...')
 * }
 * </pre>
 *
 * @since 6.0.0
 */
public abstract class RunYarnTaskType extends AbstractRunCommandTaskType {

    @Inject
    public RunYarnTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.YARN);
    }

    @Input
    @Option(option = "args", description = "Arguments to be appended to the yarn executable name on the command line.")
    public Property<String> getArgs() {
        return executableArgs;
    }
}
