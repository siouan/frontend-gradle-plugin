package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.InvalidNodeDistributionException;
import org.siouan.frontendgradleplugin.domain.exception.NodeDistributionShasumNotFoundException;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.util.DownloadSettingsMatcher;

@ExtendWith(MockitoExtension.class)
class ValidateNodeDistributionTest {

    private static final URL CHECKSUM_URL;

    private static final String DISTRIBUTION_BASE_URL = "https://nodejs.org/dist/v9.2.4/";

    private static final URL DISTRIBUTION_URL;

    private static final String DISTRIBUTION_FILENAME = "distribution.zip";

    private static final Path DISTRIBUTION_FILE_PATH = PathFixture.ANY_PATH.resolve(DISTRIBUTION_FILENAME);

    private static final Proxy PROXY = Proxy.NO_PROXY;

    static {
        try {
            DISTRIBUTION_URL = new URL(DISTRIBUTION_BASE_URL + "node-v9.2.4-win-x64.zip");
            CHECKSUM_URL = new URL(DISTRIBUTION_BASE_URL + ValidateNodeDistribution.SHASUMS_FILENAME);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    private FileManager fileManager;

    @Mock
    private DownloadResource downloadResource;

    @Mock
    private ReadNodeDistributionShasum readNodeDistributionShasum;

    @Mock
    private HashFile hashFile;

    @InjectMocks
    private ValidateNodeDistribution usecase;

    private Path temporaryDirectoryPath;

    @BeforeEach
    void setUp() {
        temporaryDirectoryPath = PathFixture.TMP_PATH;
        usecase = new ValidateNodeDistribution(fileManager, downloadResource, readNodeDistributionShasum, hashFile,
            mock(Logger.class));
    }

    @Test
    void shouldFailWhenShasumsCannotBeDownloaded() throws IOException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        final Exception expectedException = new IOException();
        doThrow(expectedException)
            .when(downloadResource)
            .execute(argThat(new DownloadSettingsMatcher(
                new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        assertThatThrownBy(() -> usecase.execute(distributionValidatorSettings)).isEqualTo(expectedException);

        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void shouldFailWhenShasumsCannotBeRead() throws IOException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        final Exception expectedException = new IOException();
        when(readNodeDistributionShasum.execute(downloadedShasumFilepath, DISTRIBUTION_FILENAME)).thenThrow(
            expectedException);
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        assertThatThrownBy(() -> usecase.execute(distributionValidatorSettings)).isEqualTo(expectedException);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void shouldFailWhenShasumIsNotFound() throws IOException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        when(readNodeDistributionShasum.execute(downloadedShasumFilepath, DISTRIBUTION_FILENAME)).thenReturn(
            Optional.empty());
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        assertThatThrownBy(() -> usecase.execute(distributionValidatorSettings)).isInstanceOf(
            NodeDistributionShasumNotFoundException.class);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void shouldFailWhenDistributionFileCannotBeHashed() throws IOException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        when(readNodeDistributionShasum.execute(downloadedShasumFilepath, DISTRIBUTION_FILENAME)).thenReturn(
            Optional.of("0123456789abcdef"));
        final Exception expectedException = new IOException();
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenThrow(expectedException);
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        assertThatThrownBy(() -> usecase.execute(distributionValidatorSettings)).isEqualTo(expectedException);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void shouldFailWhenDistributionFileHashIsIncorrect() throws IOException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionShasum.execute(downloadedShasumFilepath, DISTRIBUTION_FILENAME)).thenReturn(
            Optional.of(expectedHash));
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenReturn("fedcba98765543210");
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        assertThatThrownBy(() -> usecase.execute(distributionValidatorSettings)).isInstanceOf(
            InvalidNodeDistributionException.class);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void shouldReturnWhenDistributionFileIsValid()
        throws IOException, NodeDistributionShasumNotFoundException, InvalidNodeDistributionException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(ValidateNodeDistribution.SHASUMS_FILENAME);
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionShasum.execute(downloadedShasumFilepath, DISTRIBUTION_FILENAME)).thenReturn(
            Optional.of(expectedHash));
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenReturn(expectedHash);
        final DistributionValidatorSettings distributionValidatorSettings = new DistributionValidatorSettings(
            temporaryDirectoryPath, DISTRIBUTION_URL, DISTRIBUTION_FILE_PATH, PROXY, null);

        usecase.execute(distributionValidatorSettings);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(CHECKSUM_URL, PROXY, null, temporaryDirectoryPath, downloadedShasumFilepath))));
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }
}
