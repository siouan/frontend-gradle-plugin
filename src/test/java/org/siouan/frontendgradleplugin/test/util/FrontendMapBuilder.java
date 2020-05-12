package org.siouan.frontendgradleplugin.test.util;

import static java.util.Collections.unmodifiableMap;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A builder of a map of frontend properties.
 */
public final class FrontendMapBuilder {

    private Boolean nodeDistributionProvided;

    private String nodeVersion;

    private String nodeDistributionUrlPattern;

    private String nodeDistributionServerUsername;

    private String nodeDistributionServerPassword;

    private Path nodeInstallDirectory;

    private Boolean yarnEnabled;

    private Boolean yarnDistributionProvided;

    private String yarnVersion;

    private String yarnDistributionUrlPattern;

    private String yarnDistributionServerUsername;

    private String yarnDistributionServerPassword;

    private Path yarnInstallDirectory;

    private String installScript;

    private String cleanScript;

    private String assembleScript;

    private String checkScript;

    private String publishScript;

    private Path packageJsonDirectory;

    private Boolean verboseModeEnabled;

    private String proxyHost;

    private Integer proxyPort;

    private String proxyUsername;

    private String proxyPassword;

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
    public FrontendMapBuilder nodeDistributionUrlPattern(@Nullable final String nodeDistributionUrlPattern) {
        this.nodeDistributionUrlPattern = nodeDistributionUrlPattern;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionUrlPattern(@Nullable final URL nodeDistributionUrlPattern) {
        return nodeDistributionUrlPattern(Objects.toString(nodeDistributionUrlPattern, null));
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionServerUsername(@Nullable final String nodeDistributionServerUsername) {
        this.nodeDistributionServerUsername = nodeDistributionServerUsername;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionServerPassword(@Nullable final String nodeDistributionServerPassword) {
        this.nodeDistributionServerPassword = nodeDistributionServerPassword;
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
    public FrontendMapBuilder yarnDistributionUrlPattern(@Nullable final String yarnDistributionUrlPattern) {
        this.yarnDistributionUrlPattern = yarnDistributionUrlPattern;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionUrlPattern(@Nullable final URL yarnDistributionUrlPattern) {
        return yarnDistributionUrlPattern(Objects.toString(yarnDistributionUrlPattern, null));
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionServerUsername(@Nullable final String yarnDistributionServerUsername) {
        this.yarnDistributionServerUsername = yarnDistributionServerUsername;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionServerPassword(@Nullable final String yarnDistributionServerPassword) {
        this.yarnDistributionServerPassword = yarnDistributionServerPassword;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnInstallDirectory(@Nullable final Path yarnInstallDirectory) {
        this.yarnInstallDirectory = yarnInstallDirectory;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder installScript(@Nullable final String installScript) {
        this.installScript = installScript;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder cleanScript(@Nullable final String cleanScript) {
        this.cleanScript = cleanScript;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder assembleScript(@Nullable final String assembleScript) {
        this.assembleScript = assembleScript;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder checkScript(@Nullable final String checkScript) {
        this.checkScript = checkScript;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder publishScript(@Nullable final String publishScript) {
        this.publishScript = publishScript;
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
    public FrontendMapBuilder proxyHost(@Nullable final String proxyHost) {
        this.proxyHost = proxyHost;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder proxyPort(@Nullable final Integer proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder proxyUsername(@Nullable final String proxyUsername) {
        this.proxyUsername = proxyUsername;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder proxyPassword(@Nullable final String proxyPassword) {
        this.proxyPassword = proxyPassword;
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
        if (nodeDistributionUrlPattern != null) {
            properties.put("nodeDistributionUrlPattern", nodeDistributionUrlPattern);
        }
        if (nodeDistributionServerUsername != null) {
            properties.put("nodeDistributionServerUsername", nodeDistributionServerUsername);
        }
        if (nodeDistributionServerPassword != null) {
            properties.put("nodeDistributionServerPassword", nodeDistributionServerPassword);
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
        if (yarnDistributionUrlPattern != null) {
            properties.put("yarnDistributionUrlPattern", yarnDistributionUrlPattern);
        }
        if (yarnDistributionServerUsername != null) {
            properties.put("yarnDistributionServerUsername", yarnDistributionServerUsername);
        }
        if (yarnDistributionServerPassword != null) {
            properties.put("yarnDistributionServerPassword", yarnDistributionServerPassword);
        }
        if (yarnInstallDirectory != null) {
            properties.put("yarnInstallDirectory", yarnInstallDirectory);
        }
        if (installScript != null) {
            properties.put("installScript", installScript);
        }
        if (cleanScript != null) {
            properties.put("cleanScript", cleanScript);
        }
        if (assembleScript != null) {
            properties.put("assembleScript", assembleScript);
        }
        if (checkScript != null) {
            properties.put("checkScript", checkScript);
        }
        if (publishScript != null) {
            properties.put("publishScript", publishScript);
        }
        if (packageJsonDirectory != null) {
            properties.put("packageJsonDirectory", packageJsonDirectory);
        }
        if (verboseModeEnabled != null) {
            properties.put("verboseModeEnabled", verboseModeEnabled);
        }
        if (proxyHost != null) {
            properties.put("proxyHost", proxyHost);
        }
        if (proxyPort != null) {
            properties.put("proxyPort", proxyPort);
        }
        if (proxyUsername != null) {
            properties.put("proxyUsername", proxyUsername);
        }
        if (proxyPassword != null) {
            properties.put("proxyPassword", proxyPassword);
        }
        return unmodifiableMap(properties);
    }
}