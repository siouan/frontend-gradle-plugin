package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Distribution properties.
 *
 * @since 1.4.0
 */
public class DistributionProperties {

    private final Platform platform;

    private final String version;

    private final String downloadUrl;

    /**
     * Builds a resolver using the target platform characteristics to determine the appropriate distro (Windows, Linux,
     * i86, x64 compliant...).
     *
     * @param platform Execution platform.
     * @param version Version.
     * @param downloadUrl URL to download the distribution.
     */
    public DistributionProperties(@Nonnull final Platform platform, @Nullable final String version,
        @Nullable final String downloadUrl) {
        if ((version == null) && (downloadUrl == null)) {
            throw new IllegalArgumentException(
                "Either the Node version or a download URL must be configured (see plugin's DSL).");
        }
        this.platform = platform;
        this.version = version;
        this.downloadUrl = downloadUrl;
    }

    @Nonnull
    public Platform getPlatform() {
        return platform;
    }

    @Nullable
    public String getVersion() {
        return version;
    }

    @Nullable
    public String getDownloadUrl() {
        return downloadUrl;
    }
}
