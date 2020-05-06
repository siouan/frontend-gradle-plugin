package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;

/**
 * Resolves the URL to download the Yarn distribution.
 */
public class ResolveYarnDistributionUrl implements DistributionUrlResolver {

    private static final String VERSION_TOKEN = "VERSION";

    @Nonnull
    @Override
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition) throws MalformedURLException {
        return new URL(
            distributionDefinition.getDownloadUrlPattern().replace(VERSION_TOKEN, distributionDefinition.getVersion()));
    }
}
