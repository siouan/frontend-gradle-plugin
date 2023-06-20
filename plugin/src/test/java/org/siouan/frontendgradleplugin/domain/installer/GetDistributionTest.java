package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FrontendException;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.UnsupportedPlatformException;

@ExtendWith(MockitoExtension.class)
class GetDistributionTest {

    private static final String DISTRIBUTION_NAME = "file";

    private static final String DISTRIBUTION_URL_ROOT = "https://domain.com/dist/";

    private static final String DISTRIBUTION_URL_PATH_PATTERN = DISTRIBUTION_NAME;

    private static final String TMP_DISTRIBUTION_NAME = DISTRIBUTION_NAME + ".tmp";

    private static final String VERSION = "4.1.5";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private ResolveNodeDistributionUrl resolveNodeDistributionUrl;

    @Mock
    private BuildTemporaryFileName buildTemporaryFileName;

    @Mock
    private DownloadResource downloadResource;

    @Mock
    private ValidateNodeDistribution validateNodeDistribution;

    @InjectMocks
    private GetDistribution usecase;

    @BeforeEach
    void setUp() {
        usecase = new GetDistribution(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution, mock(Logger.class));
    }

    @Test
    void should_fail_getting_distribution_when_current_platform_is_not_supported_by_url_resolver()
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Exception expectedException = new UnsupportedPlatformException(LOCAL_PLATFORM);
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenThrow(expectedException);
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, CredentialsFixture.someCredentials(),
            ProxySettingsFixture.someProxySettings(), temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void should_fail_getting_distribution_when_distribution_url_is_malformed()
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Exception expectedException = new MalformedURLException();
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenThrow(expectedException);
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, CredentialsFixture.someCredentials(),
            ProxySettingsFixture.someProxySettings(), temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void should_fail_getting_distribution_when_resolved_url_is_invalid()
        throws UnsupportedPlatformException, MalformedURLException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenReturn(new URL("https://domain.com/"));
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, CredentialsFixture.someCredentials(),
            ProxySettingsFixture.someProxySettings(), temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionCommand)).isInstanceOf(
            InvalidDistributionUrlException.class);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void should_fail_getting_distribution_when_download_fails()
        throws UnsupportedPlatformException, IOException, ResourceDownloadException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final URL downloadUrl = new URL(distributionUrlRoot + distributionUrlPathPattern);
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenReturn(downloadUrl);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        when(buildTemporaryFileName.execute(DISTRIBUTION_NAME)).thenReturn(TMP_DISTRIBUTION_NAME);
        final Exception expectedException = new IOException();
        doThrow(expectedException)
            .when(downloadResource)
            .execute(DownloadResourceCommand
                .builder()
                .resourceUrl(downloadUrl)
                .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME))
                .destinationFilePath(distributionFilePath)
                .proxySettings(proxySettings)
                .serverCredentials(distributionServerCredentials)
                .build());
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials, proxySettings,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void should_fail_getting_distribution_when_distribution_is_invalid_after_download()
        throws FrontendException, IOException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final URL downloadUrl = new URL(distributionUrlRoot + distributionUrlPathPattern);
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenReturn(downloadUrl);
        when(buildTemporaryFileName.execute(DISTRIBUTION_NAME)).thenReturn(TMP_DISTRIBUTION_NAME);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        final Exception expectedException = mock(InvalidNodeDistributionException.class);
        doThrow(expectedException)
            .when(validateNodeDistribution)
            .execute(ValidateNodeDistributionCommand
                .builder()
                .distributionFilePath(distributionFilePath)
                .distributionUrl(downloadUrl)
                .distributionServerCredentials(distributionServerCredentials)
                .proxySettings(proxySettings)
                .temporaryDirectoryPath(temporaryDirectoryPath)
                .build());
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials, proxySettings,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionCommand)).isEqualTo(expectedException);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(downloadUrl)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME))
            .destinationFilePath(distributionFilePath)
            .proxySettings(proxySettings)
            .serverCredentials(distributionServerCredentials)
            .build());
        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void should_get_valid_distribution_when_validator_is_available() throws FrontendException, IOException {
        final Platform platform = LOCAL_PLATFORM;
        final String version = VERSION;
        final String distributionUrlRoot = DISTRIBUTION_URL_ROOT;
        final String distributionUrlPathPattern = DISTRIBUTION_URL_PATH_PATTERN;
        final Credentials distributionServerCredentials = CredentialsFixture.someCredentials();
        final ProxySettings proxySettings = ProxySettingsFixture.someProxySettings();
        final URL downloadUrl = new URL(distributionUrlRoot + distributionUrlPathPattern);
        when(resolveNodeDistributionUrl.execute(ResolveNodeDistributionUrlCommand
            .builder()
            .platform(platform)
            .version(version)
            .downloadUrlRoot(distributionUrlRoot)
            .downloadUrlPathPattern(distributionUrlPathPattern)
            .build())).thenReturn(downloadUrl);
        when(buildTemporaryFileName.execute(DISTRIBUTION_NAME)).thenReturn(TMP_DISTRIBUTION_NAME);
        final GetDistributionCommand getDistributionCommand = new GetDistributionCommand(platform, version,
            distributionUrlRoot, distributionUrlPathPattern, distributionServerCredentials, proxySettings,
            temporaryDirectoryPath);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);

        assertThat(usecase.execute(getDistributionCommand)).isEqualTo(distributionFilePath);

        verify(downloadResource).execute(DownloadResourceCommand
            .builder()
            .resourceUrl(downloadUrl)
            .temporaryFilePath(temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME))
            .destinationFilePath(distributionFilePath)
            .proxySettings(proxySettings)
            .serverCredentials(distributionServerCredentials)
            .build());
        verify(validateNodeDistribution).execute(ValidateNodeDistributionCommand
            .builder()
            .distributionFilePath(distributionFilePath)
            .distributionUrl(downloadUrl)
            .distributionServerCredentials(distributionServerCredentials)
            .proxySettings(proxySettings)
            .temporaryDirectoryPath(temporaryDirectoryPath)
            .build());
        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }
}
