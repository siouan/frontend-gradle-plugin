package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

/**
 * Extension providing configuration properties for frontend tasks.
 */
public class FrontendExtension {

    /**
     * Whether a Node distribution is provided.
     *
     * @since 2.0.0
     */
    private final Property<Boolean> nodeDistributionProvided;

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final DirectoryProperty nodeInstallDirectory;

    /**
     * URL root part to build the exact URL to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlPathPattern;

    /**
     * Username to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Node.js distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionServerPassword;

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    private final Property<Boolean> yarnEnabled;

    /**
     * Whether a Yarn distribution is provided.
     *
     * @since 2.0.0
     */
    private final Property<Boolean> yarnDistributionProvided;

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    /**
     * Directory where the distribution shall be installed.
     */
    private final DirectoryProperty yarnInstallDirectory;

    /**
     * URL root part to build the exact URL to download the Yarn distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionUrlRoot;

    /**
     * Trailing path pattern to build the exact URL to download the Yarn distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionUrlPathPattern;

    /**
     * Username to authenticate on the server providing Yarn distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionServerUsername;

    /**
     * Password to authenticate on the server providing Yarn distributions.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionServerPassword;

    /**
     * The npm/Yarn script installing frontend dependencies.
     */
    private final Property<String> installScript;

    /**
     * The npm/Yarn script cleaning frontend resources.
     */
    private final Property<String> cleanScript;

    /**
     * The npm/Yarn script assembling frontend artifacts.
     */
    private final Property<String> assembleScript;

    /**
     * The npm/Yarn script to execute to check the frontend.
     */
    private final Property<String> checkScript;

    /**
     * The npm/Yarn script publishing frontend artifacts.
     */
    private final Property<String> publishScript;

    /**
     * Directory where the 'package.json' file is located.
     */
    private final Property<File> packageJsonDirectory;

    /**
     * Proxy host used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyHost;

    /**
     * Proxy port used to download resources with HTTP protocol.
     *
     * @since 5.0.0
     */
    private final Property<Integer> httpProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTP requests.
     *
     * @since 5.0.0
     */
    private final Property<String> httpProxyPassword;

    /**
     * Proxy host used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    private final Property<String> httpsProxyHost;

    /**
     * Proxy port used to download resources with HTTPS protocol.
     *
     * @since 2.1.0
     */
    private final Property<Integer> httpsProxyPort;

    /**
     * Username to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    private final Property<String> httpsProxyUsername;

    /**
     * Password to authenticate on the proxy server for HTTPS requests.
     *
     * @since 3.0.0
     */
    private final Property<String> httpsProxyPassword;

    /**
     * Whether verbose mode is enabled.
     *
     * @since 2.0.0
     */
    private final Property<Boolean> verboseModeEnabled;

    public FrontendExtension(final Project project) {
        nodeDistributionProvided = project.getObjects().property(Boolean.class);
        nodeVersion = project.getObjects().property(String.class);
        nodeInstallDirectory = project.getObjects().directoryProperty();
        nodeDistributionUrlRoot = project.getObjects().property(String.class);
        nodeDistributionUrlPathPattern = project.getObjects().property(String.class);
        nodeDistributionServerUsername = project.getObjects().property(String.class);
        nodeDistributionServerPassword = project.getObjects().property(String.class);
        yarnDistributionProvided = project.getObjects().property(Boolean.class);
        yarnEnabled = project.getObjects().property(Boolean.class);
        yarnVersion = project.getObjects().property(String.class);
        yarnInstallDirectory = project.getObjects().directoryProperty();
        yarnDistributionUrlRoot = project.getObjects().property(String.class);
        yarnDistributionUrlPathPattern = project.getObjects().property(String.class);
        yarnDistributionServerUsername = project.getObjects().property(String.class);
        yarnDistributionServerPassword = project.getObjects().property(String.class);
        installScript = project.getObjects().property(String.class);
        cleanScript = project.getObjects().property(String.class);
        assembleScript = project.getObjects().property(String.class);
        checkScript = project.getObjects().property(String.class);
        publishScript = project.getObjects().property(String.class);
        packageJsonDirectory = project.getObjects().property(File.class);
        httpProxyHost = project.getObjects().property(String.class);
        httpProxyPort = project.getObjects().property(Integer.class);
        httpProxyUsername = project.getObjects().property(String.class);
        httpProxyPassword = project.getObjects().property(String.class);
        httpsProxyHost = project.getObjects().property(String.class);
        httpsProxyPort = project.getObjects().property(Integer.class);
        httpsProxyUsername = project.getObjects().property(String.class);
        httpsProxyPassword = project.getObjects().property(String.class);
        verboseModeEnabled = project.getObjects().property(Boolean.class);
    }

    public Property<Boolean> getNodeDistributionProvided() {
        return nodeDistributionProvided;
    }

    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    public Property<String> getNodeDistributionUrlRoot() {
        return nodeDistributionUrlRoot;
    }

    public Property<String> getNodeDistributionUrlPathPattern() {
        return nodeDistributionUrlPathPattern;
    }

    public Property<String> getNodeDistributionServerUsername() {
        return nodeDistributionServerUsername;
    }

    public Property<String> getNodeDistributionServerPassword() {
        return nodeDistributionServerPassword;
    }

    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    public Property<Boolean> getYarnDistributionProvided() {
        return yarnDistributionProvided;
    }

    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    public Property<String> getYarnDistributionUrlRoot() {
        return yarnDistributionUrlRoot;
    }

    public Property<String> getYarnDistributionUrlPathPattern() {
        return yarnDistributionUrlPathPattern;
    }

    public Property<String> getYarnDistributionServerUsername() {
        return yarnDistributionServerUsername;
    }

    public Property<String> getYarnDistributionServerPassword() {
        return yarnDistributionServerPassword;
    }

    public Property<String> getInstallScript() {
        return installScript;
    }

    public Property<String> getCleanScript() {
        return cleanScript;
    }

    public Property<String> getAssembleScript() {
        return assembleScript;
    }

    public Property<String> getCheckScript() {
        return checkScript;
    }

    public Property<String> getPublishScript() {
        return publishScript;
    }

    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    public Property<String> getHttpProxyHost() {
        return httpProxyHost;
    }

    public Property<Integer> getHttpProxyPort() {
        return httpProxyPort;
    }

    public Property<String> getHttpProxyUsername() {
        return httpProxyUsername;
    }

    public Property<String> getHttpProxyPassword() {
        return httpProxyPassword;
    }

    public Property<String> getHttpsProxyHost() {
        return httpsProxyHost;
    }

    public Property<Integer> getHttpsProxyPort() {
        return httpsProxyPort;
    }

    public Property<String> getHttpsProxyUsername() {
        return httpsProxyUsername;
    }

    public Property<String> getHttpsProxyPassword() {
        return httpsProxyPassword;
    }

    public Property<Boolean> getVerboseModeEnabled() {
        return verboseModeEnabled;
    }
}
