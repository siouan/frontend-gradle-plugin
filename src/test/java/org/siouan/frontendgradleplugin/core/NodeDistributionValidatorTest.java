package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link NodeDistributionValidator} class.
 */
class NodeDistributionValidatorTest {

    private static final String DISTRIBUTION_URL = "https://nodejs.org/dist/v9.2.4/node-v9.2.4-win-x64.zip";

    private static final String DISTRIBUTION_FILENAME = "distribution.zip";

    @TempDir
    protected File temporaryDirectory;

    @Mock
    private Project project;

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
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(task.getProject()).thenReturn(project);
        when(task.getLogger()).thenReturn(logger);
        final File installDirectory = new File(temporaryDirectory, "install");
        Files.createDirectory(installDirectory.toPath());
        validator = new NodeDistributionValidator(task, downloader, checksumReader, fileHasher, installDirectory);
    }

    @Test
    public void shouldFailWhenChecksumFileDownloadFails() throws DownloadException {
        final Exception expectedException = mock(DownloadException.class);
        doThrow(expectedException).when(downloader).download(any(URL.class), any(File.class));

        assertThatThrownBy(() -> validator
            .validate(URI.create(DISTRIBUTION_URL).toURL(), new File(temporaryDirectory, DISTRIBUTION_FILENAME)))
            .isInstanceOf(InvalidDistributionException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(File.class));
        verifyNoMoreInteractions(downloader);
        verifyZeroInteractions(checksumReader);
        verifyZeroInteractions(fileHasher);
    }

    @Test
    public void shouldFailWhenChecksumFileCannotBeRead()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final Exception expectedException = mock(IOException.class);
        final String distributionFilename = DISTRIBUTION_FILENAME;
        when(checksumReader.readHash(any(File.class), eq(distributionFilename))).thenThrow(expectedException);

        assertThatThrownBy(
            () -> validator.validate(distributionUrl, new File(temporaryDirectory, distributionFilename)))
            .isInstanceOf(InvalidDistributionException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(File.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(File.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verifyZeroInteractions(fileHasher);
    }

    @Test
    public void shouldFailWhenDistributionFileCannotBeHashed()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String hash = "0123456789abcdef";
        when(checksumReader.readHash(any(File.class), eq(distributionFilename))).thenReturn(hash);
        final Exception expectedException = mock(IOException.class);
        final File distributionFile = new File(temporaryDirectory, distributionFilename);
        when(fileHasher.hash(distributionFile)).thenThrow(expectedException);

        assertThatThrownBy(() -> validator.validate(distributionUrl, distributionFile))
            .isInstanceOf(InvalidDistributionException.class).hasCause(expectedException);

        verify(downloader).download(any(URL.class), any(File.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(File.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }

    @Test
    public void shouldFailWhenDistributionFileHashIsInvalid()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(checksumReader.readHash(any(File.class), eq(distributionFilename))).thenReturn(expectedHash);
        final File distributionFile = new File(temporaryDirectory, distributionFilename);
        final String hash = "fedcba98765543210";
        when(fileHasher.hash(distributionFile)).thenReturn(hash);

        assertThatThrownBy(() -> validator.validate(distributionUrl, distributionFile))
            .isInstanceOf(InvalidDistributionException.class).hasNoCause();

        verify(downloader).download(any(URL.class), any(File.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(File.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }

    @Test
    public void shouldReturnWhenDistributionFileIsValid()
        throws DownloadException, IOException, NodeDistributionChecksumNotFoundException, InvalidDistributionException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(checksumReader.readHash(any(File.class), eq(distributionFilename))).thenReturn(expectedHash);
        final File distributionFile = new File(temporaryDirectory, distributionFilename);
        when(fileHasher.hash(distributionFile)).thenReturn(expectedHash);

        validator.validate(distributionUrl, distributionFile);

        verify(downloader).download(any(URL.class), any(File.class));
        verifyNoMoreInteractions(downloader);
        verify(checksumReader).readHash(any(File.class), eq(distributionFilename));
        verifyNoMoreInteractions(checksumReader);
        verify(fileHasher).hash(distributionFile);
        verifyNoMoreInteractions(fileHasher);
    }
}
