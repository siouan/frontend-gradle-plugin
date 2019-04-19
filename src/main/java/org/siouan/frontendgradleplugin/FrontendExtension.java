package org.siouan.frontendgradleplugin;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.options.Option;

/**
 * Extension providing configuration properties to manage frontend build tools.
 */
public class FrontendExtension {

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
    private final Property<File> nodeInstallDirectory;

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
    private final Property<File> yarnInstallDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> yarnDistributionUrl;

    /**
     * The NPM/Yarn myscript to execute to clean frontend resources.
     */
    private final Property<String> cleanScript;

    /**
     * The NPM/Yarn myscript to execute to assemble frontend artifacts.
     */
    private final Property<String> assembleScript;

    /**
     * The NPM/Yarn myscript to execute to check the frontend.
     */
    private final Property<String> checkScript;

    public FrontendExtension(final Project project) {
        yarnEnabled = project.getObjects().property(Boolean.class);
        nodeVersion = project.getObjects().property(String.class);
        nodeInstallDirectory = project.getObjects().property(File.class);
        nodeDistributionUrl = project.getObjects().property(String.class);
        yarnVersion = project.getObjects().property(String.class);
        yarnInstallDirectory = project.getObjects().property(File.class);
        yarnDistributionUrl = project.getObjects().property(String.class);
        cleanScript = project.getObjects().property(String.class);
        assembleScript = project.getObjects().property(String.class);
        checkScript = project.getObjects().property(String.class);
    }

    @Input
    @Optional
    public Property<Boolean> getYarnEnabled() {
        return yarnEnabled;
    }

    @Input
    public Property<String> getNodeVersion() {
        return nodeVersion;
    }

    @OutputDirectory
    @Optional
    public Property<File> getNodeInstallDirectory() {
        return nodeInstallDirectory;
    }

    @Input
    @Optional
    public Property<String> getNodeDistributionUrl() {
        return nodeDistributionUrl;
    }

    @Input
    @Optional
    public Property<String> getYarnVersion() {
        return yarnVersion;
    }

    @OutputDirectory
    @Optional
    public Property<File> getYarnInstallDirectory() {
        return yarnInstallDirectory;
    }

    @Input
    @Optional
    public Property<String> getYarnDistributionUrl() {
        return yarnDistributionUrl;
    }

    @Input
    @Optional
    public Property<String> getCleanScript() {
        return cleanScript;
    }

    @Input
    @Optional
    public Property<String> getAssembleScript() {
        return assembleScript;
    }

    @Input
    @Optional
    public Property<String> getCheckScript() {
        return checkScript;
    }
}
