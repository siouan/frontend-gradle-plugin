package org.siouan.frontendgradleplugin.yarn;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.siouan.frontendgradleplugin.job.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.job.DistributionUrlResolverException;

/**
 * Resolves the name of a distribution supported by the underlying OS and JRE.
 */
public final class YarnDistributionUrlResolver implements DistributionUrlResolver {

    private static final String VERSION_TOKEN = "VERSION";

    private static final String URL_PATTERN = "https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz";

    private final String version;

    private final String distributionUrl;

    /**
     * Builds a resolver for the local platform.
     *
     * @param version Version of the distribution.
     * @param distributionUrl URL to download the distribution.
     */
    public YarnDistributionUrlResolver(final String version, final String distributionUrl) {
        if ((version == null) && (distributionUrl == null)) {
            throw new IllegalArgumentException(
                "Either the Yarn version or a download URL must be configured (see plugin's DSL).");
        }
        this.version = version;
        this.distributionUrl = distributionUrl;
    }

    public URL resolve() throws DistributionUrlResolverException {
        final String urlAsString;
        if (version == null) {
            urlAsString = distributionUrl;
        } else {
            urlAsString = URL_PATTERN.replaceAll(VERSION_TOKEN, version);
        }

        try {
            return URI.create(urlAsString).toURL();
        } catch (final MalformedURLException e) {
            throw new DistributionUrlResolverException(e);
        }
    }
}
