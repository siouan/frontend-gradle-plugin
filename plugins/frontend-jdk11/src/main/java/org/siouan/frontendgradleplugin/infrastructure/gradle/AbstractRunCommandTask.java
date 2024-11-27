package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistryException;

/**
 * This abstract class provides the reusable logic to run a command with an executable. Sub-classes must expose inputs
 * and outputs.
 */
public abstract class AbstractRunCommandTask extends DefaultTask {

    protected final ExecOperations execOperations;

    /**
     * Bean registry service provider.
     *
     * @since 8.1.0
     */
    protected final Property<BeanRegistryBuildService> beanRegistryBuildService;

    /**
     * Directory where the 'package.json' file is located, and used as the working directory when the command is
     * executed.
     */
    protected final Property<File> packageJsonDirectory;

    /**
     * Directory where the Node.js distribution is installed, and used to find the executables.
     */
    protected final Property<File> nodeInstallDirectory;

    /**
     * Type of executable to run.
     */
    protected final Property<ExecutableType> executableType;

    /**
     * Arguments to be appended to the executable name on the command line.
     */
    protected final Property<String> executableArgs;

    /**
     * Whether the task should produce log messages for the end-user.
     *
     * @since 8.1.0
     */
    protected final Property<Boolean> verboseModeEnabled;

    /**
     * Architecture of the underlying JVM.
     *
     * @since 8.1.0
     */
    protected final Property<String> systemJvmArch;

    /**
     * System name of the O/S.
     *
     * @since 8.1.0
     */
    protected final Property<String> systemOsName;

    /**
     * Additional environment variables to pass when executing the script.
     *
     * @since 8.1.0
     */
    protected final MapProperty<String, String> environmentVariables;

    AbstractRunCommandTask(final ObjectFactory objectFactory, final ExecOperations execOperations) {
        this.execOperations = execOperations;
        this.beanRegistryBuildService = objectFactory.property(BeanRegistryBuildService.class);
        this.packageJsonDirectory = objectFactory.property(File.class);
        this.nodeInstallDirectory = objectFactory.property(File.class);
        this.executableType = objectFactory.property(ExecutableType.class);
        this.executableArgs = objectFactory.property(String.class);
        this.verboseModeEnabled = objectFactory.property(Boolean.class);
        this.systemJvmArch = objectFactory.property(String.class);
        this.systemOsName = objectFactory.property(String.class);
        this.environmentVariables = objectFactory.mapProperty(String.class, String.class);
    }

    @Internal
    public Property<BeanRegistryBuildService> getBeanRegistryBuildService() {
        return beanRegistryBuildService;
    }

    @Internal
    public Property<ExecutableType> getExecutableType() {
        return executableType;
    }

    @Internal
    public Property<Boolean> getVerboseModeEnabled() {
        return verboseModeEnabled;
    }

    @Internal
    public Property<String> getSystemJvmArch() {
        return systemJvmArch;
    }

    @Internal
    public Property<String> getSystemOsName() {
        return systemOsName;
    }

    @Internal
    @SuppressWarnings("unused")
    public MapProperty<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    @Input
    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    @Input
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Asserts this task is runnable.
     *
     * @throws NonRunnableTaskException When the task is not runnable.
     */
    protected void assertThatTaskIsRunnable() throws NonRunnableTaskException {
        if (!executableArgs.isPresent()) {
            throw new NonRunnableTaskException("No arguments provided");
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

        final BeanRegistry beanRegistry = beanRegistryBuildService.get().getBeanRegistry();
        TaskLoggerInitializer.initAdapter(this, verboseModeEnabled.get(),
            beanRegistry.getBean(GradleLoggerAdapter.class), beanRegistry.getBean(GradleSettings.class));

        final Platform platform = Platform.builder().jvmArch(systemJvmArch.get()).osName(systemOsName.get()).build();
        getLogger().debug("Platform: {}", platform);
        beanRegistry
            .getBean(GradleScriptRunnerAdapter.class)
            .execute(ScriptProperties
                .builder()
                .execOperations(execOperations)
                .packageJsonDirectoryPath(packageJsonDirectory.map(File::toPath).get())
                .executableType(executableType.get())
                .nodeInstallDirectoryPath(nodeInstallDirectory.map(File::toPath).get())
                .executableArgs(executableArgs.get())
                .platform(platform)
                .environmentVariables(environmentVariables.getOrElse(Map.of()))
                .build());
    }
}
