package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.nio.file.Paths;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;

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
    final Property<String> nodeInstallDirectory;

    /**
     * Type of executable to run.
     */
    final Property<ExecutableType> executableType;

    /**
     * The command to execute.
     */
    final Property<String> script;

    AbstractRunCommandTask(final ProjectLayout projectLayout, final ObjectFactory objectFactory,
        final ExecOperations execOperations) {
        this.execOperations = execOperations;
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.packageJsonDirectory = objectFactory.property(File.class);
        this.nodeInstallDirectory = objectFactory.property(String.class);
        this.executableType = objectFactory.property(ExecutableType.class);
        this.script = objectFactory.property(String.class);
    }

    @Input
    public Property<ExecutableType> getExecutableType() {
        return executableType;
    }

    @Input
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @Input
    public Property<String> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

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
     * @throws BeanRegistryException If a component cannot be instanciated.
     */
    @TaskAction
    public void execute() throws NonRunnableTaskException, BeanRegistryException {
        assertThatTaskIsRunnable();

        Beans.getBean(beanRegistryId, TaskLoggerConfigurer.class).initLoggerAdapter(this);

        final Platform platform = Beans.getBean(beanRegistryId, PlatformProvider.class).getPlatform();
        getLogger().debug("Platform: {}", platform);
        Beans
            .getBean(beanRegistryId, GradleScriptRunnerAdapter.class)
            .execute(ScriptProperties
                .builder()
                .execOperations(execOperations)
                .packageJsonDirectoryPath(packageJsonDirectory.map(File::toPath).get())
                .executableType(executableType.get())
                .nodeInstallDirectoryPath(nodeInstallDirectory.map(Paths::get).get())
                .script(script.get())
                .platform(platform)
                .build());
    }
}
