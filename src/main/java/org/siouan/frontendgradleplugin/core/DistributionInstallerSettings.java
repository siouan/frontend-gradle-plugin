package org.siouan.frontendgradleplugin.core;

import java.io.File;
import java.util.Optional;

import org.gradle.api.Task;

/**
 * Settings for the distribution installer.
 *
 * @since 1.1.2
 */
public class DistributionInstallerSettings {

    private final Task task;

    private final String osName;

    /**
     * Directory where the distribution shall be installed.
     */
    private final File installDirectory;

    /**
     * Resolver of the distribution download URL.
     */
    private final DistributionUrlResolver urlResolver;

    /**
     * Validator of the distribution.
     */
    private final DistributionValidator validator;

    /**
     * Listener called once the distribution is installed.
     */
    private final DistributionPostInstallAction postInstallAction;

    public DistributionInstallerSettings(final Task task, final String osName,
        final DistributionUrlResolver urlResolver, final DistributionValidator validator, final File installDirectory,
        final DistributionPostInstallAction postInstallAction) {
        this.task = task;
        this.osName = osName;
        this.urlResolver = urlResolver;
        this.validator = validator;
        this.installDirectory = installDirectory;
        this.postInstallAction = postInstallAction;
    }

    public Task getTask() {
        return task;
    }

    public String getOsName() {
        return osName;
    }

    public File getInstallDirectory() {
        return installDirectory;
    }

    public DistributionUrlResolver getUrlResolver() {
        return urlResolver;
    }

    public Optional<DistributionValidator> getValidator() {
        return Optional.ofNullable(validator);
    }

    public Optional<DistributionPostInstallAction> getPostInstallAction() {
        return Optional.ofNullable(postInstallAction);
    }
}
