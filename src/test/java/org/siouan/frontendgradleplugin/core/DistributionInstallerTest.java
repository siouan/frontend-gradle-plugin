package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.siouan.frontendgradleplugin.core.archivers.Archiver;
import org.siouan.frontendgradleplugin.core.archivers.ArchiverException;
import org.siouan.frontendgradleplugin.core.archivers.ArchiverFactory;
import org.siouan.frontendgradleplugin.core.archivers.SlipAttackException;
import org.siouan.frontendgradleplugin.core.archivers.UnsupportedEntryException;

/**
 * Unit tests for the {@link DistributionInstaller} class.
 */
@ExtendWith(MockitoExtension.class)
class DistributionInstallerTest {

    private static final String DISTRIBUTION_URL_WITHOUT_EXTENSION = "https://domain.com/file";

    private static final String DISTRIBUTION_URL_WITH_EXTENSION = "https://domain.com/file.txt";

    @TempDir
    protected File temporaryDirectory;

    @Mock
    private Logger logger;

    @Mock
    private Task task;

    @Mock
    private DistributionUrlResolver urlResolver;

    @Mock
    private Downloader downloader;

    @Mock
    private DistributionValidator validator;

    @Mock
    private ArchiverFactory archiverFactory;

    @BeforeEach
    void setUp() {
        when(task.getLogger()).thenReturn(logger);
    }

    @Test
    void shouldFailWhenDistributionDownloadUrlCannotBeResolved() throws DistributionUrlResolverException {
        final Exception expectedException = mock(DistributionUrlResolverException.class);
        when(urlResolver.resolve()).thenThrow(expectedException);
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verifyNoMoreInteractions(urlResolver);
        verifyZeroInteractions(downloader, validator, archiverFactory);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDownloaded()
        throws DistributionUrlResolverException, MalformedURLException, DownloadException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Exception expectedException = mock(DownloadException.class);
        doThrow(expectedException).when(downloader).download(eq(distributionUrl), any(Path.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verifyNoMoreInteractions(urlResolver, downloader);
        verifyZeroInteractions(validator, archiverFactory);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsInvalid()
        throws DistributionUrlResolverException, DownloadException, DistributionValidatorException,
        MalformedURLException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Exception expectedException = mock(DistributionValidatorException.class);
        doThrow(expectedException).when(validator).validate(eq(distributionUrl), any(Path.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, validator,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(validator).validate(eq(distributionUrl), any(Path.class));
        verifyNoMoreInteractions(urlResolver, downloader, validator);
        verifyZeroInteractions(archiverFactory);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsNotSupported()
        throws DistributionUrlResolverException, DownloadException, MalformedURLException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITHOUT_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCauseInstanceOf(UnsupportedDistributionArchiveException.class);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verifyNoMoreInteractions(urlResolver, downloader);
        verifyZeroInteractions(validator, archiverFactory);
    }

    @Test
    void shouldFailWhenNoArchiverFoundToExplodeDistribution()
        throws DistributionUrlResolverException, DownloadException, MalformedURLException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        when(archiverFactory.get(anyString())).thenReturn(Optional.empty());
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCauseInstanceOf(UnsupportedDistributionArchiveException.class);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(archiverFactory).get(anyString());
        verifyNoMoreInteractions(urlResolver, downloader, archiverFactory);
        verifyZeroInteractions(validator);
    }

    @Test
    void shouldFailWhenArchiveContainsSlipAttack()
        throws DistributionUrlResolverException, DownloadException, MalformedURLException, ArchiverException,
        SlipAttackException, UnsupportedEntryException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverFactory.get(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(SlipAttackException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(archiverFactory).get(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloader, archiverFactory);
        verifyZeroInteractions(validator);
    }

    @Test
    void shouldFailWhenArchiveContainsAnUnsupportedEntry()
        throws DistributionUrlResolverException, DownloadException, MalformedURLException, ArchiverException,
        SlipAttackException, UnsupportedEntryException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverFactory.get(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(UnsupportedEntryException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(archiverFactory).get(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloader, archiverFactory);
        verifyZeroInteractions(validator);
    }

    @Test
    void shouldFailWhenArchiveCannotBeExploded()
        throws DistributionUrlResolverException, DownloadException, MalformedURLException, ArchiverException,
        SlipAttackException, UnsupportedEntryException {
        final URL distributionUrl = URI.create(DISTRIBUTION_URL_WITH_EXTENSION).toURL();
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Archiver archiver = mock(Archiver.class);
        when(archiverFactory.get(anyString())).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(ArchiverException.class);
        doThrow(expectedException).when(archiver).explode(any(ExplodeSettings.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, temporaryDirectory.toPath().resolve("install")));

        assertThatThrownBy(installer::install).isInstanceOf(DistributionInstallerException.class)
            .hasCause(expectedException);

        verify(urlResolver).resolve();
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(archiverFactory).get(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, downloader, archiverFactory);
        verifyZeroInteractions(validator);
    }

    @Test
    void shouldDownloadDistribution()
        throws DistributionUrlResolverException, UnsupportedEntryException, SlipAttackException,
        DistributionInstallerException, ArchiverException, DownloadException, IOException {
        final URL distributionUrl = getClass().getClassLoader().getResource("yarn-v1.15.2.tar.gz");
        final Path installDirectory = temporaryDirectory.toPath().resolve("install");
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        doAnswer(new DownloaderAnswer()).when(downloader).download(eq(distributionUrl), any(Path.class));
        final Archiver archiver = mock(Archiver.class);
        when(archiverFactory.get(anyString())).thenReturn(Optional.of(archiver));
        doAnswer(new ArchiverAnswer()).when(archiver).explode(any(ExplodeSettings.class));
        final DistributionInstaller installer = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, downloader, null,
                archiverFactory, installDirectory));

        installer.install();

        verify(urlResolver).resolve();
        try (final Stream<Path> childFiles = Files.list(installDirectory)) {
            assertThat(childFiles.findAny()).isNotEmpty();
        }
        verify(downloader).download(eq(distributionUrl), any(Path.class));
        verify(archiverFactory).get(anyString());
        verify(archiver).explode(any(ExplodeSettings.class));
        verifyNoMoreInteractions(urlResolver, archiverFactory, archiver);
        verifyZeroInteractions(validator);
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
            final String distributionFilename = settings.getArchiveFile().getFileName().toString();
            final Path copyDestDirectory = settings.getTargetDirectory()
                .resolve(Utils.removeExtension(distributionFilename));
            Files.createDirectory(copyDestDirectory);
            Files.copy(settings.getTargetDirectory().resolve(distributionFilename), copyDestDirectory.resolve("node"));
            return null;
        }
    }
}
