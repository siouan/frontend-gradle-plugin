package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.FrontendException;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;

@ExtendWith(MockitoExtension.class)
class InstallNodeDistributionTest {

    private static final String VERSION = "7.34.1";

    private static final String DISTRIBUTION_URL_ROOT = "https://domain.com/archives";

    private static final String DISTRIBUTION_URL_PATH_PATTERN = "/distro.tar.gz";

    @TempDir
    Path temporaryDirectoryPath;

    private Path extractDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private GetDistribution getDistribution;

    @Mock
    private DeployDistribution deployDistribution;

    private InstallNodeDistribution usecase;

    private Path installDirectoryPath;

    @BeforeEach
    void setUp() {
        usecase = new InstallNodeDistribution(fileManager, getDistribution, deployDistribution, mock(Logger.class));
        installDirectoryPath = temporaryDirectoryPath.resolve("install");
        extractDirectoryPath = temporaryDirectoryPath.resolve(InstallNodeDistribution.EXTRACT_DIRECTORY_NAME);
    }

    @Test
    void should_fail_when_install_directory_cannot_be_deleted() throws IOException {
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(fileManager).deleteFileTree(installDirectoryPath, true);
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN,
            CredentialsFixture.someCredentials(), ProxySettingsFixture.someProxySettings(),
            RetrySettingsFixture.someRetrySettings(), temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installNodeDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void should_fail_when_distribution_cannot_be_retrieved() throws IOException, FrontendException, URISyntaxException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final RetrySettings retrySettings = RetrySettingsFixture.someRetrySettings();
        final Exception expectedException = new UnsupportedPlatformException(LOCAL_PLATFORM);
        when(getDistribution.execute(GetDistributionCommand
            .builder()
            .platform(platform)
            .version(version)
            .distributionUrlRoot(distributionUrlRoot)
            .distributionUrlPathPattern(distributionUrlPathPattern)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .retrySettings(retrySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build())).thenThrow(expectedException);
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            platform, version, distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials,
            proxySettings, retrySettings, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installNodeDistributionCommand)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void should_fail_when_temporary_extract_directory_cannot_be_deleted()
        throws IOException, FrontendException, URISyntaxException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final RetrySettings retrySettings = RetrySettingsFixture.someRetrySettings();
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(GetDistributionCommand
            .builder()
            .platform(platform)
            .version(version)
            .distributionUrlRoot(distributionUrlRoot)
            .distributionUrlPathPattern(distributionUrlPathPattern)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .retrySettings(retrySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build())).thenReturn(distributionFilePath);
        doNothing().when(fileManager).deleteFileTree(installDirectoryPath, true);
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(fileManager).deleteFileTree(extractDirectoryPath, true);
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            platform, version, distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials,
            proxySettings, retrySettings, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installNodeDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void should_fail_when_distribution_cannot_be_deployed() throws IOException, FrontendException, URISyntaxException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final RetrySettings retrySettings = RetrySettingsFixture.someRetrySettings();
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(GetDistributionCommand
            .builder()
            .platform(platform)
            .version(version)
            .distributionUrlRoot(distributionUrlRoot)
            .distributionUrlPathPattern(distributionUrlPathPattern)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .retrySettings(retrySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build())).thenReturn(distributionFilePath);
        final Exception expectedException = mock(UnsupportedDistributionArchiveException.class);
        doThrow(expectedException)
            .when(deployDistribution)
            .execute(DeployDistributionCommand
                .builder()
                .platform(platform)
                .temporaryDirectoryPath(extractDirectoryPath)
                .installDirectoryPath(installDirectoryPath)
                .distributionFilePath(distributionFilePath)
                .build());
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            platform, version, distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials,
            proxySettings, retrySettings, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installNodeDistributionCommand)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verify(fileManager).deleteFileTree(extractDirectoryPath, true);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void should_fail_when_downloaded_distribution_file_cannot_be_deleted()
        throws IOException, FrontendException, URISyntaxException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final RetrySettings retrySettings = RetrySettingsFixture.someRetrySettings();
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(GetDistributionCommand
            .builder()
            .platform(platform)
            .version(version)
            .distributionUrlRoot(distributionUrlRoot)
            .distributionUrlPathPattern(distributionUrlPathPattern)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .retrySettings(retrySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build())).thenReturn(distributionFilePath);
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(fileManager).delete(distributionFilePath);
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            platform, version, distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials,
            proxySettings, retrySettings, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installNodeDistributionCommand)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verify(fileManager).deleteFileTree(extractDirectoryPath, true);
        verify(deployDistribution).execute(DeployDistributionCommand
            .builder()
            .platform(platform)
            .temporaryDirectoryPath(extractDirectoryPath)
            .installDirectoryPath(installDirectoryPath)
            .distributionFilePath(distributionFilePath)
            .build());
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void should_install_distribution() throws IOException, FrontendException, URISyntaxException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final RetrySettings retrySettings = RetrySettingsFixture.someRetrySettings();
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(GetDistributionCommand
            .builder()
            .platform(platform)
            .version(version)
            .distributionUrlRoot(distributionUrlRoot)
            .distributionUrlPathPattern(distributionUrlPathPattern)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .retrySettings(retrySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build())).thenReturn(distributionFilePath);
        final InstallNodeDistributionCommand installNodeDistributionCommand = new InstallNodeDistributionCommand(
            platform, version, distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials,
            proxySettings, retrySettings, temporaryDirectoryPath, installDirectoryPath);

        usecase.execute(installNodeDistributionCommand);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verify(fileManager).deleteFileTree(extractDirectoryPath, true);
        verify(deployDistribution).execute(DeployDistributionCommand
            .builder()
            .platform(platform)
            .temporaryDirectoryPath(extractDirectoryPath)
            .installDirectoryPath(installDirectoryPath)
            .distributionFilePath(distributionFilePath)
            .build());
        verify(fileManager).delete(distributionFilePath);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }
}
