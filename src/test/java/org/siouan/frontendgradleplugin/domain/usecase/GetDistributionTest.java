package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidNodeDistributionException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DistributionDefinition;
import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.DistributionUrlResolver;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidator;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorSettings;
import org.siouan.frontendgradleplugin.domain.model.DownloadSettings;
import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.ProxySettings;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.fixture.ProxySettingsFixture;
import org.siouan.frontendgradleplugin.test.util.DistributionDefintionMatcher;
import org.siouan.frontendgradleplugin.test.util.DistributionValidatorSettingsMatcher;
import org.siouan.frontendgradleplugin.test.util.DownloadSettingsMatcher;

@ExtendWith(MockitoExtension.class)
class GetDistributionTest {

    private static final String DISTRIBUTION_NAME = "file";

    private static final String DISTRIBUTION_URL_ROOT = "https://domain.com/dist/";

    private static final String DISTRIBUTION_URL_PATH_PATTERN = DISTRIBUTION_NAME;

    private static final String DISTRIBUTION_ID = DistributionId.NODE;

    private static final ProxySettings PROXY_SETTINGS = ProxySettingsFixture.NO_PROXY_SETTINGS;

    private static final String VERSION = "4.1.5";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private GetDistributionUrlResolver getDistributionUrlResolver;

    @Mock
    private DistributionUrlResolver distributionUrlResolver;

    @Mock
    private DownloadResource downloadResource;

    @Mock
    private GetDistributionValidator getDistributionValidator;

    @Mock
    private DistributionValidator distributionValidator;

    @InjectMocks
    private GetDistribution usecase;

    @BeforeEach
    void setUp() {
        usecase = new GetDistribution(getDistributionUrlResolver, downloadResource, getDistributionValidator,
            mock(Logger.class));
    }

    @Test
    void shouldFailGettingDistributionWhenDistributionIdIsNotSupportedByUrlResolver() {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.empty());
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DISTRIBUTION_ID,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isInstanceOf(
            UnsupportedDistributionIdException.class);

        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldFailGettingDistributionWhenCurrentPlatformIsNotSupportedByUrlResolver()
        throws UnsupportedPlatformException, MalformedURLException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final Exception expectedException = new UnsupportedPlatformException(PlatformFixture.LOCAL_PLATFORM);
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenThrow(expectedException);
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldFailGettingDistributionWhenDistributionUrlIsMalformed()
        throws UnsupportedPlatformException, MalformedURLException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final Exception expectedException = new MalformedURLException();
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenThrow(expectedException);
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldFailGettingDistributionWhenResolvedUrlIsInvalid()
        throws UnsupportedPlatformException, MalformedURLException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(new URL("https://domain.com/"));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isInstanceOf(
            InvalidDistributionUrlException.class);

        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldFailGettingDistributionWhenDownloadFails() throws UnsupportedPlatformException, IOException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        final Exception expectedException = new IOException();
        doThrow(expectedException)
            .when(downloadResource)
            .execute(argThat(new DownloadSettingsMatcher(
                new DownloadSettings(downloadUrl, null, PROXY_SETTINGS, temporaryDirectoryPath,
                    distributionFilePath))));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldGetDistributionWithoutValidationWhenNoValidatorIsAvailable()
        throws UnsupportedPlatformException, IOException, DistributionValidatorException,
        UnsupportedDistributionIdException, InvalidDistributionUrlException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        when(getDistributionValidator.execute(DISTRIBUTION_ID)).thenReturn(Optional.empty());
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);

        assertThat(usecase.execute(getDistributionSettings)).isEqualTo(distributionFilePath);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(downloadUrl, null, PROXY_SETTINGS, temporaryDirectoryPath, distributionFilePath))));
        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldFailGettingDistributionWhenDistributionIsInvalidAfterDownload()
        throws UnsupportedPlatformException, IOException, DistributionValidatorException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        when(getDistributionValidator.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionValidator));
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);
        final Exception expectedException = mock(InvalidNodeDistributionException.class);
        doThrow(expectedException)
            .when(distributionValidator)
            .execute(argThat(new DistributionValidatorSettingsMatcher(
                new DistributionValidatorSettings(temporaryDirectoryPath, downloadUrl, null, PROXY_SETTINGS,
                    distributionFilePath))));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(getDistributionSettings)).isEqualTo(expectedException);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(downloadUrl, null, PROXY_SETTINGS, temporaryDirectoryPath, distributionFilePath))));
        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }

    @Test
    void shouldGetValidDistributionWhenValidatorIsAvailable()
        throws UnsupportedPlatformException, IOException, DistributionValidatorException,
        UnsupportedDistributionIdException, InvalidDistributionUrlException {
        when(getDistributionUrlResolver.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionUrlResolver));
        final URL downloadUrl = new URL(DISTRIBUTION_URL_ROOT + DISTRIBUTION_URL_PATH_PATTERN);
        when(distributionUrlResolver.execute(argThat(new DistributionDefintionMatcher(
            new DistributionDefinition(PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT,
                DISTRIBUTION_URL_PATH_PATTERN))))).thenReturn(downloadUrl);
        when(getDistributionValidator.execute(DISTRIBUTION_ID)).thenReturn(Optional.of(distributionValidator));
        final GetDistributionSettings getDistributionSettings = new GetDistributionSettings(DistributionId.NODE,
            PlatformFixture.LOCAL_PLATFORM, VERSION, DISTRIBUTION_URL_ROOT, DISTRIBUTION_URL_PATH_PATTERN, null,
            PROXY_SETTINGS, temporaryDirectoryPath);
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_NAME);

        assertThat(usecase.execute(getDistributionSettings)).isEqualTo(distributionFilePath);

        verify(downloadResource).execute(argThat(new DownloadSettingsMatcher(
            new DownloadSettings(downloadUrl, null, PROXY_SETTINGS, temporaryDirectoryPath, distributionFilePath))));
        verify(distributionValidator).execute(argThat(new DistributionValidatorSettingsMatcher(
            new DistributionValidatorSettings(temporaryDirectoryPath, downloadUrl, null, PROXY_SETTINGS,
                distributionFilePath))));
        verifyNoMoreInteractions(getDistributionUrlResolver, distributionUrlResolver, downloadResource,
            getDistributionValidator, distributionValidator);
    }
}
