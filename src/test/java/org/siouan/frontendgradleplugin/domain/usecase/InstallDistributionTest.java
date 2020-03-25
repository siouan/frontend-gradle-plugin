package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
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
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.gradle.api.logging.LogLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionInstallerException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionUrlResolverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.FrontendIOException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.Archiver;
import org.siouan.frontendgradleplugin.domain.model.DistributionValidatorProperties;
import org.siouan.frontendgradleplugin.domain.model.DownloadParameters;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.InstallDistributionSettings;
import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.util.PathUtils;

@ExtendWith(MockitoExtension.class)
class InstallDistributionTest {

    private static final String DISTRIBUTION_URL_WITHOUT_EXTENSION = "https://domain.com/file";

    private static final String DISTRIBUTION_URL_WITH_EXTENSION = "https://domain.com/file.txt";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private DistributionUrlResolver urlResolver;

    @Mock
    private DownloadResource downloadResource;

    @Mock
    private DistributionValidator validator;

    @Mock
    private ArchiverProvider archiverProvider;

    @InjectMocks
    private InstallDistribution usecase;

    @Test
    void shouldFailWhenDistributionDownloadUrlCannotBeResolved() throws DistributionUrlResolverException {
        final Exception expectedException = mock(DistributionUrlResolverException.class);
        when(urlResolver.execute(null)).thenThrow(expectedException);
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verifyNoMoreInteractions(urlResolver, downloadResource, validator, archiverProvider);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloaded()
        throws DistributionUrlResolverException, MalformedURLException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final DownloadParameters downloadSettings = new DownloadParameters(distributionUrl, temporaryDirectoryPath, null);
        final Exception expectedException = mock(FrontendIOException.class);
        doThrow(expectedException).when(downloadResource).execute(downloadSettings);
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(downloadSettings);
        verifyNoMoreInteractions(urlResolver, downloadResource, validator, archiverProvider);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsInvalid()
        throws DistributionUrlResolverException, DistributionValidatorException, MalformedURLException,
        URISyntaxException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final DistributionValidatorProperties distributionValidatorProperties = new DistributionValidatorProperties(
            null, temporaryDirectoryPath, distributionUrl, null);
        final Exception expectedException = mock(DistributionValidatorException.class);
        doThrow(expectedException).when(validator).validate(distributionValidatorProperties);
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verify(validator).validate(distributionValidatorProperties);
        verifyNoMoreInteractions(urlResolver, downloadResource, validator, archiverProvider);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsNotSupported()
        throws DistributionUrlResolverException, MalformedURLException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCauseInstanceOf(UnsupportedDistributionArchiveException.class);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verifyNoMoreInteractions(urlResolver, downloadResource, validator, archiverProvider);
    }

    @Test
    void shouldFailWhenNoArchiverFoundToExplodeDistribution()
        throws DistributionUrlResolverException, MalformedURLException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.empty());
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCauseInstanceOf(UnsupportedDistributionArchiveException.class);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verifyNoMoreInteractions(urlResolver, downloadResource, archiverProvider, validator);
    }

    @Test
    void shouldFailWhenArchiveContainsSlipAttack()
        throws DistributionUrlResolverException, MalformedURLException, ArchiverException, SlipAttackException,
        UnsupportedEntryException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(SlipAttackException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloadResource, archiverProvider, validator);
    }

    @Test
    void shouldFailWhenArchiveContainsAnUnsupportedEntry()
        throws DistributionUrlResolverException, MalformedURLException, ArchiverException, SlipAttackException,
        UnsupportedEntryException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(UnsupportedEntryException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloadResource, archiverProvider, validator);
    }

    @Test
    void shouldFailWhenArchiveCannotBeExploded()
        throws DistributionUrlResolverException, MalformedURLException, ArchiverException, SlipAttackException,
        UnsupportedEntryException, FrontendIOException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(ArchiverException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        assertThatThrownBy(() -> usecase.execute(installDistributionSettings))
            .isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).execute(null);
        verify(downloadResource).execute(null);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloadResource, archiverProvider, validator);
    }

    @Test
    void shouldInstallDistributionWithoutRootDirectory()
        throws DistributionUrlResolverException, UnsupportedEntryException, SlipAttackException,
        DistributionInstallerException, ArchiverException, IOException, URISyntaxException,
        UnsupportedDistributionArchiveException, FrontendIOException, DistributionValidatorException {
        final URL distributionUrl = getClass().getClassLoader().getResource("dist-without-root-dir.zip");
        final Path installDirectory = temporaryDirectoryPath.resolve("install");
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final DownloadParameters downloadSettings = new DownloadParameters(distributionUrl, temporaryDirectoryPath, null);
        doAnswer(new DownloaderAnswer()).when(downloadResource).execute(downloadSettings);
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.of(archiver));
        doAnswer(new ArchiverAnswer()).when(archiver).explode(any(ExplodeSettings.class));
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        usecase.execute(installDistributionSettings);

        verify(urlResolver).execute(null);
        try (final Stream<Path> childFiles = Files.list(installDirectory)) {
            assertThat(childFiles.findAny()).isNotEmpty();
        }
        verify(downloadResource).execute(null);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, archiverProvider, archiver, validator);
    }

    @Test
    void shouldInstallDistributionAndRemoveRootDirectory()
        throws DistributionUrlResolverException, UnsupportedEntryException, SlipAttackException,
        DistributionInstallerException, ArchiverException, IOException, URISyntaxException,
        UnsupportedDistributionArchiveException, FrontendIOException, DistributionValidatorException {
        final URL distributionUrl = getClass().getClassLoader().getResource("yarn-v1.16.0.tar.gz");
        final Path installDirectory = temporaryDirectoryPath.resolve("install");
        when(urlResolver.execute(null)).thenReturn(distributionUrl);
        final DownloadParameters downloadSettings = new DownloadParameters(distributionUrl, temporaryDirectoryPath, null);
        doAnswer(new DownloaderAnswer()).when(downloadResource).execute(downloadSettings);
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByFilenameExtension(anyString())).thenReturn(Optional.of(archiver));
        doAnswer(new ArchiverAnswer()).when(archiver).explode(any(ExplodeSettings.class));
        final InstallDistributionSettings installDistributionSettings = new InstallDistributionSettings(null, null,
            null, LogLevel.LIFECYCLE, temporaryDirectoryPath, temporaryDirectoryPath.resolve("install"));

        usecase.execute(installDistributionSettings);

        verify(urlResolver).execute(null);
        try (final Stream<Path> childFiles = Files.list(installDirectory)) {
            assertThat(childFiles.findAny()).isNotEmpty();
        }
        verify(downloadResource).execute(downloadSettings);
        verify(archiverProvider).findByFilenameExtension(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, archiverProvider, archiver, validator);
    }

    /**
     * Downloader answer that emulates archive downloading.
     */
    private static class DownloaderAnswer implements Answer<Void> {

        @Override
        public Void answer(final InvocationOnMock invocation) throws Throwable {
            final URL distributionUrl = invocation.getArgument(0);
            final Path targetFile = invocation.getArgument(1);
            Files.copy(Paths.get(distributionUrl.toURI()), targetFile);
            return null;
        }
    }

    /**
     * Archiver answer that emulates archive exploding.
     */
    private static class ArchiverAnswer implements Answer<Void> {

        @Override
        public Void answer(final InvocationOnMock invocation) throws Throwable {
            final ExplodeSettings settings = invocation.getArgument(0);
            final Path copyDestDirectory = settings
                .getTargetDirectory()
                .resolve(PathUtils.removeExtension(settings.getArchiveFile()));
            Files.createDirectories(copyDestDirectory);
            Files.copy(settings.getArchiveFile(), copyDestDirectory.resolve("node"));
            return null;
        }
    }
}
