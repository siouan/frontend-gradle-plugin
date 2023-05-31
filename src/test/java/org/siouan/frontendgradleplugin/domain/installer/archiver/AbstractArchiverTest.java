package org.siouan.frontendgradleplugin.domain.installer.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiveEntryImpl.newDirectoryEntry;
import static org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiveEntryImpl.newFileEntry;
import static org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiveEntryImpl.newSymbolicLinkEntry;
import static org.siouan.frontendgradleplugin.domain.installer.archiver.ArchiveEntryImpl.newUnknownEntry;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.LOCAL_PLATFORM;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.test.fixture.SystemPropertiesFixture.getSystemJvmArch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.FileManager;

@ExtendWith(MockitoExtension.class)
class AbstractArchiverTest {

    private static final String ARCHIVE_FILENAME = "archive.zip";

    @TempDir
    Path temporaryDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private ArchiverContext context;

    private Path archiveFilePath;

    private List<ArchiveEntryImpl> entries;

    @BeforeEach
    void setUp() {
        entries = new ArrayList<>();
        archiveFilePath = Paths.get(ARCHIVE_FILENAME);
    }

    @Test
    void should_fail_exploding_when_target_directory_does_not_exist() {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(false);
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(explodeCommand)).isInstanceOf(
            DirectoryNotFoundException.class);

        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void should_fail_exploding_when_context_initialization_fails() {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();
        final ArchiverException expectedException = mock(ArchiverException.class);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries, expectedException).explode(
            explodeCommand)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void should_fail_exploding_when_slip_attack_is_detected() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        entries.add(ArchiveEntryImpl.newDirectoryEntry("../out-file", 0));
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();
        when(context.getExplodeCommand()).thenReturn(explodeCommand);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(explodeCommand)).isInstanceOf(
            SlipAttackException.class);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void should_fail_exploding_when_entry_cannot_be_written() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();
        when(context.getExplodeCommand()).thenReturn(explodeCommand);
        // Unix mode is 0777
        entries.add(newFileEntry("unwritable-file", 511, null));
        final IOException expectedException = new IOException();

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries, expectedException).explode(
            explodeCommand)).isEqualTo(expectedException);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void should_fail_exploding_when_archive_content_is_invalid() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();
        when(context.getExplodeCommand()).thenReturn(explodeCommand);
        entries.add(newUnknownEntry("name"));

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(explodeCommand)).isInstanceOf(
            UnsupportedEntryException.class);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void should_fail_exploding_when_symbolic_link_entry_has_invalid_relativized_target_file_path() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(LOCAL_PLATFORM)
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();
        when(context.getExplodeCommand()).thenReturn(explodeCommand);
        entries.add(newSymbolicLinkEntry("unsupported-symlink", "../unsupported-symlink/../target"));
        final Path relativizedTargetFilePath = Paths.get("../target");
        when(fileManager.isSameFile(any(Path.class), any(Path.class))).thenReturn(false);
        when(fileManager.exists(any(Path.class))).thenReturn(true);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(explodeCommand)).isInstanceOf(
            InvalidRelativizedSymbolicLinkTargetException.class);

        final ArgumentCaptor<Path> pathArgumentCaptor = ArgumentCaptor.forClass(Path.class);
        verify(fileManager).isSameFile(pathArgumentCaptor.capture(), eq(relativizedTargetFilePath));
        assertThat(pathArgumentCaptor.getValue()).endsWithRaw(relativizedTargetFilePath);
        verify(fileManager).exists(pathArgumentCaptor.getValue());
        verify(context).close();
        verify(fileManager).createSymbolicLink(temporaryDirectoryPath.resolve("unsupported-symlink"),
            relativizedTargetFilePath);
        verifyNoMoreInteractions(fileManager, context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"linux", "mac os", "windows"})
    void should_explode_archive(final String osName) throws IOException, ArchiverException {
        final Path emptyDirPath = temporaryDirectoryPath.resolve("empty-dir");
        final Path nestedEmptyDirPath = emptyDirPath.resolve("nested-empty-dir");
        final Path rootDirPath = temporaryDirectoryPath.resolve("root-dir");
        final Path nestedDirPath = rootDirPath.resolve("nested-dir");
        final Path nestedFilePath = nestedDirPath.resolve("nested-file");
        final Path otherEmptyDirPath = rootDirPath.resolve("other-empty-dir");
        final Path rootFilePath = temporaryDirectoryPath.resolve("root-file");
        final ExplodeCommand explodeCommand = ExplodeCommand
            .builder()
            .platform(aPlatform(getSystemJvmArch(), osName))
            .archiveFilePath(archiveFilePath)
            .targetDirectoryPath(temporaryDirectoryPath)
            .build();

        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        when(context.getExplodeCommand()).thenReturn(explodeCommand);
        // Unix mode is 0700
        entries.add(newDirectoryEntry("empty-dir/nested-empty-dir", 448));
        when(fileManager.isDirectory(emptyDirPath)).thenReturn(false);
        // Unix mode is 0750
        entries.add(newDirectoryEntry("root-dir", 488));
        when(fileManager.isDirectory(rootDirPath)).thenReturn(true);
        // Unix mode is 0750
        entries.add(newDirectoryEntry("root-dir/nested-dir", 488));
        when(fileManager.isDirectory(nestedDirPath)).thenReturn(true);
        // Unix mode is 0652
        entries.add(newFileEntry("root-dir/nested-dir/nested-file", 426, "nested"));
        // Unix mode is 0555
        entries.add(newDirectoryEntry("root-dir/other-empty-dir", 365));
        // Unix mode is 0403
        entries.add(newFileEntry("root-file", 259, "root"));

        if (!explodeCommand.getPlatform().isWindowsOs()) {
            entries.add(newSymbolicLinkEntry("root-dir/invalid-symlink", "unknown-target"));
            entries.add(newSymbolicLinkEntry("root-dir/symlink-to-nested-file", "nested-dir/nested-file"));
            entries.add(newSymbolicLinkEntry("root-dir/symlink-to-empty-dir", "../root-dir/other-empty-dir"));
            when(fileManager.isSameFile(any(Path.class), any(Path.class))).then(invocation -> {
                if (((Path) invocation.getArgument(0)).endsWith("unknown-target") && ((Path) invocation.getArgument(
                    1)).endsWith("unknown-target")) {
                    return false;
                }
                if (((Path) invocation.getArgument(0)).endsWith("nested-dir/nested-file")
                    && ((Path) invocation.getArgument(1)).endsWith("nested-dir/nested-file")) {
                    return true;
                }
                if (((Path) invocation.getArgument(0)).endsWith("other-empty-dir") && ((Path) invocation.getArgument(
                    1)).endsWith("other-empty-dir")) {
                    return true;
                }
                return false;
            });
            // When processing "root-dir/invalid-symlink" entry and its unknown target
            when(fileManager.exists(argThat(path -> path.endsWith("unknown-target")))).thenReturn(false);
        }
        final ArchiverImpl archiver = new ArchiverImpl(fileManager, context, entries);

        archiver.explode(explodeCommand);

        final Map<Path, String> actualWrittenFilePaths = archiver.getWrittenFilePaths();
        final Map<Path, String> expectedWrittenFilePaths = Map.of(nestedFilePath, "nested", rootFilePath, "root");
        assertThat(actualWrittenFilePaths).containsExactlyInAnyOrderEntriesOf(expectedWrittenFilePaths);

        // When processing "empty-dir/nested-empty-dir" entry.
        verify(fileManager).createDirectories(emptyDirPath);
        verify(fileManager).createDirectory(nestedEmptyDirPath);

        // When processing "root-dir" entry.
        verify(fileManager).createDirectory(rootDirPath);

        // When processing "root-dir/nested-dir" entry.
        verify(fileManager).createDirectory(nestedDirPath);

        // When processing "root-dir/other-empty-dir" entry.
        verify(fileManager).createDirectory(otherEmptyDirPath);

        if (!explodeCommand.getPlatform().isWindowsOs()) {
            verify(fileManager).createSymbolicLink(rootDirPath.resolve("invalid-symlink"), Paths.get("unknown-target"));
            verify(fileManager).createSymbolicLink(rootDirPath.resolve("symlink-to-nested-file"),
                Paths.get("nested-dir/nested-file"));
            verify(fileManager).createSymbolicLink(rootDirPath.resolve("symlink-to-empty-dir"),
                Paths.get("other-empty-dir"));

            verify(fileManager).setPosixFilePermissions(nestedEmptyDirPath,
                PosixFilePermissions.fromString("rwx------"));
            verify(fileManager).setPosixFilePermissions(rootDirPath, PosixFilePermissions.fromString("rwxr-x---"));
            verify(fileManager).setPosixFilePermissions(nestedDirPath, PosixFilePermissions.fromString("rwxr-x---"));
            verify(fileManager).setPosixFilePermissions(nestedFilePath, PosixFilePermissions.fromString("rw-r-x-w-"));
            verify(fileManager).setPosixFilePermissions(otherEmptyDirPath,
                PosixFilePermissions.fromString("r-xr-xr-x"));
            verify(fileManager).setPosixFilePermissions(rootFilePath, PosixFilePermissions.fromString("r------wx"));
        }

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }
}
