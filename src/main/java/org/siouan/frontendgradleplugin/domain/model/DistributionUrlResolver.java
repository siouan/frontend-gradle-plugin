package org.siouan.frontendgradleplugin.domain.model;

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;

/**
 * Interface of a component capable to resolve the URL to download a distribution.
 */
@FunctionalInterface
public interface DistributionUrlResolver {

    /**
     * Resolves the URL to download the distribution.
     *
     * @param distributionDefinition Properties to resolve the appropriate distribution.
     * @return An URL.
     * @throws UnsupportedPlatformException If the underlying platform is not supported and a URL cannot be resolved.
     * @throws MalformedURLException If the URL built is invalid due to an invalid definition.
     */
    @Nonnull
    URL execute(@Nonnull DistributionDefinition distributionDefinition)
        throws UnsupportedPlatformException, MalformedURLException;
}
