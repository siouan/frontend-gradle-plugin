package org.siouan.frontendgradleplugin.domain.usecase;

import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;

/**
 * Identifies the appropriate URL resolver from a type of distribution.
 *
 * @since 2.0.0
 */
public class GetDistributionUrlResolver {

    /**
     * Gets an instance of a distribution URL resolver supporting the given ID.
     *
     * @param distributionId Distribution ID.
     * @return Distribution URL resolver.
     * @see DistributionId
     */
    @Nonnull
    public Optional<DistributionUrlResolver> execute(@Nonnull final String distributionId) {
        switch (distributionId) {
        case DistributionId.NODE:
            return Optional.of(new ResolveNodeDistributionUrl());
        case DistributionId.YARN:
            return Optional.of(new ResolveYarnDistributionUrl());
        default:
            return Optional.empty();
        }
    }
}
