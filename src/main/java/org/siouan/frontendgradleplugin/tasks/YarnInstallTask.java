package org.siouan.frontendgradleplugin.tasks;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;
import org.siouan.frontendgradleplugin.core.DistributionInstaller;
import org.siouan.frontendgradleplugin.core.DistributionInstallerException;
import org.siouan.frontendgradleplugin.core.DistributionInstallerSettings;
import org.siouan.frontendgradleplugin.core.DownloaderImpl;
import org.siouan.frontendgradleplugin.core.Utils;
import org.siouan.frontendgradleplugin.core.YarnDistributionUrlResolver;
import org.siouan.frontendgradleplugin.core.archivers.ArchiverFactoryImpl;

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
     *
     * @throws DistributionInstallerException If the installer failed.
     */
    @TaskAction
    public void execute() throws DistributionInstallerException {
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(this, Utils.getSystemOsName(),
            getTemporaryDir().toPath(), new YarnDistributionUrlResolver(yarnVersion.get(), yarnDistributionUrl.getOrNull()),
            new DownloaderImpl(getTemporaryDir().toPath()), null, new ArchiverFactoryImpl(),
            yarnInstallDirectory.getAsFile().map(File::toPath).getOrNull());
        new DistributionInstaller(settings).install();
    }
}
