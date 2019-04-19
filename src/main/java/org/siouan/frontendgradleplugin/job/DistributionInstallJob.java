package org.siouan.frontendgradleplugin.job;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.function.Function;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;
import org.siouan.frontendgradleplugin.DownloaderImpl;
import org.siouan.frontendgradleplugin.DownloadException;
import org.siouan.frontendgradleplugin.Utils;

/**
 * Job that downloads and installs a distribution.
 */
public class DistributionInstallJob extends AbstractJob {

    /**
     * Directory where the distribution shall be installed.
     */
    private final File installDirectory;

    /**
     * Resolver of the distribution download URL.
     */
    private final DistributionUrlResolver urlResolver;

    /**
     * Validator of the distribution.
     */
    private final DistributionValidator validator;

    public DistributionInstallJob(final Task task, final DistributionUrlResolver urlResolver,
        final DistributionValidator validator, final File installDirectory) {
        super(task);
        this.urlResolver = urlResolver;
        this.validator = validator;
        this.installDirectory = installDirectory;
    }

    public void install()
        throws InvalidDistributionException, IOException, DistributionUrlResolverException, DownloadException,
        UnsupportedDistributionArchiveException {
        final Project project = task.getProject();

        checkInstallDirectory();

        // Resolve the URL to download the distribution
        final URL distributionUrl = urlResolver.resolve();

        // Download the distribution
        final String distributionUrlAsString = distributionUrl.toString();
        logLifecycle("Downloading distribution at '" + distributionUrlAsString + "'");
        final DownloaderImpl downloader = new DownloaderImpl(task.getTemporaryDir());
        final File distributionFile = new File(installDirectory,
            distributionUrlAsString.substring(distributionUrlAsString.lastIndexOf('/') + 1));
        downloader.download(distributionUrl, distributionFile);

        if (validator != null) {
            validator.validate(distributionUrl, distributionFile);
        }

        // Explode the archive
        logLifecycle("Extracting distribution into '" + distributionFile.getParent() + "'");
        final Function<Object, FileTree> extractFunction;
        if (distributionFile.getName().endsWith(".zip")) {
            extractFunction = project::zipTree;
        } else if (distributionFile.getName().endsWith(".tar.gz") || distributionFile.getName().endsWith(".gzip")
            || distributionFile.getName().endsWith(".tar.Z")) {
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

        logLifecycle("Distribution installed in '" + distributionFile.getParent() + "'");
    }

    private void checkInstallDirectory() throws IOException {
        Files.createDirectories(installDirectory.toPath());

        logLifecycle("Removing content in install directory '" + installDirectory.getAbsolutePath() + "'.");
        Utils.deleteRecursively(installDirectory.toPath(), false);
    }
}
