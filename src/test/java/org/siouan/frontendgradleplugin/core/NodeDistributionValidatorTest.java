package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.Task;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link NodeDistributionValidator} class.
 */
@ExtendWith(MockitoExtension.class)
class NodeDistributionValidatorTest {

    private static final String DISTRIBUTION_URL = "https://nodejs.org/dist/v9.2.4/node-v9.2.4-win-x64.zip";

    private static final String DISTRIBUTION_FILENAME = "distribution.zip";

    @TempDir
    Path temporaryDirectory;

    @Mock
    private Logger logger;

    @Mock
    private Task task;

    @Mock
    private Downloader downloader;

    @Mock
    private NodeDistributionChecksumReader checksumReader;

    @Mock
    private FileHasher fileHasher;

    private NodeDistributionValidator validator;

    @BeforeEach
    void setUp() throws IOException {
        when(task.getLogger()).thenReturn(logger);
        final Path installDirectory = temporaryDirectory.resolve("install");
        Files.createDirectory(installDirectory);
        validator = new NodeDistributionValidator(task, LogLevel.LIFECYCLE, downloader, checksumReader, fileHasher,
            installDirectory);
    }

    @Test
    void shouldFailWhenChecksumFileDownloadFails() throws DownloadException {
        final Exception expectedException = mock(DownloadException.class);
        doThrow(expectedException).when(downloader).download(any(URL.class), any(Path.class));

        assertThatThrownBy(() -> validator
            .validate(URI.create(DISTRIBUTION_URL).toURL(), temporaryDirectory.resolve(DISTRIBUTION_FILENAME)))
            .isInstanceOf(DistributionValidatorException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(Path.class));
        verifyNoMoreInteractions(downloader, checksumReader, fileHasher);
    }

    @Test
    void shouldFailWhenChecksumFileCannotBeRead()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final Exception expectedException = mock(IOException.class);
        final String distributionFilename = DISTRIBUTION_FILENAME;
        when(checksumReader.readHash(any(Path.class), eq(distributionFilename))).thenThrow(expectedException);

        assertThatThrownBy(
            () -> validator.validate(distributionUrl, temporaryDirectory.resolve(distributionFilename)))
            .isInstanceOf(DistributionValidatorException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(Path.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader, fileHasher);
    }

    @Test
    void shouldFailWhenDistributionFileCannotBeHashed()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String hash = "0123456789abcdef";
        when(checksumReader.readHash(any(Path.class), eq(distributionFilename))).thenReturn(hash);
        final Exception expectedException = mock(IOException.class);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        when(fileHasher.hash(distributionFile)).thenThrow(expectedException);

        assertThatThrownBy(() -> validator.validate(distributionUrl, distributionFile))
            .isInstanceOf(DistributionValidatorException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(Path.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }

    @Test
    void shouldFailWhenDistributionFileHashIsInvalid()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(checksumReader.readHash(any(Path.class), eq(distributionFilename))).thenReturn(expectedHash);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        final String hash = "fedcba98765543210";
        when(fileHasher.hash(distributionFile)).thenReturn(hash);

        assertThatThrownBy(() -> validator.validate(distributionUrl, distributionFile))
            .isInstanceOf(DistributionValidatorException.class).hasNoCause();

        verify(downloader).download(any(URL.class), any(Path.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }

    @Test
    void shouldReturnWhenDistributionFileIsValid()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException,
        DistributionValidatorException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(checksumReader.readHash(any(Path.class), eq(distributionFilename))).thenReturn(expectedHash);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        when(fileHasher.hash(distributionFile)).thenReturn(expectedHash);

        validator.validate(distributionUrl, distributionFile);

        verify(downloader).download(any(URL.class), any(Path.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }
}
