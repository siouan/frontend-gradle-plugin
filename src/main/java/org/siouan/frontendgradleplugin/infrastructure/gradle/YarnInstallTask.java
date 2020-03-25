package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;
import java.net.URISyntaxException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionInstallerException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.InstallDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.usecase.InstallDistribution;
import org.siouan.frontendgradleplugin.infrastructure.BeanInstanciationException;
import org.siouan.frontendgradleplugin.infrastructure.Beans;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends DefaultTask {

    final Property<LogLevel> loggingLevel;

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
        loggingLevel = getProject().getObjects().property(LogLevel.class);
        yarnVersion = getProject().getObjects().property(String.class);
        yarnInstallDirectory = getProject().getObjects().directoryProperty();
        yarnDistributionUrl = getProject().getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<LogLevel> getLoggingLevel() {
        return loggingLevel;
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
     *
     * @throws DistributionInstallerException If the installer failed.
     */
    @TaskAction
    public void execute()
        throws DistributionInstallerException, FrontendIOException, DistributionValidatorException, ArchiverException,
        UnsupportedDistributionArchiveException, DistributionUrlResolverException, UnsupportedEntryException,
        SlipAttackException, BeanInstanciationException, URISyntaxException {
        Beans
            .getBean(InstallDistribution.class)
            .execute(new InstallDistributionSettings(yarnVersion.get(), yarnDistributionUrl.getOrNull(),
                Beans.getBean(Platform.class), loggingLevel.get(), getTemporaryDir().toPath(),
                yarnInstallDirectory.getAsFile().map(File::toPath).get()));
    }
}
