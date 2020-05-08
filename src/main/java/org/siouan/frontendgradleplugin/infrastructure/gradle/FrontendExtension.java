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
     * URL pattern to download the Node.js distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> nodeDistributionUrlPattern;

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
     * URL pattern to download the Yarn distribution.
     *
     * @since 3.0.0
     */
    private final Property<String> yarnDistributionUrlPattern;

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
     * Proxy host used to download resources.
     *
     * @since 2.1.0
     */
    private final Property<String> proxyHost;

    /**
     * Proxy port used to download resources.
     *
     * @since 2.1.0
     */
    private final Property<Integer> proxyPort;

    /**
     * Username to authenticate on the proxy server.
     *
     * @since 3.0.0
     */
    private final Property<String> proxyUsername;

    /**
     * Password to authenticate on the proxy server.
     *
     * @since 3.0.0
     */
    private final Property<String> proxyPassword;

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
        nodeDistributionUrlPattern = project.getObjects().property(String.class);
        nodeDistributionServerUsername = project.getObjects().property(String.class);
        nodeDistributionServerPassword = project.getObjects().property(String.class);
        yarnDistributionProvided = project.getObjects().property(Boolean.class);
        yarnEnabled = project.getObjects().property(Boolean.class);
        yarnVersion = project.getObjects().property(String.class);
        yarnInstallDirectory = project.getObjects().directoryProperty();
        yarnDistributionUrlPattern = project.getObjects().property(String.class);
        yarnDistributionServerUsername = project.getObjects().property(String.class);
        yarnDistributionServerPassword = project.getObjects().property(String.class);
        installScript = project.getObjects().property(String.class);
        cleanScript = project.getObjects().property(String.class);
        assembleScript = project.getObjects().property(String.class);
        checkScript = project.getObjects().property(String.class);
        publishScript = project.getObjects().property(String.class);
        packageJsonDirectory = project.getObjects().property(File.class);
        proxyHost = project.getObjects().property(String.class);
        proxyPort = project.getObjects().property(Integer.class);
        proxyUsername = project.getObjects().property(String.class);
        proxyPassword = project.getObjects().property(String.class);
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

    public Property<String> getNodeDistributionUrlPattern() {
        return nodeDistributionUrlPattern;
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

    public Property<String> getYarnDistributionUrlPattern() {
        return yarnDistributionUrlPattern;
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

    public Property<String> getProxyHost() {
        return proxyHost;
    }

    public Property<Integer> getProxyPort() {
        return proxyPort;
    }

    public Property<String> getProxyUsername() {
        return proxyUsername;
    }

    public Property<String> getProxyPassword() {
        return proxyPassword;
    }

    public Property<Boolean> getVerboseModeEnabled() {
        return verboseModeEnabled;
    }
}
