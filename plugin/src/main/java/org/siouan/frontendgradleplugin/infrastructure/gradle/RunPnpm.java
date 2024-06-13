package org.siouan.frontendgradleplugin.infrastructure.gradle;

import javax.inject.Inject;

import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code pnpm} command. To do so, the {@code script}
 * property must be defined, and custom task may depend on the {@code installNode} task or the {@code installFrontend}
 * task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm
 * tasks.register&lt;RunPnpm&gt;('mytask') {
 *     dependsOn(tasks.named('installFrontend'))
 *     script.set('...')
 * }
 * </pre>
 *
 * @since 7.0.0
 */
public abstract class RunPnpm extends AbstractRunCommandTaskType {

    @Inject
    public RunPnpm(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        super(objectFactory, execOperations);
        executableType.set(ExecutableType.PNPM);
    }

    @Input
    public Property<String> getScript() {
        return script;
    }
}
