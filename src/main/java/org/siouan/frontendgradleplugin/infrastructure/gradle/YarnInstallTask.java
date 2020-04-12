package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallYarnDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;
import org.siouan.frontendgradleplugin.infrastructure.TooManyCandidateBeansException;
import org.siouan.frontendgradleplugin.infrastructure.ZeroOrMultiplePublicConstructorsException;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends DefaultTask {

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    /**
     * Directory where the distribution shall be installed.
     */
    private final DirectoryProperty yarnInstallDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> yarnDistributionUrl;

    public YarnInstallTask() {
        yarnVersion = getProject().getObjects().property(String.class);
        yarnInstallDirectory = getProject().getObjects().directoryProperty();
        yarnDistributionUrl = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionUrl() {
        return yarnDistributionUrl;
    }

    @OutputDirectory
    @Optional
    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    /**
     * Executes the task: downloads and installs the distribution.
     */
    @TaskAction
    public void execute()
        throws ArchiverException, UnsupportedDistributionArchiveException, UnsupportedPlatformException,
        BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException,
        UnsupportedDistributionIdException, InvalidDistributionUrlException, DistributionValidatorException,
        IOException {
        final URL distributionUrl = yarnDistributionUrl.isPresent() ? new URL(yarnDistributionUrl.get()) : null;
        Beans
            .getBean(InstallYarnDistribution.class)
            .execute(new InstallSettings(Beans.getBean(Platform.class), yarnVersion.get(), distributionUrl,
                getTemporaryDir().toPath(), yarnInstallDirectory.getAsFile().map(File::toPath).get()));
    }
}
