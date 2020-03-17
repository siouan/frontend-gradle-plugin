package org.siouan.frontendgradleplugin.core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

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
        this.version = version;
        this.distributionUrl = distributionUrl;
    }

    @Override
    public URL resolve() throws DistributionUrlResolverException {
        final String urlAsString;
        if (distributionUrl == null) {
            urlAsString = URL_PATTERN.replace(VERSION_TOKEN, version);
        } else {
            urlAsString = distributionUrl;
        }

        try {
            return URI.create(urlAsString).toURL();
        } catch (final MalformedURLException e) {
            throw new DistributionUrlResolverException(e);
        }
    }
}
