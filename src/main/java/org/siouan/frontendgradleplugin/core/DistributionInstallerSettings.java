package org.siouan.frontendgradleplugin.core;

import java.nio.file.Path;
import java.util.Optional;

import org.gradle.api.Task;
import org.siouan.frontendgradleplugin.core.archivers.ArchiverFactory;

/**
 * Settings for the distribution installer.
 *
 * @since 1.1.2
 */
public class DistributionInstallerSettings {

    private final Task task;

    private final String osName;

    private final Path temporaryDirectory;

    /**
     * Directory where the distribution shall be installed.
     */
    private final Path installDirectory;

    /**
     * Resolver of the distribution download URL.
     */
    private final DistributionUrlResolver urlResolver;

    /**
     * Downloader.
     */
    private final Downloader downloader;

    /**
     * Validator of the distribution.
     */
    private final DistributionValidator validator;

    /**
     * Factory of archiver.
     */
    private final ArchiverFactory archiverFactory;

    /**
     * Builds an installer.
     *
     * @param task Related Gradle task.
     * @param osName O/S name.
     * @param temporaryDirectory Directory where the distribution being downloaded will be temporarily stored.
     * @param urlResolver Resolver of the download URL.
     * @param downloader Downloader.
     * @param validator Distribution validator.
     * @param archiverFactory Factory providing archivers.
     * @param installDirectory Install directory.
     */
    public DistributionInstallerSettings(final Task task, final String osName, final Path temporaryDirectory,
        final DistributionUrlResolver urlResolver, final Downloader downloader, final DistributionValidator validator,
        final ArchiverFactory archiverFactory, final Path installDirectory) {
        this.task = task;
        this.osName = osName;
        this.temporaryDirectory = temporaryDirectory;
        this.urlResolver = urlResolver;
        this.downloader = downloader;
        this.validator = validator;
        this.archiverFactory = archiverFactory;
        this.installDirectory = installDirectory;
    }

    /**
     * Gets the related task.
     *
     * @return Task.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Gets the underlying O/S name.
     *
     * @return O/S name.
     */
    public String getOsName() {
        return osName;
    }

    public Path getTemporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * Gets the install directory.
     *
     * @return Install directory.
     */
    public Path getInstallDirectory() {
        return installDirectory;
    }

    /**
     * Gets the resolver of the distribution URL.
     *
     * @return URL.
     */

    public DistributionUrlResolver getUrlResolver() {
        return urlResolver;
    }

    /**
     * Gest the downloader.
     *
     * @return Downloader.
     */
    public Downloader getDownloader() {
        return downloader;
    }

    /**
     * Gets the distribution validator.
     *
     * @return Validator.
     */
    public Optional<DistributionValidator> getValidator() {
        return Optional.ofNullable(validator);
    }

    /**
     * Gets the archiver factory.
     *
     * @return Factory.
     */
    public ArchiverFactory getArchiverFactory() {
        return archiverFactory;
    }
}
