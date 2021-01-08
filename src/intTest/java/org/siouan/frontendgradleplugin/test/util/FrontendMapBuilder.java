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

    private String nodeDistributionUrlRoot;

    private String nodeDistributionUrlPathPattern;

    private String nodeDistributionServerUsername;

    private String nodeDistributionServerPassword;

    private Path nodeInstallDirectory;

    private Boolean yarnEnabled;

    private Boolean yarnDistributionProvided;

    private String yarnVersion;

    private String yarnDistributionUrlRoot;

    private String yarnDistributionUrlPathPattern;

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

    private String httpProxyHost;

    private Integer httpProxyPort;

    private String httpProxyUsername;

    private String httpProxyPassword;

    private String httpsProxyHost;

    private Integer httpsProxyPort;

    private String httpsProxyUsername;

    private String httpsProxyPassword;

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
    public FrontendMapBuilder nodeDistributionUrlRoot(@Nullable final String nodeDistributionUrlRoot) {
        this.nodeDistributionUrlRoot = nodeDistributionUrlRoot;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionUrlRoot(@Nullable final URL nodeDistributionUrlRoot) {
        return nodeDistributionUrlRoot(Objects.toString(nodeDistributionUrlRoot, null));
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionUrlPathPattern(@Nullable final String nodeDistributionUrlPathPattern) {
        this.nodeDistributionUrlPathPattern = nodeDistributionUrlPathPattern;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder nodeDistributionUrl(@Nonnull final URL nodeDistributionUrl) {
        final String fileName = nodeDistributionUrl.getFile();
        final String nodeDistributionUrlAsString = nodeDistributionUrl.toString();
        return nodeDistributionUrlRoot(nodeDistributionUrlAsString.substring(0,
            nodeDistributionUrlAsString.indexOf(fileName))).nodeDistributionUrlPathPattern(fileName);
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
    public FrontendMapBuilder yarnDistributionUrlRoot(@Nullable final String yarnDistributionUrlRoot) {
        this.yarnDistributionUrlRoot = yarnDistributionUrlRoot;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionUrlRoot(@Nullable final URL yarnDistributionUrlRoot) {
        return yarnDistributionUrlRoot(Objects.toString(yarnDistributionUrlRoot, null));
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionUrlPathPattern(@Nullable final String yarnDistributionUrlPathPattern) {
        this.yarnDistributionUrlPathPattern = yarnDistributionUrlPathPattern;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder yarnDistributionUrl(@Nonnull final URL yarnDistributionUrl) {
        final String fileName = yarnDistributionUrl.getFile();
        final String yarnDistributionUrlAsString = yarnDistributionUrl.toString();
        return yarnDistributionUrlRoot(yarnDistributionUrlAsString.substring(0,
            yarnDistributionUrlAsString.indexOf(fileName))).yarnDistributionUrlPathPattern(fileName);
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
    public FrontendMapBuilder httpProxyHost(@Nullable final String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpProxyPort(@Nullable final Integer httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpProxyUsername(@Nullable final String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpProxyPassword(@Nullable final String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpsProxyHost(@Nullable final String httpsProxyHost) {
        this.httpsProxyHost = httpsProxyHost;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpsProxyPort(@Nullable final Integer httpsProxyPort) {
        this.httpsProxyPort = httpsProxyPort;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpsProxyUsername(@Nullable final String httpsProxyUsername) {
        this.httpsProxyUsername = httpsProxyUsername;
        return this;
    }

    @Nonnull
    public FrontendMapBuilder httpsProxyPassword(@Nullable final String httpsProxyPassword) {
        this.httpsProxyPassword = httpsProxyPassword;
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
        if (nodeDistributionUrlRoot != null) {
            properties.put("nodeDistributionUrlRoot", nodeDistributionUrlRoot);
        }
        if (nodeDistributionUrlPathPattern != null) {
            properties.put("nodeDistributionUrlPathPattern", nodeDistributionUrlPathPattern);
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
        if (yarnDistributionUrlRoot != null) {
            properties.put("yarnDistributionUrlRoot", yarnDistributionUrlRoot);
        }
        if (yarnDistributionUrlPathPattern != null) {
            properties.put("yarnDistributionUrlPathPattern", yarnDistributionUrlPathPattern);
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
        if (httpProxyHost != null) {
            properties.put("httpProxyHost", httpProxyHost);
        }
        if (httpProxyPort != null) {
            properties.put("httpProxyPort", httpProxyPort);
        }
        if (httpProxyUsername != null) {
            properties.put("httpProxyUsername", httpProxyUsername);
        }
        if (httpProxyPassword != null) {
            properties.put("httpProxyPassword", httpProxyPassword);
        }
        if (httpsProxyHost != null) {
            properties.put("httpsProxyHost", httpsProxyHost);
        }
        if (httpsProxyPort != null) {
            properties.put("httpsProxyPort", httpsProxyPort);
        }
        if (httpsProxyUsername != null) {
            properties.put("httpsProxyUsername", httpsProxyUsername);
        }
        if (httpsProxyPassword != null) {
            properties.put("httpsProxyPassword", httpsProxyPassword);
        }
        return unmodifiableMap(properties);
    }
}
