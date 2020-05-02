package org.siouan.frontendgradleplugin.test.util;

import static java.util.Collections.unmodifiableMap;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A builder of a map of frontend properties.
 */
public final class FrontendMapBuilder {

    private Boolean nodeDistributionProvided;

    private String nodeVersion;

    private URL nodeDistributionUrl;

    private Path nodeInstallDirectory;

    private Boolean yarnEnabled;

    private Boolean yarnDistributionProvided;

    private String yarnVersion;

    private URL yarnDistributionUrl;

    private Path yarnInstallDirectory;

    private Path packageJsonDirectory;

    private Boolean verboseModeEnabled;

    @Nonnull
    public FrontendMapBuilder nodeDistributionProvided(@Nullable final Boolean nodeDistributionProvided) {
        this.nodeDistributionProvided = nodeDistributionProvided;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeVersion(@Nullable final String nodeVersion) {
        this.nodeVersion = nodeVersion;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionUrl(@Nullable final URL nodeDistributionUrl) {
        this.nodeDistributionUrl = nodeDistributionUrl;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeInstallDirectory(@Nullable final Path nodeInstallDirectory) {
        this.nodeInstallDirectory = nodeInstallDirectory;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnEnabled(@Nullable final Boolean yarnEnabled) {
        this.yarnEnabled = yarnEnabled;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionProvided(@Nullable final Boolean yarnDistributionProvided) {
        this.yarnDistributionProvided = yarnDistributionProvided;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnVersion(@Nullable final String yarnVersion) {
        this.yarnVersion = yarnVersion;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionUrl(@Nullable final URL yarnDistributionUrl) {
        this.yarnDistributionUrl = yarnDistributionUrl;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnInstallDirectory(@Nullable final Path yarnInstallDirectory) {
        this.yarnInstallDirectory = yarnInstallDirectory;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder packageJsonDirectory(@Nullable final Path packageJsonDirectory) {
        this.packageJsonDirectory = packageJsonDirectory;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder verboseModeEnabled(@Nullable final Boolean verboseModeEnabled) {
        this.verboseModeEnabled = verboseModeEnabled;
        return this;
    }

    @Nonnull
    public Map<String, Object> toMap() {
        final Map<String, Object> properties = new HashMap<>();
        if (nodeDistributionProvided != null) {
            properties.put("nodeDistributionProvided", nodeDistributionProvided);
        }
        if (nodeVersion != null) {
            properties.put("nodeVersion", nodeVersion);
        }
        if (nodeDistributionUrl != null) {
            properties.put("nodeDistributionUrl", nodeDistributionUrl);
        }
        if (nodeInstallDirectory != null) {
            properties.put("nodeInstallDirectory", nodeInstallDirectory);
        }
        if (yarnEnabled != null) {
            properties.put("yarnEnabled", yarnEnabled);
        }
        if (yarnDistributionProvided != null) {
            properties.put("yarnDistributionProvided", yarnDistributionProvided);
        }
        if (yarnVersion != null) {
            properties.put("yarnVersion", yarnVersion);
        }
        if (yarnDistributionUrl != null) {
            properties.put("yarnDistributionUrl", yarnDistributionUrl);
        }
        if (yarnInstallDirectory != null) {
            properties.put("yarnInstallDirectory", yarnInstallDirectory);
        }
        if (packageJsonDirectory != null) {
            properties.put("packageJsonDirectory", packageJsonDirectory);
        }
        if (verboseModeEnabled != null) {
            properties.put("verboseModeEnabled", verboseModeEnabled);
        }
        return unmodifiableMap(properties);
    }
}
