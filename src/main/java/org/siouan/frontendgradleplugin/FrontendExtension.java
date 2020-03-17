package org.siouan.frontendgradleplugin;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

/**
 * Extension providing configuration properties for frontend tasks.
 */
public class FrontendExtension {

    /**
     * Directory where the 'package.json' file is located.
     */
    private final Property<File> packageJsonDirectory;

    /**
     * Whether a Yarn distribution shall be downloaded and installed.
     */
    private final Property<Boolean> yarnEnabled;

    /**
     * Version of the Node distribution to download.
     */
    private final Property<String> nodeVersion;

    /**
     * Directory where the Node distribution shall be installed.
     */
    private final DirectoryProperty nodeInstallDirectory;

    /**
     * URL to download the Node distribution.
     */
    private final Property<String> nodeDistributionUrl;

    /**
     * Version of the distribution to download.
     */
    private final Property<String> yarnVersion;

    /**
     * Directory where the distribution shall be installed.
     */
    private final DirectoryProperty yarnInstallDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> yarnDistributionUrl;

    /**
     * The NPM/Yarn script to execute to install frontend dependencies.
     */
    private final Property<String> installScript;

    /**
     * The NPM/Yarn script to execute to clean frontend resources.
     */
    private final Property<String> cleanScript;

    /**
     * The NPM/Yarn script to execute to assemble frontend artifacts.
     */
    private final Property<String> assembleScript;

    /**
     * The NPM/Yarn script to execute to check the frontend.
     */
    private final Property<String> checkScript;

    public FrontendExtension(final Project project) {
        packageJsonDirectory = project.getObjects().property(File.class);
        nodeVersion = project.getObjects().property(String.class);
        nodeInstallDirectory = project.getObjects().directoryProperty();
        nodeDistributionUrl = project.getObjects().property(String.class);
        yarnEnabled = project.getObjects().property(Boolean.class);
        yarnVersion = project.getObjects().property(String.class);
        yarnInstallDirectory = project.getObjects().directoryProperty();
        yarnDistributionUrl = project.getObjects().property(String.class);
        installScript = project.getObjects().property(String.class);
        cleanScript = project.getObjects().property(String.class);
        assembleScript = project.getObjects().property(String.class);
        checkScript = project.getObjects().property(String.class);
    }

    public Property<File> getPackageJsonDirectory() {
        return packageJsonDirectory;
    }

    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    public DirectoryProperty getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    public Property<String> getNodeDistributionUrl() {
        return nodeDistributionUrl;
    }

    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    public DirectoryProperty getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    public Property<String> getYarnDistributionUrl() {
        return yarnDistributionUrl;
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
}
