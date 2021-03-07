package org.siouan.frontendgradleplugin.domain.usecase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Resolves the URL to download a Node.js distribution.
 */
public class ResolveNodeDistributionUrl implements DistributionUrlResolver {

    /**
     * Token in the download URL pattern replaced with the version number.
     */
    public static final String VERSION_TOKEN = "VERSION";

    /**
     * Token in the download URL pattern replaced with the architecture ID of the underlying platform.
     */
    public static final String ARCHITECTURE_ID_TOKEN = "ARCH";

    /**
     * Token in the download URL pattern replaced with the archive type supported on the underlying platform.
     */
    public static final String TYPE_TOKEN = "TYPE";

    /**
     * A gzipped TAR distribution archive.
     */
    private static final String TAR_GZ_TYPE = "tar.gz";

    /**
     * A zipped distribution archive.
     */
    private static final String ZIP_TYPE = "zip";

    /**
     * Architecture ID for a 64 bits Linux O/S.
     */
    private static final String LINUX_64_ARCH = "linux-x64";

    /**
     * Architecture ID for a 64 bits MacOS O/S.
     */
    private static final String MACOS_64_ARCH = "darwin-x64";

    /**
     * Architecture ID for a 32 bits Windows O/S.
     */
    private static final String WINDOWS_32_ARCH = "win-x86";

    /**
     * Architecture ID for a 64 bits Linux O/S.
     */
    private static final String WINDOWS_64_ARCH = "win-x64";

    @Override
    @Nonnull
    public URL execute(@Nonnull final DistributionDefinition distributionDefinition)
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = distributionDefinition.getPlatform();
        final Optional<String> architectureId = resolveArchitectureId(platform);
        if (!architectureId.isPresent()) {
            throw new UnsupportedPlatformException(distributionDefinition.getPlatform());
        }

        return new URL(distributionDefinition.getDownloadUrlRoot() + distributionDefinition
            .getDownloadUrlPathPattern()
            .replace(VERSION_TOKEN, distributionDefinition.getVersion())
            .replace(ARCHITECTURE_ID_TOKEN, architectureId.get())
            .replace(TYPE_TOKEN, resolveType(platform)));
    }

    /**
     * Resolves the architecture ID for a given platform.
     *
     * @param platform Platform.
     * @return The architecture ID. If empty, the target platform is not supported.
     */
    @Nonnull
    private Optional<String> resolveArchitectureId(@Nonnull final Platform platform) {
        final String extension;
        if (platform.is64BitsArch()) {
            if (platform.isWindowsOs()) {
                extension = WINDOWS_64_ARCH;
            } else if (platform.isLinuxOs()) {
                extension = LINUX_64_ARCH;
            } else if (platform.isMacOs()) {
                extension = MACOS_64_ARCH;
            } else {
                extension = null;
            }
        } else {
            if (platform.isWindowsOs()) {
                extension = WINDOWS_32_ARCH;
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }

    /**
     * Resolves the distribution type supported for a given platform.
     *
     * @param platform Platform.
     * @return The distribution type.
     */
    @Nonnull
    private String resolveType(@Nonnull final Platform platform) {
        return platform.isWindowsOs() ? ZIP_TYPE : TAR_GZ_TYPE;
    }
}
