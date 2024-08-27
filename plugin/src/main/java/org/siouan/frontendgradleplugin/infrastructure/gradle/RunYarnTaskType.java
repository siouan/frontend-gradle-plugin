package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.process.ExecOperations;

/**
 * Task type allowing developers to implement custom task and run a {@code yarn} command. To do so, the {@code script}
 * property must be defined or provided as a command line option, and custom task shall depend on the
 * {@code installYarn} task or the {@code installFrontend} task.
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
public abstract class RunYarnTaskType extends RunYarnTask {

    @Inject
    public RunYarnTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        RunCommandTaskTypes.configure(this, getProject());
    }
}
