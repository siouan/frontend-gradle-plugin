package org.siouan.frontendgradleplugin.domain.installer;

import java.util.Optional;

import org.siouan.frontendgradleplugin.domain.Platform;

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
     * Architecture ID for a 64 bits Linux O/S.
     */
    private static final String LINUX_ARM_32_ARCH = "linux-armv7l";

    /**
     * Architecture ID for a PPC 64 bits MacOS O/S.
     */
    private static final String MACOS_PPC_64_ARCH = "darwin-x64";

    /**
     * Architecture ID for an ARM 64 bits MacOS O/S.
     */
    private static final String MACOS_ARM_64_ARCH = "darwin-arm64";

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
    public Optional<String> execute(final Platform platform) {
        final String extension;
        if (platform.is64BitsArch()) {
            if (platform.isWindowsOs()) {
                extension = WINDOWS_64_ARCH;
            } else if (platform.isLinuxOs()) {
                extension = LINUX_64_ARCH;
            } else if (platform.isMacOs()) {
                extension = platform.isArm64BitsArch() ? MACOS_ARM_64_ARCH : MACOS_PPC_64_ARCH;
            } else {
                extension = null;
            }
        } else {
            if (platform.isWindowsOs()) {
                extension = WINDOWS_32_ARCH;
            } else if (platform.isLinuxOs() && platform.isArm32BitsArch()) {
                extension = LINUX_ARM_32_ARCH;
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }
}
