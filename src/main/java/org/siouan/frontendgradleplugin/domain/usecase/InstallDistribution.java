package org.siouan.frontendgradleplugin.domain.usecase;

import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionInstallerException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.DistributionProperties;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorProperties;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.InstallDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.util.FileUtils;
import org.siouan.frontendgradleplugin.domain.util.PathUtils;

/**
 * Component that downloads and installs a distribution.
 */
public class InstallDistribution {

    private final DistributionUrlResolver distributionUrlResolver;

    private final DownloadResource downloadResource;

    private final DistributionValidator distributionValidator;

    private final ArchiverProvider archiverProvider;

    private final Logger logger;

    public InstallDistribution(DistributionUrlResolver distributionUrlResolver, DownloadResource downloadResource,
        DistributionValidator distributionValidator, ArchiverProvider archiverProvider, Logger logger) {
        this.distributionUrlResolver = distributionUrlResolver;
        this.downloadResource = downloadResource;
        this.distributionValidator = distributionValidator;
        this.archiverProvider = archiverProvider;
        this.logger = logger;
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
    public void execute(InstallDistributionSettings installDistributionSettings)
        throws DistributionInstallerException, DistributionUrlResolverException, FrontendIOException,
        DistributionValidatorException, UnsupportedDistributionArchiveException, SlipAttackException, ArchiverException,
        UnsupportedEntryException, URISyntaxException {
        try {
            logger.log("Removing install directory '" + installDistributionSettings.getInstallDirectory() + "'.");
            FileUtils.deleteFileTree(installDistributionSettings.getInstallDirectory(), true);

            // Resolve the URL to download the distribution
            final DistributionProperties distributionProperties = new DistributionProperties(
                installDistributionSettings.getPlatform(), installDistributionSettings.getVersion(),
                installDistributionSettings.getDownloadUrl());
            final URL distributionUrl = distributionUrlResolver.execute(distributionProperties);

            // Download the distribution
            final String distributionUrlAsString = distributionUrl.toString();
            logger.log("Downloading distribution at '" + distributionUrlAsString + "'");
            final Path distributionFilePath = installDistributionSettings
                .getTemporaryDirectory()
                .resolve(distributionUrlAsString.substring(distributionUrlAsString.lastIndexOf('/') + 1));
            downloadResource.execute(
                new DownloadParameters(distributionUrl, installDistributionSettings.getTemporaryDirectory(),
                    distributionFilePath));

            if (true/*installDistributionSettings.isValidationEnabled()*/) {
                final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
                    installDistributionSettings.getInstallDirectory(),
                    installDistributionSettings.getTemporaryDirectory(), distributionUrl, distributionFilePath);
                distributionValidator.validate(distributionValidatorProperties);
            }

            // Explodes the archive
            final Path explodeTargetDirectory = Files.createDirectory(distributionFilePath.resolveSibling("extract"));
            logger.log("Exploding distribution into '" + explodeTargetDirectory + "'");
            final ExplodeSettings explodeSettings = new ExplodeSettings(distributionFilePath, explodeTargetDirectory,
                installDistributionSettings.getPlatform());
            final Optional<String> distributionFileExtension = PathUtils
                .getExtension(distributionFilePath)
                .flatMap(extension -> {
                    final Optional<String> newExtension;
                    if (PathUtils.isGzipExtension(extension)) {
                        newExtension = PathUtils
                            .getExtension(PathUtils.removeExtension(distributionFilePath))
                            .map(ext -> ext + extension);
                    } else {
                        newExtension = Optional.of(extension);
                    }
                    return newExtension;
                });
            distributionFileExtension
                .flatMap(archiverProvider::findByFilenameExtension)
                .orElseThrow(() -> new UnsupportedDistributionArchiveException(distributionFilePath))
                .explode(explodeSettings);

            logger.log("Moving distribution into '" + installDistributionSettings.getInstallDirectory() + "'");
            // Removes the root directory of exploded content, if relevant.
            final Set<Path> distributionFiles;
            try (final Stream<Path> childFiles = Files.list(explodeTargetDirectory)) {
                distributionFiles = childFiles
                    .filter(childFile -> !childFile.getFileName().equals(distributionFilePath.getFileName()))
                    .collect(toSet());
            }
            final Path distributionRootDirectory;
            if (distributionFiles.size() == 1) {
                distributionRootDirectory = distributionFiles.iterator().next();
            } else {
                distributionRootDirectory = explodeTargetDirectory;
            }
            FileUtils.moveFileTree(distributionRootDirectory, installDistributionSettings.getInstallDirectory());

            logger.log("Removing explode directory '" + explodeTargetDirectory + "'");
            Files.deleteIfExists(explodeTargetDirectory);
            logger.log("Removing distribution file '" + distributionFilePath + "'");
            Files.delete(distributionFilePath);

            logger.log("Distribution installed in '" + installDistributionSettings.getInstallDirectory() + "'");
        } catch (final IOException e) {
            throw new DistributionInstallerException(e);
        }
    }
}
