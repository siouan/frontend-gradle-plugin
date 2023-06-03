package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.io.IOException;
import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.ResolvePackageManager;
import org.siouan.frontendgradleplugin.domain.ResolvePackageManagerCommand;
import org.siouan.frontendgradleplugin.domain.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.infrastructure.bean.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.bean.Beans;
import org.siouan.frontendgradleplugin.infrastructure.bean.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.bean.ZeroOrMultiplePublicConstructorsException;

/**
 * This task resolves the name of the package manager applicable for the current project by parsing the
 * {@code packageManager} property in the {@code package.json} file.
 *
 * @since 7.0.0
 */
public class ResolvePackageManagerTask extends DefaultTask {

    /**
     * Bean registry ID.
     */
    private final String beanRegistryId;

    /**
     * The 'package.json' file.
     */
    private final RegularFileProperty metadataFile;

    /**
     * Path to the Node.js install directory.
     */
    private final Property<File> nodeInstallDirectory;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerNameFile;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerExecutablePathFile;

    @Inject
    public ResolvePackageManagerTask(final ProjectLayout projectLayout, final ObjectFactory objectFactory) {
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.metadataFile = objectFactory.fileProperty();
        this.nodeInstallDirectory = objectFactory.property(File.class);
        this.packageManagerNameFile = objectFactory.fileProperty();
        this.packageManagerExecutablePathFile = objectFactory.fileProperty();
    }

    @InputFile
    public RegularFileProperty getMetadataFile() {
        return metadataFile;
    }

    @Input
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @OutputFile
    public RegularFileProperty getPackageManagerNameFile() {
        return packageManagerNameFile;
    }

    @OutputFile
    public RegularFileProperty getPackageManagerExecutablePathFile() {
        return packageManagerExecutablePathFile;
    }

    @TaskAction
    public void execute()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException,
        UnsupportedPackageManagerException, IOException {
        Beans.getBean(beanRegistryId, TaskLoggerConfigurer.class).initLoggerAdapter(this);

        final Platform platform = Beans.getBean(beanRegistryId, PlatformProvider.class).getPlatform();
        getLogger().debug("Platform: {}", platform);
        Beans
            .getBean(beanRegistryId, ResolvePackageManager.class)
            .execute(ResolvePackageManagerCommand
                .builder()
                .metadataFilePath(metadataFile.getAsFile().get().toPath())
                .nodeInstallDirectoryPath(nodeInstallDirectory.map(File::toPath).get())
                .platform(platform)
                .packageManagerNameFilePath(packageManagerNameFile.getAsFile().get().toPath())
                .packageManagerExecutablePathFilePath(packageManagerExecutablePathFile.getAsFile().get().toPath())
                .build());
    }
}
