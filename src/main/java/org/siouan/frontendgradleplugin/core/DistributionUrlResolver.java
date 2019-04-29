package org.siouan.frontendgradleplugin.core;

import java.net.URL;

/**
 * Interface of a component capable to resolve the URL to download a distribution.
 */
@FunctionalInterface
interface DistributionUrlResolver {

    /**
     * Resolves the URL to download the distribution.
     *
     * @return An URL.
     * @throws DistributionUrlResolverException If the URL cannot be resolved.
     */
    URL resolve() throws DistributionUrlResolverException;
}
