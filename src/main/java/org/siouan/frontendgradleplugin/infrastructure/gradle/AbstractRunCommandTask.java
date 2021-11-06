package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import javax.annotation.Nonnull;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.exception.ExecutableNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.NonRunnableTaskException;
import org.siouan.frontendgradleplugin.domain.model.Environment;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.SystemSettingsProvider;
import org.siouan.frontendgradleplugin.infrastructure.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.GradleScriptRunnerAdapter;
import org.siouan.frontendgradleplugin.infrastructure.gradle.adapter.ScriptProperties;

/**
 * This abstract class provides the reusable logic to run a command with an executable. Sub-classes must expose inputs
 * and outputs.
 */
public abstract class AbstractRunCommandTask extends DefaultTask {

    final ExecOperations execOperations;

    /**
     * Bean registry ID.
     *
     * @since 5.2.0
     */
    final String beanRegistryId;

    /**
     * Directory where the 'package.json' file is located.
     */
    final Property<File> packageJsonDirectory;

    /**
     * Directory where the Node distribution is installed.
     */
    final DirectoryProperty nodeInstallDirectory;

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    final Property<Boolean> yarnEnabled;

    /**
     * The command to execute.
     */
    final Property<String> script;

    AbstractRunCommandTask(@Nonnull final ProjectLayout projectLayout, @Nonnull final ObjectFactory objectFactory,
        @Nonnull final ExecOperations execOperations) {
        this.execOperations = execOperations;
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.packageJsonDirectory = objectFactory.property(File.class);
        this.nodeInstallDirectory = objectFactory.directoryProperty();
        this.yarnEnabled = objectFactory.property(Boolean.class);
        this.script = objectFactory.property(String.class);
        final FrontendExtension extension;
        try {
            extension = Beans.getBean(beanRegistryId, FrontendExtension.class);
        } catch (final BeanRegistryException e) {
            throw new GradleException("Frontend plugin's bean registry failed", e);
        }
        this.packageJsonDirectory.set(extension.getPackageJsonDirectory());
        this.nodeInstallDirectory.set(extension.getNodeInstallDirectory());
        this.yarnEnabled.set(extension.getYarnEnabled());
    }

    @Input
    @Optional
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @Internal
    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Internal
    protected abstract String getExecutableType();

    /**
     * Asserts this task is runnable.
     *
     * @throws NonRunnableTaskException When the task is not runnable.
     */
    protected void assertThatTaskIsRunnable() throws NonRunnableTaskException {
        if (!script.isPresent()) {
            throw new NonRunnableTaskException("Missing property 'script'.");
        }
    }

    /**
     * Executes the task. If a command has been provided, it is run with the selected type of executable. Otherwise, the
     * task does nothing.
     *
     * @throws NonRunnableTaskException When the task is not runnable.
     * @throws ExecutableNotFoundException When the executable cannot be found (Node, npx, npm, Yarn).
     * @throws BeanRegistryException If a component cannot be instanciated.
     */
    @TaskAction
    public void execute() throws NonRunnableTaskException, ExecutableNotFoundException, BeanRegistryException {
        assertThatTaskIsRunnable();

        Beans.getBean(beanRegistryId, TaskLoggerConfigurer.class).initLoggerAdapter(this);

        final SystemSettingsProvider systemSettingsProvider = Beans.getBean(beanRegistryId,
            SystemSettingsProvider.class);
        final Platform platform = new Platform(systemSettingsProvider.getSystemJvmArch(),
            systemSettingsProvider.getSystemOsName(), new Environment(systemSettingsProvider.getNodejsHomePath()));
        getLogger().debug("Platform: {}", platform);
        Beans
            .getBean(beanRegistryId, GradleScriptRunnerAdapter.class)
            .execute(
                new ScriptProperties(execOperations, packageJsonDirectory.map(File::toPath).get(), getExecutableType(),
                    nodeInstallDirectory.getAsFile().map(File::toPath).getOrNull(), script.get(), platform));
    }
}
