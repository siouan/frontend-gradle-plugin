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
    private static final String LINUX_64_EXTENSION = "linux-x64.tar.gz";

    /**
     * Extension of the distribution file name under a 64 bits MacOS O/S.
     */
    private static final String MACOS_64_EXTENSION = "darwin-x64.tar.gz";

    /**
     * Extension of the distribution file name under a 32 bits Windows O/S.
     */
    private static final String WINDOWS_32_EXTENSION = "win-x86.zip";

    /**
     * Extension of the distribution file name under a 64 bits Windows O/S.
     */
    private static final String WINDOWS_64_EXTENSION = "win-x64.zip";

    /**
     * Token in the download URL pattern that is replaced by the version.
     */
    private static final String VERSION_TOKEN = "VERSION";

    /**
     * Token in the download URL pattern that is replaced by the detected OS.
     */
    private static final String OS_EXTENSION_TOKEN = "OS_EXTENSION";

    private static final String URL_PATTERN = "https://nodejs.org/dist/v" + VERSION_TOKEN
        + "/node-v" + VERSION_TOKEN +  "-" + OS_EXTENSION_TOKEN;

    @Override
    @Nonnull
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition)
        throws UnsupportedPlatformException, MalformedURLException {
        final String version = distributionDefinition.getVersion();
        if (distributionDefinition.getDownloadUrl() != null) {
            return distributionDefinition.getDownloadUrl();
        }

        String urlPattern = URL_PATTERN;
        if (distributionDefinition.getDownloadUrlPattern() != null) {
            urlPattern = distributionDefinition.getDownloadUrlPattern();
        }

        final Optional<String> extension = resolveExtension(distributionDefinition.getPlatform());
        if (!extension.isPresent()) {
            throw new UnsupportedPlatformException(distributionDefinition.getPlatform());
        }

        String url = urlPattern
            .replace(VERSION_TOKEN, version)
            .replace(OS_EXTENSION_TOKEN, extension.get());

        return URI.create(url).toURL();
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
