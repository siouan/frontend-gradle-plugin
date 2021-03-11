package org.siouan.frontendgradleplugin.domain.usecase;

import java.util.Optional;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.model.Platform;

/**
 * Resolves the architecture ID used to build the URL to download the Node.js distribution.
 *
 * @since 5.0.1
 */
public class ResolveNodeDistributionArchitectureId {

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

    /**
     * Resolves the architecture ID for a given platform.
     *
     * @param platform Platform.
     * @return The architecture ID. If empty, the target platform is not supported.
     */
    @Nonnull
    public Optional<String> execute(@Nonnull final Platform platform) {
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
}
