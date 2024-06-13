package org.siouan.frontendgradleplugin.test;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

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

    private String corepackVersion;

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

    public FrontendMapBuilder nodeDistributionProvided(final Boolean nodeDistributionProvided) {
        this.nodeDistributionProvided = nodeDistributionProvided;
        return this;
    }

    public FrontendMapBuilder nodeVersion(final String nodeVersion) {
        this.nodeVersion = nodeVersion;
        return this;
    }

    public FrontendMapBuilder nodeDistributionUrlRoot(final String nodeDistributionUrlRoot) {
        this.nodeDistributionUrlRoot = nodeDistributionUrlRoot;
        return this;
    }

    public FrontendMapBuilder nodeDistributionUrlPathPattern(final String nodeDistributionUrlPathPattern) {
        this.nodeDistributionUrlPathPattern = nodeDistributionUrlPathPattern;
        return this;
    }

    public FrontendMapBuilder nodeDistributionUrl(final Path nodeDistributionFilePath) {
        final String fileName = nodeDistributionFilePath.getFileName().toString();
        final String nodeDistributionUrlAsString = nodeDistributionFilePath.getParent().toUri().toString();
        return nodeDistributionUrlRoot(nodeDistributionUrlAsString).nodeDistributionUrlPathPattern(fileName);
    }

    public FrontendMapBuilder nodeDistributionServerUsername(final String nodeDistributionServerUsername) {
        this.nodeDistributionServerUsername = nodeDistributionServerUsername;
        return this;
    }

    public FrontendMapBuilder nodeDistributionServerPassword(final String nodeDistributionServerPassword) {
        this.nodeDistributionServerPassword = nodeDistributionServerPassword;
        return this;
    }

    public FrontendMapBuilder nodeInstallDirectory(final Path nodeInstallDirectory) {
        this.nodeInstallDirectory = nodeInstallDirectory;
        return this;
    }

    public FrontendMapBuilder corepackVersion(final String corepackVersion) {
        this.corepackVersion = corepackVersion;
        return this;
    }

    public FrontendMapBuilder installScript(final String installScript) {
        this.installScript = installScript;
        return this;
    }

    public FrontendMapBuilder cleanScript(final String cleanScript) {
        this.cleanScript = cleanScript;
        return this;
    }

    public FrontendMapBuilder assembleScript(final String assembleScript) {
        this.assembleScript = assembleScript;
        return this;
    }

    public FrontendMapBuilder checkScript(final String checkScript) {
        this.checkScript = checkScript;
        return this;
    }

    public FrontendMapBuilder publishScript(final String publishScript) {
        this.publishScript = publishScript;
        return this;
    }

    public FrontendMapBuilder packageJsonDirectory(final Path packageJsonDirectory) {
        this.packageJsonDirectory = packageJsonDirectory;
        return this;
    }

    public FrontendMapBuilder verboseModeEnabled(final Boolean verboseModeEnabled) {
        this.verboseModeEnabled = verboseModeEnabled;
        return this;
    }

    public FrontendMapBuilder httpProxyHost(final String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
        return this;
    }

    public FrontendMapBuilder httpProxyPort(final Integer httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
        return this;
    }

    public FrontendMapBuilder httpProxyUsername(final String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
        return this;
    }

    public FrontendMapBuilder httpProxyPassword(final String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
        return this;
    }

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
        if (corepackVersion != null) {
            properties.put("corepackVersion", corepackVersion);
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
        return Map.copyOf(properties);
    }
}
