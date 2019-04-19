package org.siouan.frontendgradleplugin.yarn;

import java.io.File;
import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.job.DistributionInstallJob;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.DownloadException;
import org.siouan.frontendgradleplugin.job.InvalidDistributionException;
import org.siouan.frontendgradleplugin.job.UnsupportedDistributionArchiveException;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends DefaultTask {

    /**
     * Default task name.
     */
    public static final String DEFAULT_NAME = "installYarn";

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    private final Property<Boolean> yarnEnabled;

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
        yarnEnabled = getProject().getObjects().property(Boolean.class);
        yarnVersion = getProject().getObjects().property(String.class);
        yarnInstallDirectory = getProject().getObjects().property(File.class);
        yarnDistributionUrl = getProject().getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Input
    @Optional
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @OutputDirectory
    @Optional
    public Property<File> getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionUrl() {
        return yarnDistributionUrl;
    }

    @TaskAction
    public void execute() throws IOException, InvalidDistributionException, DistributionUrlResolverException,
        UnsupportedDistributionArchiveException, DownloadException {
        if (yarnEnabled.get()) {
            final String version = yarnVersion.getOrNull();
            final String distributionUrl = yarnDistributionUrl.getOrNull();
            final File installDirectory = yarnInstallDirectory.getOrNull();
            new DistributionInstallJob(this, new YarnDistributionUrlResolver(version, distributionUrl), null,
                installDirectory).install();
        }
    }
}
