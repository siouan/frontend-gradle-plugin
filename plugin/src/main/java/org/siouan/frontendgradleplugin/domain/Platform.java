package org.siouan.frontendgradleplugin.domain;

import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.Set;
import java.util.stream.Stream;

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

    private static final Set<String> SUPPORTED_JVM_ARM_32_BITS_ARCH_IDS = Set.of("arm");

    private static final Set<String> SUPPORTED_JVM_ARM_64_BITS_ARCH_IDS = Set.of("aarch64");

    private static final Set<String> SUPPORTED_JVM_64_BITS_ARCH_IDS = Stream
        .concat(Stream.of("x64", "x86_64", "amd64", "ppc", "sparc", "aarch64"),
            SUPPORTED_JVM_ARM_64_BITS_ARCH_IDS.stream())
        .collect(toUnmodifiableSet());

    private static final Set<String> SUPPORTED_LINUX_OS_IDS = Set.of("linux");

    private static final Set<String> SUPPORTED_MAC_OS_IDS = Set.of("mac os");

    private static final Set<String> SUPPORTED_WINDOWS_OS_IDS = Set.of("windows");

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
     * @param parts ID parts.
     * @return {@code true} if a part is a substring on the given ID.
     */
    private boolean matchesAnyIdPart(final String id, final Set<String> parts) {
        final String lowerCaseId = id.toLowerCase();
        return parts.stream().anyMatch(lowerCaseId::contains);
    }
}
