package org.siouan.frontendgradleplugin.infrastructure.gradle;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * Task provided as a type to let developers implement custom task based on it. The task does not expose Node/Yarn
 * related options to avoid duplicating the plugin configuration. Using this task as a type to register a custom task
 * requires only to define the 'script' attribute, and to make the custom task depends on the 'installFrontend' task.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * tasks.register('mytask', org.siouan.frontendgradleplugin.infrastructure.gradle.RunScriptTask) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 */
public class RunScriptTask extends AbstractRunScriptTask {

    public RunScriptTask() {
        super(true);
        final FrontendExtension extension = getProject().getExtensions().findByType(FrontendExtension.class);
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        loggingLevel.set(extension.getLoggingLevel());
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
}
