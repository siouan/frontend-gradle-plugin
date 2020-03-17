package org.siouan.frontendgradleplugin.tasks;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.siouan.frontendgradleplugin.FrontendExtension;
import org.siouan.frontendgradleplugin.core.Executor;

/**
 * Task provided as a type to let developers implement custom task based on it. The task does not expose Node related
 * options to avoid duplicating the plugin configuration. Using this task as a type to register a custom task requires
 * only to define the {@code script} attribute, and to make the custom task depends on the {@code installNode} or {@code
 * installFrontend} tasks. Choosing the related parent task will depend on the user needs.
 * <p>
 * A typical usage of this task in a 'build.gradle' file would be:
 * <pre>
 * tasks.register('mytask', org.siouan.frontendgradleplugin.tasks.RunNodeTask) {
 *     dependsOn tasks.named('installFrontend')
 *     script = 'myscript'
 * }
 * </pre>
 *
 * @since 1.2.0
 */
public class RunNodeTask extends AbstractRunScriptTask {

    public RunNodeTask() {
        super(true);
        final FrontendExtension extension = getProject().getExtensions().findByType(FrontendExtension.class);
        packageJsonDirectory.set(extension.getPackageJsonDirectory());
        nodeInstallDirectory.set(extension.getNodeInstallDirectory());
    }

    @Input
    public Property<String> getScript() {
        return script;
    }

    @Override
    protected Executor getExecutionType() {
        return Executor.NODE;
    }
}
