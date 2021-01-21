package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.util.Objects;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code npm} command. To do so, the {@code script}
 * property must be defined, and custom task may depend on the {@code installNode} task or the {@code installFrontend}
 * task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm
 * tasks.register('mytask', RunNpm) {
 *     dependsOn tasks.named('installFrontend')
 *     command = 'mycommand'
 * }
 * </pre>
 *
 * @since 6.0.0
 */
public class RunNpm extends AbstractRunCommandTask {

    public RunNpm() {
        super();
        final FrontendExtension extension = Objects.requireNonNull(
            getProject().getExtensions().findByType(FrontendExtension.class));
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected String getExecutableType() {
        return ExecutableType.NPM;
    }
}
