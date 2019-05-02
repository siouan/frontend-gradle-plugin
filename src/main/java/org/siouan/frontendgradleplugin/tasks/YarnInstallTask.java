package org.siouan.frontendgradleplugin.tasks;

import java.io.File;
import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.DistributionInstaller;
import org.siouan.frontendgradleplugin.core.DistributionInstallerSettings;
import org.siouan.frontendgradleplugin.core.DistributionPostInstallException;
import org.siouan.frontendgradleplugin.core.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.core.DownloadException;
import org.siouan.frontendgradleplugin.core.InvalidDistributionException;
import org.siouan.frontendgradleplugin.core.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.core.Utils;
import org.siouan.frontendgradleplugin.core.YarnDistributionUrlResolver;
import org.siouan.frontendgradleplugin.core.YarnPostInstallAction;

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
    private final Property<File> yarnInstallDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> yarnDistributionUrl;

    public YarnInstallTask() {
        yarnVersion = getProject().getObjects().property(String.class);
        yarnInstallDirectory = getProject().getObjects().property(File.class);
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
    public Property<File> getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    /**
     * Executes the task: downloads and installs the distribution.
     *
     * @throws IOException If an I/O error occured.
     * @throws InvalidDistributionException If the downloaded distribution is invalid.
     * @throws DistributionUrlResolverException If the URL to download the distribution cannot be downloaded.
     * @throws UnsupportedDistributionArchiveException If the type of the distribution is not supported.
     * @throws DownloadException If a download error occured.
     * @throws DistributionPostInstallException If the post-install action has failed.
     */
    @TaskAction
    public void execute() throws IOException, InvalidDistributionException, DistributionUrlResolverException,
        UnsupportedDistributionArchiveException, DownloadException, DistributionPostInstallException {
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(this, Utils.getSystemOsName(),
            new YarnDistributionUrlResolver(yarnVersion.get(), yarnDistributionUrl.getOrNull()), null,
            yarnInstallDirectory.getOrNull(), new YarnPostInstallAction());
        new DistributionInstaller(settings).install();
    }
}
