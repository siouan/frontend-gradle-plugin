package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class represents an execution platform, identified by the architecture of the JVM, and the name of the OS.
 *
 * @since 2.0.0
 */
public class Platform {

    private static final String[] SUPPORTED_JVM_64_BITS_ARCH_IDS = new String[] {"x64", "x86_64", "amd64", "ppc",
        "sparc"};

    private static final String[] SUPPORTED_LINUX_OS_IDS = new String[] {"linux"};

    private static final String[] SUPPORTED_MAC_OS_IDS = new String[] {"mac os"};

    private static final String[] SUPPORTED_WINDOWS_OS_IDS = new String[] {"windows"};

    /**
     * Architecture on which JVM runs.
     */
    private final String jvmArch;

    /**
     * Name of the underlying O/S.
     */
    private final String osName;

    /**
     * Install directory of Node.js.
     */
    private final Path nodeInstallDirectory;

    /**
     * Install directory of Yarn.
     */
    private final Path yarnInstallDirectory;

    /**
     * Builds a platform with custom architecture.
     *
     * @param jvmArch JVM architecture.
     * @param osName Underlying O/S name.
     * @param nodeInstallDirectory Path to a platform-wide Node.js installation.
     * @param yarnInstallDirectory Path to a platform-wide Yarn installation.
     */
    public Platform(@Nonnull final String jvmArch, @Nonnull final String osName,
        @Nullable final Path nodeInstallDirectory, @Nullable final Path yarnInstallDirectory) {
        this.jvmArch = jvmArch;
        this.osName = osName;
        this.nodeInstallDirectory = nodeInstallDirectory;
        this.yarnInstallDirectory = yarnInstallDirectory;
    }

    /**
     * Tells whether the JVM has a 64 bits architecture.
     *
     * @return {@code true} if the architecture is a 64 bits architecture.
     */
    public boolean is64BitsArch() {
        return matchesAnyIdPart(jvmArch, SUPPORTED_JVM_64_BITS_ARCH_IDS);
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
     * Gets the path to a global Node.js installation.
     *
     * @return Path to an install directory.
     */
    @Nullable
    public Path getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    /**
     * Gets the path to a global Yarn installation.
     *
     * @return Path to an install directory.
     */
    @Nullable
    public Path getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Override
    public String toString() {
        return Platform.class.getSimpleName() + " {jvmArch=" + jvmArch + ", osName=" + osName
            + ", nodeInstallDirectory=" + nodeInstallDirectory + ", yarnInstallDirectory=" + yarnInstallDirectory + '}';
    }

    /**
     * Whether the given ID matches any of the given parts.
     *
     * @param id ID.
     * @param parts Array of ID parts.
     * @return {@code true} if a part is a substring on the given ID.
     */
    private boolean matchesAnyIdPart(@Nonnull final String id, @Nonnull final String[] parts) {
        final String lowerCaseId = id.toLowerCase();
        return Arrays.stream(parts).anyMatch(lowerCaseId::contains);
    }
}
