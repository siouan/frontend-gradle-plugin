package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;

/**
 * Distribution definition.
 *
 * @since 2.0.0
 */
public class DistributionDefinition {

    /**
     * Underlying platform.
     */
    private final Platform platform;

    /**
     * Version of distribution.
     */
    private final String version;

    /**
     * URL pattern to download the distribution.
     */
    private final String downloadUrlPattern;

    /**
     * Builds a definition.
     *
     * @param platform Underlying platform.
     * @param version Version of distribution.
     * @param downloadUrlPattern URL pattern to download the distribution.
     */
    public DistributionDefinition(@Nonnull final Platform platform, @Nonnull final String version,
        @Nonnull final String downloadUrlPattern) {
        this.platform = platform;
        this.version = version;
        this.downloadUrlPattern = downloadUrlPattern;
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
     * Gets the version of distribution.
     *
     * @return Version.
     */
    @Nonnull
    public String getVersion() {
        return version;
    }

    /**
     * Gets the URL pattern to download the distribution.
     *
     * @return URL pattern.
     */
    @Nonnull
    public String getDownloadUrlPattern() {
        return downloadUrlPattern;
    }
}
