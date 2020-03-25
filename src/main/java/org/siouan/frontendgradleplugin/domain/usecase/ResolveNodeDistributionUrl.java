package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.model.DistributionProperties;

/**
 * Resolves the URL to download a Node distribution.
 */
public class ResolveNodeDistributionUrl implements DistributionUrlResolver {

    @Override
    public URL execute(@Nonnull final DistributionProperties distributionProperties)
        throws DistributionUrlResolverException {
        final String urlAsString;
        final String distributionUrl = distributionProperties.getDownloadUrl();
        final String version = distributionProperties.getVersion();
        if (distributionUrl == null) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append("https://nodejs.org/dist/v");
            buffer.append(version);
            buffer.append("/node-v");
            buffer.append(version);
            final Optional<String> extension = resolveExtension(distributionProperties);
            if (extension.isPresent()) {
                buffer.append(extension.get());
            } else {
                throw new DistributionUrlResolverException(
                    "This platform is not supported yet: " + distributionProperties.getPlatform());
            }

            urlAsString = buffer.toString();
        } else {
            urlAsString = distributionUrl;
        }

        try {
            return URI.create(urlAsString).toURL();
        } catch (final MalformedURLException e) {
            throw new DistributionUrlResolverException(e);
        }
    }

    private Optional<String> resolveExtension(@Nonnull final DistributionProperties distributionProperties) {
        final String extension;
        if (distributionProperties.getPlatform().is64BitsArch()) {
            if (distributionProperties.getPlatform().isWindowsOs()) {
                extension = "-win-x64.zip";
            } else if (distributionProperties.getPlatform().isLinuxOs()) {
                extension = "-linux-x64.tar.gz";
            } else if (distributionProperties.getPlatform().isMacOs()) {
                extension = "-darwin-x64.tar.gz";
            } else {
                extension = null;
            }
        } else {
            if (distributionProperties.getPlatform().isWindowsOs()) {
                extension = "-win-x86.zip";
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }
}
