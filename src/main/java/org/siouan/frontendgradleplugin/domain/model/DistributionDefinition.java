package org.siouan.frontendgradleplugin.domain.model;

import java.net.URL;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
     * URL to download the distribution.
     */
    private final URL downloadUrl;

    /**
     * URL to download the distribution.
     */
    private final String downloadUrlPattern;

    /**
     * Builds a definition.
     *
     * @param platform Underlying platform.
     * @param version Version of distribution.
     * @param downloadUrl URL to download the distribution.
     * @param downloadUrlPattern URL pattern to download the distribution.
     */
    public DistributionDefinition(@Nonnull final Platform platform, @Nonnull final String version,
        @Nullable final URL downloadUrl, @Nullable String downloadUrlPattern) {
        this.platform = platform;
        this.version = version;
        this.downloadUrl = downloadUrl;
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
     * Gets the URL to download the distribution.
     *
     * @return URL.
     */
    @Nullable
    public URL getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Gets the URL pattern to download the distribution.
     *
     * @return URL.
     */
    @Nullable
    public String getDownloadUrlPattern() {
        return downloadUrlPattern;
    }
}
