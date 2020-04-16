package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.util.Objects;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;

/**
 * Task type allowing developers to implement custom task and run a {@code npm} or {@code yarn} command. To do so, the
 * {@code script} property must be defined, and custom task shall depend on the {@code installFrontend} task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
 * tasks.register('mytask', RunNpmYarn) {
 *     dependsOn tasks.named('installFrontend')
 *     command = 'mycommand'
 * }
 * </pre>
 */
public class RunNpmYarn extends AbstractRunScriptTask {

    public RunNpmYarn() {
        super();
        final FrontendExtension extension = Objects.requireNonNull(
            getProject().getExtensions().findByType(FrontendExtension.class));
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
        yarnEnabled.set(extension.getYarnEnabled());
        if (extension.getYarnEnabled().get()) {
            yarnInstallDirectory.set(extension.getYarnInstallDirectory());
        }
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected ExecutableType getExecutableType() {
        return yarnEnabled.get() ? ExecutableType.YARN : ExecutableType.NPM;
    }
}
