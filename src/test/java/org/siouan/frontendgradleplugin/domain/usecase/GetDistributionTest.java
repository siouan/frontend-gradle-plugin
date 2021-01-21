package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import org.siouan.frontendgradleplugin.domain.exception.FrontendException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidNodeDistributionException;
import org.siouan.frontendgradleplugin.domain.exception.ResourceDownloadException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.util.DistributionDefintionMatcher;
import org.siouan.frontendgradleplugin.test.util.DistributionValidatorSettingsMatcher;
import org.siouan.frontendgradleplugin.test.util.DownloadSettingsMatcher;

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
    void shouldFailGettingDistributionWhenCurrentPlatformIsNotSupportedByUrlResolver()
        throws UnsupportedPlatformException, MalformedURLException {
        final Exception expectedException = new UnsupportedPlatformException(PlatformFixture.LOCAL_PLATFORM);
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenThrow(expectedException);
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void shouldFailGettingDistributionWhenDistributionUrlIsMalformed()
        throws UnsupportedPlatformException, MalformedURLException {
        final Exception expectedException = new MalformedURLException();
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenThrow(expectedException);
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void shouldFailGettingDistributionWhenResolvedUrlIsInvalid()
        throws UnsupportedPlatformException, MalformedURLException {
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(new URL("https://domain.com/"));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings))
            .isInstanceOf(InvalidDistributionUrlException.class);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void shouldFailGettingDistributionWhenDownloadFails()
        throws UnsupportedPlatformException, IOException, ResourceDownloadException {
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        when(buildTemporaryFileName.execute(anyString())).thenReturn(TMP_DISTRIBUTION_NAME);
        final Exception expectedException = new IOException();
        doThrow(expectedException)
            .when(downloadResource)
            .execute(argThat(new DownloadSettingsMatcher(
                new DownloadSettings(downloadUrl, null, null, temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME),
                    distributionFilePath))));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void shouldFailGettingDistributionWhenDistributionIsInvalidAfterDownload() throws FrontendException, IOException {
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        when(buildTemporaryFileName.execute(anyString())).thenReturn(TMP_DISTRIBUTION_NAME);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        final Exception expectedException = mock(InvalidNodeDistributionException.class);
        doThrow(expectedException)
            .when(validateNodeDistribution)
            .execute(argThat(new DistributionValidatorSettingsMatcher(
                new DistributionValidatorSettings(temporaryDirectoryPath, downloadUrl, null, null,
                    distributionFilePath))));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verify(downloadResource)
            .execute(argThat(new DownloadSettingsMatcher(
                new DownloadSettings(downloadUrl, null, null, temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME),
                    distributionFilePath))));
        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }

    @Test
    void shouldGetValidDistributionWhenValidatorIsAvailable() throws FrontendException, IOException {
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(resolveNodeDistributionUrl.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        when(buildTemporaryFileName.execute(anyString())).thenReturn(TMP_DISTRIBUTION_NAME);
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null, null,
            temporaryDirectoryPath);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);

        assertThat(usecase.execute(getDistributionSettings)).isEqualTo(distributionFilePath);

        verify(downloadResource)
            .execute(argThat(new DownloadSettingsMatcher(
                new DownloadSettings(downloadUrl, null, null, temporaryDirectoryPath.resolve(TMP_DISTRIBUTION_NAME),
                    distributionFilePath))));
        verify(validateNodeDistribution)
            .execute(argThat(new DistributionValidatorSettingsMatcher(
                new DistributionValidatorSettings(temporaryDirectoryPath, downloadUrl, null, null,
                    distributionFilePath))));
        verifyNoMoreInteractions(resolveNodeDistributionUrl, buildTemporaryFileName, downloadResource,
            validateNodeDistribution);
    }
}
