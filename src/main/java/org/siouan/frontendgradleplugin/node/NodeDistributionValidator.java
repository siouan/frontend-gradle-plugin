package org.siouan.frontendgradleplugin.node;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

import org.gradle.api.Task;
import org.siouan.frontendgradleplugin.DownloadException;
import org.siouan.frontendgradleplugin.Downloader;
import org.siouan.frontendgradleplugin.FileHasher;
import org.siouan.frontendgradleplugin.job.AbstractJob;
import org.siouan.frontendgradleplugin.job.DistributionValidator;
import org.siouan.frontendgradleplugin.job.InvalidDistributionException;

/**
 * Validates a Node distribution by comparing its SHA-256 hash against the one officially published.
 */
public class NodeDistributionValidator extends AbstractJob implements DistributionValidator {

    private final Downloader downloader;

    private final NodeDistributionChecksumReader checksumReader;

    private final FileHasher fileHasher;

    private final File installDirectory;

    public NodeDistributionValidator(final Task task, final Downloader downloader,
        final NodeDistributionChecksumReader checksumReader, final FileHasher fileHasher, final File installDirectory) {
        super(task);
        this.downloader = downloader;
        this.checksumReader = checksumReader;
        this.fileHasher = fileHasher;
        this.installDirectory = installDirectory;
    }

    public void validate(final URL distributionUrl, final File distributionFile) throws InvalidDistributionException {
        final String distributionUrlAsString = distributionUrl.toString();

        // Resolve the URL to download the checksum file
        final String checksumUrlAsString =
            distributionUrlAsString.substring(0, distributionUrlAsString.lastIndexOf('/') + 1) + "SHASUMS256.txt";
        final File checksumFile = new File(installDirectory,
            checksumUrlAsString.substring(checksumUrlAsString.lastIndexOf('/') + 1));
        try {
            final URL checksumUrl = URI.create(checksumUrlAsString).toURL();

            // Download the checksum file
            logLifecycle("Downloading checksums at '" + checksumUrlAsString + "'");
            downloader.download(checksumUrl, checksumFile);

            // Verify the distribution integrity
            logLifecycle("Verifying distribution integrity");
            final String expectedHash = checksumReader.readHash(checksumFile, distributionFile.getName());
            if (!fileHasher.hash(distributionFile).equals(expectedHash)) {
                throw new InvalidDistributionException("Distribution corrupted: invalid checksum");
            }
        } catch (final IOException | DownloadException | NodeDistributionChecksumNotFoundException e) {
            throw new InvalidDistributionException(e);
        } finally {
            try {
                Files.deleteIfExists(checksumFile.toPath());
            } catch (final IOException e) {
                logWarn("Checksum file could not be deleted: '" + checksumFile.getAbsolutePath() + '\'', e);
            }
        }
    }
}
