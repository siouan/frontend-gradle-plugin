package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.model.DistributionProperties;

/**
 * Resolves the name of a distribution supported by the underlying OS and JRE.
 */
public class ResolveYarnDistributionUrl implements DistributionUrlResolver {

    private static final String VERSION_TOKEN = "VERSION";

    private static final String URL_PATTERN = "https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz";

    @Override
    public URL execute(final DistributionProperties distributionProperties) throws DistributionUrlResolverException {
        final String urlAsString;
        if (distributionProperties.getDownloadUrl() == null) {
            urlAsString = URL_PATTERN.replaceAll(VERSION_TOKEN, distributionProperties.getVersion());
        } else {
            urlAsString = distributionProperties.getDownloadUrl();
        }

        try {
            return URI.create(urlAsString).toURL();
        } catch (final MalformedURLException e) {
            throw new DistributionUrlResolverException(e);
        }
    }
}
