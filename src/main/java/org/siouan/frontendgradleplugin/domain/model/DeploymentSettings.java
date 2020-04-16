package org.siouan.frontendgradleplugin.domain.model;

import java.nio.file.Path;
import javax.annotation.Nonnull;

/**
 * Settings for distribution deployment.
 *
 * @since 2.0.0
 */
public class DeploymentSettings {

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Directory where the distribution content can be temporarily extracted.
     */
    private final Path extractDirectoryPath;

    /**
     * Directory where the distribution shall be installed.
     */
    private final Path installDirectoryPath;

    /**
     * Path to the distribution archive.
     */
    private final Path distributionFilePath;

    /**
     * Builds an installer.
     *
     * @param platform Underlying platform.
     * @param extractDirectoryPath Path to a directory where the archive content may be temporarily extracted.
     * @param installDirectoryPath Path to a directory where the archive content must be deployed.
     * @param distributionFilePath Path to the distribution archive.
     */
    public DeploymentSettings(@Nonnull final Platform platform, @Nonnull final Path extractDirectoryPath,
        @Nonnull final Path installDirectoryPath, @Nonnull final Path distributionFilePath) {
        this.platform = platform;
        this.extractDirectoryPath = extractDirectoryPath;
        this.installDirectoryPath = installDirectoryPath;
        this.distributionFilePath = distributionFilePath;
    }

    /**
     * Gets the underlying platform.
     *
     * @return Platform.
     */
    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the path to the directory where the distribution content may be temporarily extracted.
     *
     * @return Path.
     */
    @Nonnull
    public Path getExtractDirectoryPath() {
        return extractDirectoryPath;
    }

    /**
     * Gets the path to the directory where the distribution shall be deployed.
     *
     * @return Path.
     */
    @Nonnull
    public Path getInstallDirectoryPath() {
        return installDirectoryPath;
    }

    /**
     * Gets the path to the distribution archive.
     *
     * @return Path.
     */
    @Nonnull
    public Path getDistributionFilePath() {
        return distributionFilePath;
    }
}
