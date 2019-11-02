package org.siouan.frontendgradleplugin.core;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.siouan.frontendgradleplugin.core.archivers.ArchiverException;
import org.siouan.frontendgradleplugin.core.archivers.SlipAttackException;
import org.siouan.frontendgradleplugin.core.archivers.UnsupportedEntryException;

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
     * <li>Delete the distribution archive and all unnecessary files.</li>
     * </ul>
     *
     * @throws DistributionInstallerException If the installer failed.
     */
    public void install() throws DistributionInstallerException {
        try {
            deleteInstallDirectory();

            // Resolve the URL to download the distribution
            final URL distributionUrl = settings.getUrlResolver().resolve();

            // Download the distribution
            final String distributionUrlAsString = distributionUrl.toString();
            logLifecycle("Downloading distribution at '" + distributionUrlAsString + "'");
            final Path distributionFile = settings
                .getTemporaryDirectory()
                .resolve(distributionUrlAsString.substring(distributionUrlAsString.lastIndexOf('/') + 1));
            settings.getDownloader().download(distributionUrl, distributionFile);

            final Optional<DistributionValidator> validator = settings.getValidator();
            if (validator.isPresent()) {
                validator.get().validate(distributionUrl, distributionFile);
            }

            // Explodes the archive
            final Path explodeTargetDirectory = Files.createDirectory(distributionFile.resolveSibling("extract"));
            logLifecycle("Exploding distribution into '" + explodeTargetDirectory + "'");
            final ExplodeSettings explodeSettings = new ExplodeSettings(distributionFile, explodeTargetDirectory,
                settings.getOsName());
            final Optional<String> distributionFileExtension = Utils
                .getExtension(distributionFile.getFileName().toString())
                .flatMap(extension -> {
                    final Optional<String> newExtension;
                    if (Utils.isGzipExtension(extension)) {
                        newExtension = Utils
                            .getExtension(Utils.removeExtension(distributionFile.getFileName().toString()))
                            .map(ext -> ext + extension);
                    } else {
                        newExtension = Optional.of(extension);
                    }
                    return newExtension;
                });
            distributionFileExtension
                .flatMap(extension -> settings.getArchiverFactory().get(extension))
                .orElseThrow(UnsupportedDistributionArchiveException::new)
                .explode(explodeSettings);

            logLifecycle("Moving distribution into '" + settings.getInstallDirectory() + "'");
            // Removes the root directory of exploded content, if relevant.
            final Set<Path> distributionFiles;
            try (final Stream<Path> childFiles = Files.list(explodeTargetDirectory)) {
                distributionFiles = childFiles
                    .filter(childFile -> !childFile.getFileName().equals(distributionFile.getFileName()))
                    .collect(toSet());
            }
            final Path distributionRootDirectory;
            if (distributionFiles.size() == 1) {
                distributionRootDirectory = distributionFiles.iterator().next();
            } else {
                distributionRootDirectory = explodeTargetDirectory;
            }
            Utils.moveFiles(distributionRootDirectory, settings.getInstallDirectory());

            logLifecycle("Removing explode directory '" + explodeTargetDirectory + "'");
            Files.deleteIfExists(explodeTargetDirectory);
            logLifecycle("Removing distribution file '" + distributionFile + "'");
            Files.delete(distributionFile);

            logLifecycle("Distribution installed in '" + settings.getInstallDirectory() + "'");
        } catch (final IOException | DistributionUrlResolverException | DownloadException | DistributionValidatorException | UnsupportedDistributionArchiveException | UnsupportedEntryException | SlipAttackException | ArchiverException e) {
            throw new DistributionInstallerException(e);
        }
    }

    private void deleteInstallDirectory() throws IOException {
        logLifecycle("Removing install directory '" + settings.getInstallDirectory() + "'.");
        Utils.deleteRecursively(settings.getInstallDirectory(), true);
    }
}
