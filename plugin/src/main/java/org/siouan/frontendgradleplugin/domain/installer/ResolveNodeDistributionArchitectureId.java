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
     * Architecture ID for a AIX O/S.
     */
    static final String AIX_PPC64_ARCH = "aix-ppc64";

    /**
     * Architecture ID for a 64 bits Linux O/S.
     */
    static final String LINUX_X64_ARCH = "linux-x64";

    /**
     * Architecture ID for an ARM 64 bits Linux O/S.
     */
    static final String LINUX_ARM64_ARCH = "linux-arm64";

    /**
     * Architecture ID for a 64 bits Linux O/S.
     */
    static final String LINUX_ARM32_ARCH = "linux-armv7l";

    /**
     * Architecture ID for a X64 bits MacOS O/S.
     */
    static final String MACOS_X64_ARCH = "darwin-x64";

    /**
     * Architecture ID for an ARM 64 bits MacOS O/S.
     */
    static final String MACOS_ARM64_ARCH = "darwin-arm64";

    /**
     * Architecture ID for a 32 bits Windows O/S.
     */
    static final String WINDOWS_X86_ARCH = "win-x86";

    /**
     * Architecture ID for a X64 bits Linux O/S.
     */
    static final String WINDOWS_X64_ARCH = "win-x64";

    /**
     * Architecture ID for an ARM 64 bits Windows O/S.
     */
    static final String WINDOWS_ARM64_ARCH = "win-arm64";

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
                extension = platform.isArm64BitsArch() ? WINDOWS_ARM64_ARCH : WINDOWS_X64_ARCH;
            } else if (platform.isLinuxOs()) {
                extension = platform.isArm64BitsArch() ? LINUX_ARM64_ARCH : LINUX_X64_ARCH;
            } else if (platform.isMacOs()) {
                extension = platform.isArm64BitsArch() ? MACOS_ARM64_ARCH : MACOS_X64_ARCH;
            } else if (platform.isAixOs()) {
                extension = AIX_PPC64_ARCH;
            } else {
                extension = null;
            }
        } else {
            if (platform.isWindowsOs()) {
                extension = WINDOWS_X86_ARCH;
            } else if (platform.isLinuxOs() && platform.isArm32BitsArch()) {
                extension = LINUX_ARM32_ARCH;
            } else {
                extension = null;
            }
        }

        return Optional.ofNullable(extension);
    }
}
