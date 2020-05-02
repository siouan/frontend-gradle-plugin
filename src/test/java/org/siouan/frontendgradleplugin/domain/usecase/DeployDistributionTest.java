package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.util.Resources.getResourcePath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.model.Archiver;
import org.siouan.frontendgradleplugin.domain.model.DeploymentSettings;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.util.PathUtils;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.util.ExplodeSettingsMatcher;

@ExtendWith(MockitoExtension.class)
class DeployDistributionTest {

    private static final String DISTRIBUTION_FILENAME = "archive.tar.gz";

    @TempDir
    Path temporaryDirectoryPath;

    private Path extractDirectoryPath;

    private Path installDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private ArchiverProvider archiverProvider;

    @InjectMocks
    private DeployDistribution usecase;

    @BeforeEach
    void setUp() {
        installDirectoryPath = temporaryDirectoryPath.resolve("install");
        extractDirectoryPath = temporaryDirectoryPath.resolve("extract");
        usecase = new DeployDistribution(fileManager, archiverProvider, mock(Logger.class));
    }

    @Test
    void shouldFailWhenTemporaryExtractDirectoryCannotBeCreated() throws IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        final Exception expectedException = new IOException();
        when(fileManager.createDirectory(extractDirectoryPath)).thenThrow(expectedException);
        final DeploymentSettings deploymentSettings = new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deploymentSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void shouldFailWhenNoArchiverFoundToExplodeDistribution() throws IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.empty());
        final DeploymentSettings deploymentSettings = new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deploymentSettings)).isInstanceOf(
            UnsupportedDistributionArchiveException.class);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void shouldFailWhenArchiveCannotBeExploded() throws ArchiverException, IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        final Exception expectedException = mock(ArchiverException.class);
        doThrow(expectedException)
            .when(archiver)
            .explode(argThat(new ExplodeSettingsMatcher(
                new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, distributionFilePath, extractDirectoryPath))));
        final DeploymentSettings deploymentSettings = new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deploymentSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void shouldInstallDistributionWithoutRootDirectory()
        throws ArchiverException, UnsupportedDistributionArchiveException, IOException {
        final Path distributionFilePath = getResourcePath(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        doAnswer(new ArchiverAnswer())
            .when(archiver)
            .explode(argThat(new ExplodeSettingsMatcher(
                new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, distributionFilePath, extractDirectoryPath))));
        when(fileManager.list(extractDirectoryPath)).thenReturn(
            Stream.of(extractDirectoryPath.resolve("file1"), extractDirectoryPath.resolve("file2")));
        final DeploymentSettings deploymentSettings = new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        usecase.execute(deploymentSettings);

        verify(fileManager).moveFileTree(extractDirectoryPath, installDirectoryPath);
        verify(fileManager).deleteIfExists(extractDirectoryPath);
        verifyNoMoreInteractions(fileManager, archiverProvider, archiver);
    }

    @Test
    void shouldInstallDistributionAndRemoveRootDirectory()
        throws ArchiverException, UnsupportedDistributionArchiveException, IOException {
        final Path distributionFilePath = getResourcePath(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        doAnswer(new ArchiverAnswer())
            .when(archiver)
            .explode(argThat(new ExplodeSettingsMatcher(
                new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, distributionFilePath, extractDirectoryPath))));
        when(fileManager.list(extractDirectoryPath)).thenReturn(Stream.of(extractDirectoryPath.resolve("root-dir")));
        final DeploymentSettings deploymentSettings = new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        usecase.execute(deploymentSettings);

        verify(fileManager).moveFileTree(argThat(path -> path.getParent().equals(extractDirectoryPath)),
            eq(installDirectoryPath));
        verify(fileManager).deleteIfExists(extractDirectoryPath);
        verifyNoMoreInteractions(fileManager, archiverProvider, archiver);
    }

    /**
     * Archiver answer that emulates archive exploding.
     */
    private static class ArchiverAnswer implements Answer<Void> {

        @Override
        public Void answer(final InvocationOnMock invocation) throws Throwable {
            final ExplodeSettings settings = invocation.getArgument(0);
            final Path copyDestDirectory = settings
                .getTargetDirectoryPath()
                .resolve(PathUtils.removeExtension(settings.getArchiveFilePath()));
            Files.createDirectories(copyDestDirectory);
            Files.copy(settings.getArchiveFilePath(), copyDestDirectory.resolve("node"));
            return null;
        }
    }
}
