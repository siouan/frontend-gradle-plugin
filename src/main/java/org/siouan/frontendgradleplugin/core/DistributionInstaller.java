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
            checkInstallDirectory();

            // Resolve the URL to download the distribution
            final URL distributionUrl = settings.getUrlResolver().resolve();

            // Download the distribution
            final String distributionUrlAsString = distributionUrl.toString();
            logLifecycle("Downloading distribution at '" + distributionUrlAsString + "'");
            final Path distributionFile = settings.getInstallDirectory()
                .resolve(distributionUrlAsString.substring(distributionUrlAsString.lastIndexOf('/') + 1));
            settings.getDownloader().download(distributionUrl, distributionFile);

            final Optional<DistributionValidator> validator = settings.getValidator();
            if (validator.isPresent()) {
                validator.get().validate(distributionUrl, distributionFile);
            }


            // Explodes the archive
            logLifecycle("Exploding distribution into '" + distributionFile.getParent() + "'");
            final ExplodeSettings explodeSettings = new ExplodeSettings(distributionFile, distributionFile.getParent(),
                settings.getOsName());
            final Optional<String> distributionFileExtension = Utils
                .getExtension(distributionFile.getFileName().toString()).flatMap(extension -> {
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
            distributionFileExtension.flatMap(extension -> settings.getArchiverFactory().get(extension))
                .orElseThrow(UnsupportedDistributionArchiveException::new).explode(explodeSettings);

            // Removes the root directory of exploded content, if relevant.
            final Set<Path> distributionFiles;
            try (final Stream<Path> childFiles = Files.list(distributionFile.getParent())) {
                distributionFiles = childFiles
                    .filter(childFile -> !childFile.getFileName().equals(distributionFile.getFileName()))
                    .collect(toSet());
            }
            if (distributionFiles.size() == 1) {
                final Path distributionRootDirectory = distributionFiles.iterator().next();
                Utils.moveFiles(distributionRootDirectory, distributionFile.getParent());
                Files.delete(distributionRootDirectory);
            }

            logLifecycle("Removing distribution file '" + distributionFile + "'");
            Files.delete(distributionFile);

            logLifecycle("Distribution installed in '" + distributionFile.getParent() + "'");
        } catch (final IOException | DistributionUrlResolverException | DownloadException | DistributionValidatorException | UnsupportedDistributionArchiveException | UnsupportedEntryException | SlipAttackException | ArchiverException e) {
            throw new DistributionInstallerException(e);
        }
    }

    private void checkInstallDirectory() throws IOException {
        Files.createDirectories(settings.getInstallDirectory());

        logLifecycle("Removing content in install directory '" + settings.getInstallDirectory() + "'.");
        Utils.deleteRecursively(settings.getInstallDirectory(), false);
    }
}
