package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;

/**
 * Resolves the name of a distribution supported by the underlying OS and JRE.
 */
public class ResolveYarnDistributionUrl implements DistributionUrlResolver {

    private static final String VERSION_TOKEN = "VERSION";

    private static final String URL_PATTERN = "https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz";

    @Nonnull
    @Override
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition) throws MalformedURLException {
        if (distributionDefinition.getDownloadUrl() == null) {
            return new URL(URL_PATTERN.replace(VERSION_TOKEN, distributionDefinition.getVersion()));
        }

        return distributionDefinition.getDownloadUrl();
    }
}
