package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

import org.gradle.api.Project;
import org.gradle.api.file.FileTree;

/**
 * Component that downloads and installs a distribution.
 */
public class DistributionInstaller extends AbstractTaskJob {

    /**
     * Directory where the distribution shall be installed.
     */
    private final DistributionInstallerSettings settings;

    public DistributionInstaller(final DistributionInstallerSettings settings) {
        super(settings.getTask());
        this.settings = settings;
    }

    /**
     * Installs a distribution:
     * <ul>
     * <li>Empty the install directory.</li>
     * <li>Resolve the URL to download the distribution.</li>
     * <li>Download the distribution.</li>
     * <li>Validate the downloaded distribution.</li>
     * <li>Explode the distribution archive.</li>
     * <li>Deletes the distribution archive and all unnecessary files.</li>
     * </ul>
     *
     * @throws InvalidDistributionException If the  distribution is invalid.
     * @throws IOException If an I/O occurs when accessing the file system.
     * @throws DistributionUrlResolverException If the URL to download the distribution could not be resolved.
     * @throws DownloadException If the distribution could not be downloaded.
     * @throws UnsupportedDistributionArchiveException If the distribution archive is not supported, and could not be
     * exploded.
     * @throws DistributionPostInstallException If the post-install action has failed.
     */
    public void install()
        throws InvalidDistributionException, IOException, DistributionUrlResolverException, DownloadException,
        UnsupportedDistributionArchiveException, DistributionPostInstallException {
        final Project project = task.getProject();

        checkInstallDirectory();

        // Resolve the URL to download the distribution
        final URL distributionUrl = settings.getUrlResolver().resolve();

        // Download the distribution
        final String distributionUrlAsString = distributionUrl.toString();
        logLifecycle("Downloading distribution at '" + distributionUrlAsString + "'");
        final DownloaderImpl downloader = new DownloaderImpl(task.getTemporaryDir());
        final File distributionFile = new File(settings.getInstallDirectory(),
            distributionUrlAsString.substring(distributionUrlAsString.lastIndexOf('/') + 1));
        downloader.download(distributionUrl, distributionFile);

        final Optional<DistributionValidator> validator = settings.getValidator();
        if (validator.isPresent()) {
            validator.get().validate(distributionUrl, distributionFile);
        }

        // Explode the archive
        logLifecycle("Extracting distribution into '" + distributionFile.getParent() + "'");
        final Function<Object, FileTree> extractFunction;
        if (distributionFile.getName().endsWith(".zip")) {
            extractFunction = project::zipTree;
        } else if (distributionFile.getName().endsWith(".tar.gz")) {
            extractFunction = project::tarTree;
        } else {
            logError("Unsupported type of archive: " + distributionFile.getName());
            throw new UnsupportedDistributionArchiveException();
        }
        project.copy(copySpec -> {
            copySpec.from(extractFunction.apply(distributionFile));
            copySpec.into(distributionFile.getParent());
        });

        // Removes the root directory of exploded content
        final File distributionRootDirectory = new File(distributionFile.getParentFile(),
            Utils.removeExtension(distributionFile.getName()));
        Utils.moveFiles(distributionRootDirectory, distributionFile.getParentFile());
        Files.delete(distributionRootDirectory.toPath());

        logLifecycle("Removing distribution file '" + distributionFile.getAbsolutePath() + "'");
        Files.delete(distributionFile.toPath());

        logLifecycle("Running post-install");
        final Optional<DistributionPostInstallAction> listener = settings.getPostInstallAction();
        if (listener.isPresent()) {
            listener.get().onDistributionInstalled(settings);
        }

        logLifecycle("Distribution installed in '" + distributionFile.getParent() + "'");
    }

    private void checkInstallDirectory() throws IOException {
        final Path installPath = settings.getInstallDirectory().toPath();
        Files.createDirectories(installPath);

        logLifecycle("Removing content in install directory '" + installPath.toString() + "'.");
        Utils.deleteRecursively(installPath, false);
    }
}
