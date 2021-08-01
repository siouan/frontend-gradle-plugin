package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.util.Objects;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * Task type allowing developers to implement custom task and run a {@code yarn} command. To do so, the {@code script}
 * property must be defined, and custom task shall depend on the {@code installYarn} task or the {@code installFrontend}
 * task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn
 * tasks.register('mytask', RunYarn) {
 *     dependsOn tasks.named('installFrontend')
 *     command = 'mycommand'
 * }
 * </pre>
 *
 * @since 6.0.0
 */
public class RunYarn extends AbstractRunYarnTask {

    public RunYarn() {
        super();
        final FrontendExtension extension = Objects.requireNonNull(
            getProject().getExtensions().findByType(FrontendExtension.class));
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
        yarnEnabled.set(extension.getYarnEnabled());
    }

    @Input
    public Property<String> getScript() {
        return script;
    }
}
