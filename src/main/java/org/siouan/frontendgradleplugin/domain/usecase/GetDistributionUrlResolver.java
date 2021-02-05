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

    private final ResolveNodeDistributionUrl resolveNodeDistributionUrl;

    private final ResolveYarnDistributionUrl resolveYarnDistributionUrl;

    public GetDistributionUrlResolver(final ResolveNodeDistributionUrl resolveNodeDistributionUrl,
        final ResolveYarnDistributionUrl resolveYarnDistributionUrl) {
        this.resolveNodeDistributionUrl = resolveNodeDistributionUrl;
        this.resolveYarnDistributionUrl = resolveYarnDistributionUrl;
    }

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
            return Optional.of(resolveNodeDistributionUrl);
        case DistributionId.YARN:
            return Optional.of(resolveYarnDistributionUrl);
        default:
            return Optional.empty();
        }
    }
}
