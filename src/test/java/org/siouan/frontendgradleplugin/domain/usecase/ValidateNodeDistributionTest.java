package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionChecksumNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorProperties;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;

@ExtendWith(MockitoExtension.class)
class ValidateNodeDistributionTest {

    private static final String DISTRIBUTION_URL = "https://nodejs.org/dist/v9.2.4/node-v9.2.4-win-x64.zip";

    private static final String DISTRIBUTION_FILENAME = "distribution.zip";

    @TempDir
    Path temporaryDirectory;

    @Mock
    private DownloadResource downloadResource;

    @Mock
    private ReadNodeDistributionChecksum readNodeDistributionChecksum;

    @Mock
    private HashFile hashFile;

    @Mock
    private org.siouan.frontendgradleplugin.domain.model.Logger logger;

    private ValidateNodeDistribution validator;

    @BeforeEach
    void setUp() throws IOException {
        final Path installDirectory = temporaryDirectory.resolve("install");
        Files.createDirectory(installDirectory);
        validator = new ValidateNodeDistribution(downloadResource, readNodeDistributionChecksum, hashFile, logger);
    }

    @Test
    void shouldFailWhenChecksumFileDownloadFails()
        throws FrontendIOException, MalformedURLException, URISyntaxException {
        final Exception expectedException = mock(FrontendIOException.class);
        final DownloadParameters downloadSettings = new DownloadParameters(null, temporaryDirectory, null);
        doThrow(expectedException).when(downloadResource).execute(downloadSettings);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectory, URI.create(DISTRIBUTION_URL).toURL(),
            temporaryDirectory.resolve(DISTRIBUTION_FILENAME));

        assertThatThrownBy(() -> validator.validate(distributionValidatorProperties))
            .isInstanceOf(DistributionValidatorException.class)
            .hasCause(expectedException);

        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(downloadResource, readNodeDistributionChecksum, hashFile);
    }

    @Test
    void shouldFailWhenChecksumFileCannotBeRead()
        throws FrontendIOException, IOException, NodeDistributionChecksumNotFoundException, URISyntaxException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final DownloadParameters downloadSettings = new DownloadParameters(null, temporaryDirectory, null);
        final Exception expectedException = mock(IOException.class);
        final String distributionFilename = DISTRIBUTION_FILENAME;
        when(readNodeDistributionChecksum.execute(any(Path.class), eq(distributionFilename))).thenThrow(
            expectedException);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectory, distributionUrl, temporaryDirectory.resolve(distributionFilename));

        assertThatThrownBy(() -> validator.validate(distributionValidatorProperties))
            .isInstanceOf(DistributionValidatorException.class)
            .hasCause(expectedException);

        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(downloadResource);
        verify(readNodeDistributionChecksum).execute(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(readNodeDistributionChecksum, hashFile);
    }

    @Test
    void shouldFailWhenDistributionFileCannotBeHashed()
        throws FrontendIOException, IOException, NodeDistributionChecksumNotFoundException, URISyntaxException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final DownloadParameters downloadSettings = new DownloadParameters(null, temporaryDirectory, null);
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String hash = "0123456789abcdef";
        when(readNodeDistributionChecksum.execute(any(Path.class), eq(distributionFilename))).thenReturn(hash);
        final Exception expectedException = mock(IOException.class);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        when(hashFile.execute(distributionFile)).thenThrow(expectedException);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectory, distributionUrl, distributionFile);

        assertThatThrownBy(() -> validator.validate(distributionValidatorProperties))
            .isInstanceOf(DistributionValidatorException.class)
            .hasCause(expectedException);

        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(downloadResource);
        verify(readNodeDistributionChecksum).execute(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(readNodeDistributionChecksum);
        verify(hashFile).execute(distributionFile);
        verifyNoMoreInteractions(hashFile);
    }

    @Test
    void shouldFailWhenDistributionFileHashIsInvalid()
        throws FrontendIOException, IOException, NodeDistributionChecksumNotFoundException, URISyntaxException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final DownloadParameters downloadSettings = new DownloadParameters(null, temporaryDirectory, null);
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionChecksum.execute(any(Path.class), eq(distributionFilename))).thenReturn(expectedHash);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        final String hash = "fedcba98765543210";
        when(hashFile.execute(distributionFile)).thenReturn(hash);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectory, distributionUrl, distributionFile);

        assertThatThrownBy(() -> validator.validate(distributionValidatorProperties))
            .isInstanceOf(DistributionValidatorException.class)
            .hasNoCause();

        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(downloadResource);
        verify(readNodeDistributionChecksum).execute(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(readNodeDistributionChecksum);
        verify(hashFile).execute(distributionFile);
        verifyNoMoreInteractions(hashFile);
    }

    @Test
    void shouldReturnWhenDistributionFileIsValid()
        throws FrontendIOException, IOException, NodeDistributionChecksumNotFoundException,
        DistributionValidatorException, URISyntaxException {
        final DownloadParameters downloadSettings = new DownloadParameters(null, temporaryDirectory, null);
        final URL distributionUrl = URI.create(DISTRIBUTION_URL).toURL();
        final String distributionFilename = DISTRIBUTION_FILENAME;
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionChecksum.execute(any(Path.class), eq(distributionFilename))).thenReturn(expectedHash);
        final Path distributionFile = temporaryDirectory.resolve(distributionFilename);
        when(hashFile.execute(distributionFile)).thenReturn(expectedHash);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectory, distributionUrl, distributionFile);

        validator.validate(distributionValidatorProperties);

        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(downloadResource);
        verify(readNodeDistributionChecksum).execute(any(Path.class), eq(distributionFilename));
        verifyNoMoreInteractions(readNodeDistributionChecksum);
        verify(hashFile).execute(distributionFile);
        verifyNoMoreInteractions(hashFile);
    }
}
