package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;

/**
 * Distribution definition.
 *
 * @since 2.0.0
 */
public class DistributionDefinition {

    private final Platform platform;

    private final String version;

    private final String downloadUrlRoot;

    private final String downloadUrlPathPattern;

    /**
     * Builds a definition.
     *
     * @param platform Underlying platform.
     * @param version Version of distribution.
     * @param downloadUrlRoot URL root part to build the exact URL to download the distribution.
     * @param downloadUrlPathPattern Trailing path pattern to build the exact URL to download the distribution.
     */
    public DistributionDefinition(@Nonnull final Platform platform, @Nonnull final String version,
        @Nonnull final String downloadUrlRoot, @Nonnull final String downloadUrlPathPattern) {
        this.platform = platform;
        this.version = version;
        this.downloadUrlRoot = downloadUrlRoot;
        this.downloadUrlPathPattern = downloadUrlPathPattern;
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
     * Gets the URL root part to build the exact URL to download the distribution.
     *
     * @return URL root.
     */
    @Nonnull
    public String getDownloadUrlRoot() {
        return downloadUrlRoot;
    }

    /**
     * Gets the trailing path pattern to build the exact URL to download the distribution.
     *
     * @return Path pattern.
     */
    @Nonnull
    public String getDownloadUrlPathPattern() {
        return downloadUrlPathPattern;
    }
}
