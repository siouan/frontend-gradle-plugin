package org.siouan.frontendgradleplugin.domain.usecase;

import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidator;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

/**
 * Identifies the appropriate validator for a type of distribution.
 *
 * @since 2.0.0
 */
public class GetDistributionValidator {

    private final FileManager fileManager;

    private final DownloadResource downloadResource;

    private final ReadNodeDistributionShasum readNodeDistributionShasum;

    private final HashFile hashFile;

    private final Logger logger;

    public GetDistributionValidator(final FileManager fileManager, final DownloadResource downloadResource,
        final ReadNodeDistributionShasum readNodeDistributionShasum, final HashFile hashFile, final Logger logger) {
        this.fileManager = fileManager;
        this.downloadResource = downloadResource;
        this.readNodeDistributionShasum = readNodeDistributionShasum;
        this.hashFile = hashFile;
        this.logger = logger;
    }

    /**
     * Gets an instance of a distribution validator supporting the given ID.
     *
     * @param distributionId Distribution ID.
     * @return Distribution validator.
     * @see DistributionId
     */
    @Nonnull
    public Optional<DistributionValidator> execute(@Nonnull String distributionId) {
        switch (distributionId) {
        case DistributionId.NODE:
            return Optional.of(
                new ValidateNodeDistribution(fileManager, downloadResource, readNodeDistributionShasum, hashFile,
                    logger));
        case DistributionId.YARN:
        default:
            return Optional.empty();
        }
    }
}
