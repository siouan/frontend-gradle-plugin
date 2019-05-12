package org.siouan.frontendgradleplugin.core;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.Task;

/**
 * Validates a Node distribution by comparing its SHA-256 hash against the one officially published.
 */
public class NodeDistributionValidator extends AbstractTaskJob implements DistributionValidator {

    /**
     * Downloader used to get the file containing all checksums for a given distribution.
     */
    private final Downloader downloader;

    /**
     * Reader that gives the checksum of the distribution from a file.
     */
    private final NodeDistributionChecksumReader checksumReader;

    /**
     * Hasher used to check the distribution integrity.
     */
    private final FileHasher fileHasher;

    /**
     * Install directory.
     */
    private final Path installDirectory;

    /**
     * Builds a validator of a Node distribution.
     *
     * @param task Refering task.
     * @param downloader Downloader.
     * @param checksumReader Reader of checksum's file.
     * @param fileHasher Hasher.
     * @param installDirectory Install directory.
     */
    public NodeDistributionValidator(final Task task, final Downloader downloader,
        final NodeDistributionChecksumReader checksumReader, final FileHasher fileHasher, final Path installDirectory) {
        super(task);
        this.downloader = downloader;
        this.checksumReader = checksumReader;
        this.fileHasher = fileHasher;
        this.installDirectory = installDirectory;
    }

    @Override
    public void validate(final URL distributionUrl, final Path distributionFile) throws DistributionValidatorException {
        final String distributionUrlAsString = distributionUrl.toString();

        // Resolve the URL to download the checksum file
        final String checksumUrlAsString =
            distributionUrlAsString.substring(0, distributionUrlAsString.lastIndexOf('/') + 1) + "SHASUMS256.txt";
        final Path checksumFile = installDirectory
            .resolve(checksumUrlAsString.substring(checksumUrlAsString.lastIndexOf('/') + 1));
        try {
            final URL checksumUrl = URI.create(checksumUrlAsString).toURL();

            // Download the checksum file
            logLifecycle("Downloading checksums at '" + checksumUrlAsString + "'");
            downloader.download(checksumUrl, checksumFile);

            // Verify the distribution integrity
            logLifecycle("Verifying distribution integrity");
            final String expectedHash = checksumReader
                .readHash(checksumFile, distributionFile.getFileName().toString());
            if (!fileHasher.hash(distributionFile).equals(expectedHash)) {
                throw new DistributionValidatorException("Distribution corrupted: invalid checksum");
            }
        } catch (final IOException | DownloadException | NodeDistributionChecksumNotFoundException e) {
            throw new DistributionValidatorException(e);
        } finally {
            try {
                Files.deleteIfExists(checksumFile);
            } catch (final IOException e) {
                logWarn("Checksum file could not be deleted: '" + checksumFile + '\'', e);
            }
        }
    }
}
