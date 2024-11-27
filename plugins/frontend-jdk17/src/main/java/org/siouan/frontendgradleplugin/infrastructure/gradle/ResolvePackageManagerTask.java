package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.io.IOException;
import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.InvalidJsonFileException;
import org.siouan.frontendgradleplugin.domain.MalformedPackageManagerSpecification;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.ResolvePackageManager;
import org.siouan.frontendgradleplugin.domain.ResolvePackageManagerCommand;
import org.siouan.frontendgradleplugin.domain.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanRegistry;
import org.siouan.frontendgradleplugin.infrastructure.bean.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.bean.ZeroOrMultiplePublicConstructorsException;

/**
 * This task resolves the name of the package manager applicable for the current project by parsing the
 * {@code packageManager} property in the {@code package.json} file.
 *
 * @since 7.0.0
 */
@CacheableTask
public class ResolvePackageManagerTask extends DefaultTask {

    /**
     * Bean registry service provider.
     *
     * @since 8.1.0
     */
    protected final Property<BeanRegistryBuildService> beanRegistryBuildService;

    /**
     * The 'package.json' file.
     */
    private final RegularFileProperty packageJsonFile;

    /**
     * Path to the Node.js install directory.
     */
    private final Property<File> nodeInstallDirectory;

    /**
     * File that will contain the specification (name and version) of the package manager for the project.
     */
    private final RegularFileProperty packageManagerSpecificationFile;

    /**
     * File that will contain the path to the package manager executable file.
     */
    private final RegularFileProperty packageManagerExecutablePathFile;

    /**
     * Whether the task should produce log messages for the end-user.
     *
     * @since 8.1.0
     */
    private final Property<Boolean> verboseModeEnabled;

    /**
     * Architecture of the underlying JVM.
     *
     * @since 8.1.0
     */
    private final Property<String> systemJvmArch;

    /**
     * System name of the O/S.
     *
     * @since 8.1.0
     */
    private final Property<String> systemOsName;

    @Inject
    public ResolvePackageManagerTask(final ObjectFactory objectFactory) {
        this.beanRegistryBuildService = objectFactory.property(BeanRegistryBuildService.class);
        this.packageJsonFile = objectFactory.fileProperty();
        this.nodeInstallDirectory = objectFactory.property(File.class);
        this.packageManagerSpecificationFile = objectFactory.fileProperty();
        this.packageManagerExecutablePathFile = objectFactory.fileProperty();
        this.verboseModeEnabled = objectFactory.property(Boolean.class);
        this.systemJvmArch = objectFactory.property(String.class);
        this.systemOsName = objectFactory.property(String.class);
    }

    @Internal
    public Property<BeanRegistryBuildService> getBeanRegistryBuildService() {
        return beanRegistryBuildService;
    }

    @InputFile
    @PathSensitive(PathSensitivity.ABSOLUTE)
    @Optional
    public RegularFileProperty getPackageJsonFile() {
        return packageJsonFile;
    }

    @Input
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
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

    @OutputFile
    public RegularFileProperty getPackageManagerSpecificationFile() {
        return packageManagerSpecificationFile;
    }

    @OutputFile
    public RegularFileProperty getPackageManagerExecutablePathFile() {
        return packageManagerExecutablePathFile;
    }

    @TaskAction
    public void execute()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException,
        IOException, InvalidJsonFileException, MalformedPackageManagerSpecification,
        UnsupportedPackageManagerException {
        final BeanRegistry beanRegistry = beanRegistryBuildService.get().getBeanRegistry();
        TaskLoggerInitializer.initAdapter(this, verboseModeEnabled.get(),
            beanRegistry.getBean(GradleLoggerAdapter.class), beanRegistry.getBean(GradleSettings.class));

        final Platform platform = Platform.builder().jvmArch(systemJvmArch.get()).osName(systemOsName.get()).build();
        getLogger().debug("Platform: {}", platform);
        // Though it is not used by the plugin later, the version of the package manager is written in the specification
        // file so as other tasks using this file as an input are re-executed if the package manager is upgraded (same
        // package manager, different version).
        beanRegistry
            .getBean(ResolvePackageManager.class)
            .execute(ResolvePackageManagerCommand
                .builder()
                .packageJsonFilePath(packageJsonFile.getAsFile().map(File::toPath).getOrNull())
                .nodeInstallDirectoryPath(nodeInstallDirectory.map(File::toPath).get())
                .platform(platform)
                .packageManagerSpecificationFilePath(packageManagerSpecificationFile.getAsFile().get().toPath())
                .packageManagerExecutablePathFilePath(packageManagerExecutablePathFile.getAsFile().get().toPath())
                .build());
    }
}
