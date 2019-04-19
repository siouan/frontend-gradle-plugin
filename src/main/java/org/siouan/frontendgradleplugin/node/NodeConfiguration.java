package org.siouan.frontendgradleplugin.node;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputDirectory;

/**
 * Extension providing configuration properties to manage a Node distribution.
 */
public class NodeConfiguration {

    /**
     * Version of the distribution to download.
     */
    private final Property<String> version;

    /**
     * Directory where the distribution shall be installed.
     */
    private final Property<File> installDirectory;

    /**
     * URL to download the distribution.
     */
    private final Property<String> distributionUrl;

    public NodeConfiguration(final Project project) {
        version = project.getObjects().property(String.class);
        installDirectory = project.getObjects().property(File.class);
        distributionUrl = project.getObjects().property(String.class);
    }

    @Input
    public Property<String> getVersion() {
        return version;
    }

    @OutputDirectory
    public Property<File> getInstallDirectory() {
        return installDirectory;
    }

    @Input
    public Property<String> getDistributionUrl() {
        return distributionUrl;
    }
}
