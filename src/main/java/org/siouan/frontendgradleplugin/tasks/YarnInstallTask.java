package org.siouan.frontendgradleplugin.tasks;

import java.io.File;
import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.DistributionInstallJob;
import org.siouan.frontendgradleplugin.core.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.core.DownloadException;
import org.siouan.frontendgradleplugin.core.InvalidDistributionException;
import org.siouan.frontendgradleplugin.core.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.core.YarnDistributionUrlResolver;

/**
 * Task that downloads and installs a Yarn distribution.
 */
public class YarnInstallTask extends DefaultTask {

    /**
     * Default task name.
     */
    public static final String DEFAULT_NAME = "installYarn";

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

    @TaskAction
    public void execute() throws IOException, InvalidDistributionException, DistributionUrlResolverException,
        UnsupportedDistributionArchiveException, DownloadException {
        new DistributionInstallJob(this,
            new YarnDistributionUrlResolver(yarnVersion.get(), yarnDistributionUrl.getOrNull()), null,
            yarnInstallDirectory.getOrNull()).install();
    }
}
