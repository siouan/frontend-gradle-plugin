package org.siouan.frontendgradleplugin.domain.model;

import java.util.Arrays;
import javax.annotation.Nonnull;

import org.siouan.frontendgradleplugin.domain.util.SystemUtils;

/**
 * This class represents an execution platform, identified by the architecture of the JVM, and the name of the OS.
 *
 * @since 1.4.2
 */
public class Platform {

    private static final String[] SUPPORTED_JVM_64_BITS_ARCH_IDS = new String[] {"x64", "x86_64", "amd64", "ppc",
        "sparc"};

    private static final String[] SUPPORTED_LINUX_OS_IDS = new String[] {"linux"};

    private static final String[] SUPPORTED_MAC_OS_IDS = new String[] {"mac os"};

    private static final String[] SUPPORTED_WINDOWS_OS_IDS = new String[] {"windows"};

    private final String jvmArch;

    private final String osName;

    /**
     * Builds a platform with custom architecture.
     *
     * @param jvmArch JVM architecture.
     * @param osName Underlying OS name.
     */
    public Platform(@Nonnull final String jvmArch, @Nonnull final String osName) {
        this.jvmArch = jvmArch;
        this.osName = osName;
    }

    /**
     * Tells whether the JVM has a 64 bits architecture.
     *
     * @return {@code true} if the architecture is a 64 bits architecture.
     * @see SystemUtils#getSystemJvmArch()
     */
    public boolean is64BitsArch() {
        return matchesAnyIdPart(jvmArch, SUPPORTED_JVM_64_BITS_ARCH_IDS);
    }

    /**
     * Tells whether the platform OS is a Linux OS.
     *
     * @return {@code true} if the platform OS is a Linux OS.
     * @see SystemUtils#getSystemOsName()
     */
    public boolean isLinuxOs() {
        return matchesAnyIdPart(osName, SUPPORTED_LINUX_OS_IDS);
    }

    /**
     * Tells whether the platform OS is a Mac OS.
     *
     * @return {@code true} if the platform OS is a Mac OS.
     * @see SystemUtils#getSystemOsName()
     */
    public boolean isMacOs() {
        return matchesAnyIdPart(osName, SUPPORTED_MAC_OS_IDS);
    }

    /**
     * Tells whether the platform OS is a Windows OS.
     *
     * @return {@code true} if the platform OS is a Windows OS.
     * @see SystemUtils#getSystemOsName()
     */
    public boolean isWindowsOs() {
        return matchesAnyIdPart(osName, SUPPORTED_WINDOWS_OS_IDS);
    }

    private boolean matchesAnyIdPart(@Nonnull final String id, @Nonnull final String[] idParts) {
        final String lowerCaseId = id.toLowerCase();
        return Arrays.stream(idParts).anyMatch(lowerCaseId::contains);
    }
}
