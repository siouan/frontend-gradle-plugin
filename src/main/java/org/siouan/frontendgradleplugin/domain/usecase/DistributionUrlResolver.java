package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.URL;

import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.model.DistributionProperties;

/**
 * Interface of a component capable to resolve the URL to download a distribution.
 */
@FunctionalInterface
public interface DistributionUrlResolver {

    /**
     * Resolves the URL to download the distribution.
     *
     * @param distributionProperties Properties to resolve the appropriate distribution.
     * @return An URL.
     * @throws DistributionUrlResolverException If the URL cannot be resolved.
     */
    URL execute(DistributionProperties distributionProperties) throws DistributionUrlResolverException;
}
