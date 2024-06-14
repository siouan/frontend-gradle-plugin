package org.siouan.frontendgradleplugin.domain;

import java.util.Arrays;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This class represents an execution platform, identified by the architecture of the JVM, and the name of the OS.
 *
 * @since 2.0.0
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Platform {

    private static final String[] SUPPORTED_JVM_ARM_32_BITS_ARCH_IDS = new String[] {"arm"};

    private static final String[] SUPPORTED_JVM_ARM_64_BITS_ARCH_IDS = new String[] {"aarch64"};

    private static final String[] SUPPORTED_JVM_64_BITS_ARCH_IDS = new String[] {"x64", "x86_64", "amd64", "ppc",
        "sparc", "aarch64"};

    private static final String[] SUPPORTED_LINUX_OS_IDS = new String[] {"linux"};

    private static final String[] SUPPORTED_MAC_OS_IDS = new String[] {"mac os"};

    private static final String[] SUPPORTED_WINDOWS_OS_IDS = new String[] {"windows"};

    /**
     * Architecture of the underlying JVM.
     */
    @EqualsAndHashCode.Include
    @ToString.Include
    private final String jvmArch;

    /**
     * System name of the O/S.
     */
    @EqualsAndHashCode.Include
    @ToString.Include
    private final String osName;

    /**
     * Tells whether the JVM has a 64 bits architecture.
     *
     * @return {@code true} if the architecture is a 64 bits architecture.
     */
    public boolean is64BitsArch() {
        return matchesAnyIdPart(jvmArch, SUPPORTED_JVM_64_BITS_ARCH_IDS);
    }

    /**
     * Tells whether the JVM has an ARM 64 bits architecture.
     *
     * @return {@code true} if the architecture is an ARM 64 bits architecture.
     */
    public boolean isArm64BitsArch() {
        return matchesAnyIdPart(jvmArch, SUPPORTED_JVM_ARM_64_BITS_ARCH_IDS);
    }

    /**
     * Tells whether the JVM has an ARM 32 bits architecture.
     *
     * @return {@code true} if the architecture is an ARM 64 bits architecture.
     */
    public boolean isArm32BitsArch() {
        return matchesAnyIdPart(jvmArch, SUPPORTED_JVM_ARM_32_BITS_ARCH_IDS);
    }

    /**
     * Tells whether the platform O/S is a Linux O/S.
     *
     * @return {@code true} if the platform O/S is a Linux O/S.
     */
    public boolean isLinuxOs() {
        return matchesAnyIdPart(osName, SUPPORTED_LINUX_OS_IDS);
    }

    /**
     * Tells whether the platform O/S is a Mac O/S.
     *
     * @return {@code true} if the platform O/S is a Mac O/S.
     */
    public boolean isMacOs() {
        return matchesAnyIdPart(osName, SUPPORTED_MAC_OS_IDS);
    }

    /**
     * Tells whether the platform O/S is a Windows O/S.
     *
     * @return {@code true} if the platform O/S is a Windows O/S.
     */
    public boolean isWindowsOs() {
        return matchesAnyIdPart(osName, SUPPORTED_WINDOWS_OS_IDS);
    }

    /**
     * Whether the given ID matches any of the given parts.
     *
     * @param id ID.
     * @param parts Array of ID parts.
     * @return {@code true} if a part is a substring on the given ID.
     */
    private boolean matchesAnyIdPart(final String id, final String[] parts) {
        final String lowerCaseId = id.toLowerCase();
        return Arrays.stream(parts).anyMatch(lowerCaseId::contains);
    }
}
