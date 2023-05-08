package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.IOException;
import java.nio.file.Paths;
import javax.annotation.Nonnull;
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
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.PlatformProvider;
import org.siouan.frontendgradleplugin.domain.model.ResolvePackageManagerQuery;
import org.siouan.frontendgradleplugin.domain.usecase.ResolvePackageManager;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;

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
    private final Property<String> nodeInstallDirectory;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerNameFile;

    /**
     * File that will contain information about the package manager.
     */
    private final RegularFileProperty packageManagerExecutablePathFile;

    @Inject
    public ResolvePackageManagerTask(@Nonnull final ProjectLayout projectLayout,
        @Nonnull final ObjectFactory objectFactory) {
        this.beanRegistryId = Beans.getBeanRegistryId(projectLayout.getProjectDirectory().toString());
        this.metadataFile = objectFactory.fileProperty();
        this.nodeInstallDirectory = objectFactory.property(String.class);
        this.packageManagerNameFile = objectFactory.fileProperty();
        this.packageManagerExecutablePathFile = objectFactory.fileProperty();
    }

    @InputFile
    public RegularFileProperty getMetadataFile() {
        return metadataFile;
    }

    @Input
    public Property<String> getNodeInstallDirectory() {
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
            .execute(new ResolvePackageManagerQuery(metadataFile.getAsFile().get().toPath(),
                nodeInstallDirectory.map(Paths::get).get(), platform, packageManagerNameFile.getAsFile().get().toPath(),
                packageManagerExecutablePathFile.getAsFile().get().toPath()));
    }
}
