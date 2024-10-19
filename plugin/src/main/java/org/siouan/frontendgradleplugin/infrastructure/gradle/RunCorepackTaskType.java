package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.process.ExecOperations;

/**
 * Task type allowing developers to implement custom task and run a {@code node} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend either on the {@code installNode} task or on the
 * {@code installFrontend} task, depending on the user need.
 * <p>
 * A typical usage of this task type in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepackTaskType
 * tasks.register&lt;RunCorepackTaskType&gt;('mytask') {
 *     dependsOn(tasks.named('installCorepack'))
 *     args.set('...')
 * }
 * </pre>
 *
 * @since 7.0.0
 */
public abstract class RunCorepackTaskType extends RunCorepackTask {

    @Inject
    public RunCorepackTaskType(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        RunCommandTaskTypes.configure(this, getProject());
    }
}
