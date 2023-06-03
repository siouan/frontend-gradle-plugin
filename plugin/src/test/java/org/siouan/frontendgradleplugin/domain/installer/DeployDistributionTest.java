package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.Resources.getResourcePath;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.siouan.frontendgradleplugin.domain.FileManager;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.PathUtils;
import org.siouan.frontendgradleplugin.domain.Platform;
import org.siouan.frontendgradleplugin.domain.installer.archiver.Archiver;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverException;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiverProvider;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

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
    void should_fail_when_temporary_extract_directory_cannot_be_created() throws IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        final Exception expectedException = new IOException();
        when(fileManager.createDirectory(extractDirectoryPath)).thenThrow(expectedException);
        final DeployDistributionCommand deployDistributionCommand = new DeployDistributionCommand(LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deployDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void should_fail_when_no_archiver_found_to_explode_distribution() throws IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.empty());
        final DeployDistributionCommand deployDistributionCommand = new DeployDistributionCommand(LOCAL_PLATFORM,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deployDistributionCommand)).isInstanceOf(
            UnsupportedDistributionArchiveException.class);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void should_fail_when_archive_cannot_be_exploded() throws ArchiverException, IOException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        final Platform platform = LOCAL_PLATFORM;
        final Exception expectedException = mock(ArchiverException.class);
        doThrow(expectedException)
            .when(archiver)
            .explode(ExplodeCommand
                .builder()
                .platform(platform)
                .archiveFilePath(distributionFilePath)
                .targetDirectoryPath(extractDirectoryPath)
                .build());
        final DeployDistributionCommand deployDistributionCommand = new DeployDistributionCommand(platform,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        assertThatThrownBy(() -> usecase.execute(deployDistributionCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, archiverProvider);
    }

    @Test
    void should_install_distribution_without_root_directory()
        throws ArchiverException, UnsupportedDistributionArchiveException, IOException {
        final Path distributionFilePath = getResourcePath(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        final Platform platform = LOCAL_PLATFORM;
        doAnswer(new ArchiverAnswer())
            .when(archiver)
            .explode(ExplodeCommand
                .builder()
                .platform(platform)
                .archiveFilePath(distributionFilePath)
                .targetDirectoryPath(extractDirectoryPath)
                .build());
        when(fileManager.list(extractDirectoryPath)).thenReturn(
            Stream.of(extractDirectoryPath.resolve("file1"), extractDirectoryPath.resolve("file2")));
        final DeployDistributionCommand deployDistributionCommand = new DeployDistributionCommand(platform,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        usecase.execute(deployDistributionCommand);

        verify(fileManager).moveFileTree(extractDirectoryPath, installDirectoryPath);
        verify(fileManager).deleteIfExists(extractDirectoryPath);
        verifyNoMoreInteractions(fileManager, archiverProvider, archiver);
    }

    @Test
    void should_install_distribution_and_remove_root_directory()
        throws ArchiverException, UnsupportedDistributionArchiveException, IOException {
        final Path distributionFilePath = getResourcePath(DISTRIBUTION_FILENAME);
        when(fileManager.createDirectory(extractDirectoryPath)).then(returnsFirstArg());
        final Archiver archiver = mock(Archiver.class);
        when(archiverProvider.findByArchiveFilePath(distributionFilePath)).thenReturn(Optional.of(archiver));
        final Platform platform = LOCAL_PLATFORM;
        doAnswer(new ArchiverAnswer())
            .when(archiver)
            .explode(ExplodeCommand
                .builder()
                .platform(platform)
                .archiveFilePath(distributionFilePath)
                .targetDirectoryPath(extractDirectoryPath)
                .build());
        when(fileManager.list(extractDirectoryPath)).thenReturn(Stream.of(extractDirectoryPath.resolve("root-dir")));
        final DeployDistributionCommand deployDistributionCommand = new DeployDistributionCommand(platform,
            extractDirectoryPath, installDirectoryPath, distributionFilePath);

        usecase.execute(deployDistributionCommand);

        final ArgumentCaptor<Path> sourcePathArgumentCaptor = ArgumentCaptor.forClass(Path.class);
        verify(fileManager).moveFileTree(sourcePathArgumentCaptor.capture(), eq(installDirectoryPath));
        assertThat(sourcePathArgumentCaptor.getValue()).hasParentRaw(extractDirectoryPath);
        verify(fileManager).deleteIfExists(extractDirectoryPath);
        verifyNoMoreInteractions(fileManager, archiverProvider, archiver);
    }

    /**
     * Archiver answer that emulates archive exploding.
     */
    private static class ArchiverAnswer implements Answer<Void> {

        @Override
        public Void answer(final InvocationOnMock invocation) throws Throwable {
            final ExplodeCommand settings = invocation.getArgument(0);
            final Path copyDestDirectory = settings
                .getTargetDirectoryPath()
                .resolve(PathUtils.removeExtension(settings.getArchiveFilePath()));
            Files.createDirectories(copyDestDirectory);
            Files.copy(settings.getArchiveFilePath(), copyDestDirectory.resolve("node"));
            return null;
        }
    }
}
