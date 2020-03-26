package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Resolves the URL to download a Node distribution.
 */
public class ResolveNodeDistributionUrl implements DistributionUrlResolver {

    /**
     * Extension of the distribution file name under a 64 bits Linux O/S.
     */
    private static final String LINUX_64_EXTENSION = "-linux-x64.tar.gz";

    /**
     * Extension of the distribution file name under a 64 bits MacOS O/S.
     */
    private static final String MACOS_64_EXTENSION = "-darwin-x64.tar.gz";

    /**
     * Extension of the distribution file name under a 32 bits Windows O/S.
     */
    private static final String WINDOWS_32_EXTENSION = "-win-x86.zip";

    /**
     * Extension of the distribution file name under a 64 bits Windows O/S.
     */
    private static final String WINDOWS_64_EXTENSION = "-win-x64.zip";

    private static final String NODE_CDN_PART_1 = "https://nodejs.org/dist/v";

    private static final String NODE_CDN_PART_2 = "/node-v";

    @Override
    @Nonnull
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition)
        throws UnsupportedPlatformException, MalformedURLException {
        final String version = distributionDefinition.getVersion();
        if (distributionDefinition.getDownloadUrl() == null) {
            final StringBuilder buffer = new StringBuilder();
            buffer.append(NODE_CDN_PART_1);
            buffer.append(version);
            buffer.append(NODE_CDN_PART_2);
            buffer.append(version);
            final Optional<String> extension = resolveExtension(distributionDefinition.getPlatform());
            if (extension.isPresent()) {
                buffer.append(extension.get());
            } else {
                throw new UnsupportedPlatformException(distributionDefinition.getPlatform());
            }

            return URI.create(buffer.toString()).toURL();
        }

        return distributionDefinition.getDownloadUrl();
    }

    /**
     * Resolves the extension of the distribution file name considering a target platform.
     *
     * @param targetPlatform Target platform.
     * @return The extension of the distribution file name. If empty, the target platform is not supported.
     */
    @Nonnull
    private Optional<String> resolveExtension(@Nonnull final Platform targetPlatform) {
        final String extension;
        if (targetPlatform.is64BitsArch()) {
            if (targetPlatform.isWindowsOs()) {
                extension = WINDOWS_64_EXTENSION;
            } else if (targetPlatform.isLinuxOs()) {
                extension = LINUX_64_EXTENSION;
            } else if (targetPlatform.isMacOs()) {
                extension = MACOS_64_EXTENSION;
            } else {
                extension = null;
            }
        } else {
            if (targetPlatform.isWindowsOs()) {
                extension = WINDOWS_32_EXTENSION;
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }
}
