package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.installer.ValidateNodeDistribution.SHASUMS_FILE_NAME;
import static org.siouan.frontendgradleplugin.test.PathFixture.ANY_PATH;
import static org.siouan.frontendgradleplugin.test.PathFixture.TMP_PATH;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;

@ExtendWith(MockitoExtension.class)
class ValidateNodeDistributionTest {

    private static final URL CHECKSUM_URL;

    private static final String DISTRIBUTION_BASE_URL = "https://nodejs.org/dist/v9.2.4/";

    private static final URL DISTRIBUTION_URL;

    private static final String DISTRIBUTION_FILENAME = "distribution.zip";

    private static final Path DISTRIBUTION_FILE_PATH = ANY_PATH.resolve(DISTRIBUTION_FILENAME);

    private static final String TMP_SHASUMS_FILE_NAME = SHASUMS_FILE_NAME + ".tmp";

    static {
        try {
            DISTRIBUTION_URL = new URL(DISTRIBUTION_BASE_URL + "node-v9.2.4-win-x64.zip");
            CHECKSUM_URL = new URL(DISTRIBUTION_BASE_URL + SHASUMS_FILE_NAME);
        } catch (final MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    private FileManager fileManager;

    @Mock
    private BuildTemporaryFileName buildTemporaryFileName;

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
        temporaryDirectoryPath = TMP_PATH;
        usecase = new ValidateNodeDistribution(fileManager, buildTemporaryFileName, downloadResource,
            readNodeDistributionShasum, hashFile, mock(Logger.class));
    }

    @Test
    void should_fail_when_shasums_cannot_be_downloaded() throws IOException, ResourceDownloadException {
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final Exception expectedException = new IOException();
        doThrow(expectedException)
            .when(downloadResource)
            .execute(DownloadResourceCommand
                .builder()
                .resourceUrl(CHECKSUM_URL)
                .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
                .destinationFilePath(downloadedShasumFilepath)
                .serverCredentials(distributionServerCredentials)
                .proxySettings(proxySettings)
                .build());
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        assertThatThrownBy(() -> usecase.execute(validateNodeDistributionCommand)).isEqualTo(expectedException);

        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void should_fail_when_shasums_cannot_be_read() throws IOException, ResourceDownloadException {
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        final Exception expectedException = new IOException();
        when(readNodeDistributionShasum.execute(ReadNodeDistributionShasumCommand
            .builder()
            .nodeDistributionShasumFilePath(downloadedShasumFilepath)
            .distributionFileName(DISTRIBUTION_FILENAME)
            .build())).thenThrow(expectedException);
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        assertThatThrownBy(() -> usecase.execute(validateNodeDistributionCommand)).isEqualTo(expectedException);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(CHECKSUM_URL)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
            .destinationFilePath(downloadedShasumFilepath)
            .serverCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .build());
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void should_fail_when_shasum_is_not_found() throws IOException, ResourceDownloadException {
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        when(readNodeDistributionShasum.execute(ReadNodeDistributionShasumCommand
            .builder()
            .nodeDistributionShasumFilePath(downloadedShasumFilepath)
            .distributionFileName(DISTRIBUTION_FILENAME)
            .build())).thenReturn(Optional.empty());
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        assertThatThrownBy(() -> usecase.execute(validateNodeDistributionCommand)).isInstanceOf(
            NodeDistributionShasumNotFoundException.class);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(CHECKSUM_URL)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
            .destinationFilePath(downloadedShasumFilepath)
            .serverCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .build());
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void should_fail_when_distribution_file_cannot_be_hashed() throws IOException, ResourceDownloadException {
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        when(readNodeDistributionShasum.execute(ReadNodeDistributionShasumCommand
            .builder()
            .nodeDistributionShasumFilePath(downloadedShasumFilepath)
            .distributionFileName(DISTRIBUTION_FILENAME)
            .build())).thenReturn(Optional.of("0123456789abcdef"));
        final Exception expectedException = new IOException();
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenThrow(expectedException);
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        assertThatThrownBy(() -> usecase.execute(validateNodeDistributionCommand)).isEqualTo(expectedException);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(CHECKSUM_URL)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
            .destinationFilePath(downloadedShasumFilepath)
            .serverCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .build());
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void should_fail_when_distribution_file_hash_is_incorrect() throws IOException, ResourceDownloadException {
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionShasum.execute(ReadNodeDistributionShasumCommand
            .builder()
            .nodeDistributionShasumFilePath(downloadedShasumFilepath)
            .distributionFileName(DISTRIBUTION_FILENAME)
            .build())).thenReturn(Optional.of(expectedHash));
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenReturn("fedcba98765543210");
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        assertThatThrownBy(() -> usecase.execute(validateNodeDistributionCommand)).isInstanceOf(
            InvalidNodeDistributionException.class);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(CHECKSUM_URL)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
            .destinationFilePath(downloadedShasumFilepath)
            .serverCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .build());
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }

    @Test
    void should_return_when_distribution_file_is_valid()
        throws IOException, NodeDistributionShasumNotFoundException, InvalidNodeDistributionException,
        ResourceDownloadException {
        when(buildTemporaryFileName.execute(SHASUMS_FILE_NAME)).thenReturn(TMP_SHASUMS_FILE_NAME);
        final Path downloadedShasumFilepath = temporaryDirectoryPath.resolve(SHASUMS_FILE_NAME);
        final String expectedHash = "0123456789abcdef";
        when(readNodeDistributionShasum.execute(ReadNodeDistributionShasumCommand
            .builder()
            .nodeDistributionShasumFilePath(downloadedShasumFilepath)
            .distributionFileName(DISTRIBUTION_FILENAME)
            .build())).thenReturn(Optional.of(expectedHash));
        when(hashFile.execute(DISTRIBUTION_FILE_PATH)).thenReturn(expectedHash);
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final ValidateNodeDistributionCommand validateNodeDistributionCommand = new ValidateNodeDistributionCommand(
            temporaryDirectoryPath, DISTRIBUTION_URL, distributionServerCredentials, proxySettings,
            DISTRIBUTION_FILE_PATH);

        usecase.execute(validateNodeDistributionCommand);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(CHECKSUM_URL)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_SHASUMS_FILE_NAME))
            .destinationFilePath(downloadedShasumFilepath)
            .serverCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .build());
        verify(fileManager).deleteIfExists(downloadedShasumFilepath);
        verifyNoMoreInteractions(fileManager, downloadResource, readNodeDistributionShasum, hashFile);
    }
}
