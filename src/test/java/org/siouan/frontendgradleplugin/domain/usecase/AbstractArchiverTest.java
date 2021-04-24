package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.fixture.ArchiveEntryImpl.newDirectoryEntry;
import static org.siouan.frontendgradleplugin.test.fixture.ArchiveEntryImpl.newFileEntry;
import static org.siouan.frontendgradleplugin.test.fixture.ArchiveEntryImpl.newSymbolicLinkEntry;
import static org.siouan.frontendgradleplugin.test.fixture.ArchiveEntryImpl.newUnknownEntry;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DirectoryNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidRelativizedSymbolicLinkTargetException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.test.fixture.ArchiveEntryImpl;
import org.siouan.frontendgradleplugin.test.fixture.ArchiverImpl;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

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
    void shouldFailExplodingWhenTargetDirectoryDoesNotExist() {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(false);
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(settings))
            .isInstanceOf(DirectoryNotFoundException.class);

        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void shouldFailExplodingWhenContextInitializationFails() {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);
        final ArchiverException expectedException = mock(ArchiverException.class);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries, expectedException).explode(settings))
            .isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void shouldFailExplodingWhenSlipAttackIsDetected() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        entries.add(ArchiveEntryImpl.newDirectoryEntry("../out-file", 0));
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);
        when(context.getSettings()).thenReturn(settings);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(settings))
            .isInstanceOf(SlipAttackException.class);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void shouldFailExplodingWhenEntryCannotBeWritten() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newFileEntry("unwritable-file", 0777, null));
        final IOException expectedException = new IOException();

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries, expectedException).explode(settings))
            .isEqualTo(expectedException);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void shouldFailExplodingWhenArchiveContentIsInvalid() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newUnknownEntry("name"));

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(settings))
            .isInstanceOf(UnsupportedEntryException.class);

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }

    @Test
    void shouldFailExplodingWhenSymbolicLinkEntryHasInvalidRelativizedTargetFilePath() throws IOException {
        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        final ExplodeSettings settings = new ExplodeSettings(PlatformFixture.LOCAL_PLATFORM, archiveFilePath,
            temporaryDirectoryPath);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newSymbolicLinkEntry("unsupported-symlink", "../unsupported-symlink/../target"));
        final Path relativizedTargetFilePath = Paths.get("../target");
        final Path normalizedAndRelativizedTargetFilePath = Paths.get("../target");
        when(fileManager.isSameFile(argThat(path -> path.endsWith(relativizedTargetFilePath)),
            eq(normalizedAndRelativizedTargetFilePath))).thenReturn(false);
        when(fileManager.exists(argThat(path -> path.endsWith(relativizedTargetFilePath)))).thenReturn(true);

        assertThatThrownBy(() -> new ArchiverImpl(fileManager, context, entries).explode(settings))
            .isInstanceOf(InvalidRelativizedSymbolicLinkTargetException.class);

        verify(context).close();
        verify(fileManager)
            .createSymbolicLink(temporaryDirectoryPath.resolve("unsupported-symlink"),
                normalizedAndRelativizedTargetFilePath);
        verifyNoMoreInteractions(fileManager, context);
    }

    @ParameterizedTest
    @ValueSource(strings = {"linux", "mac os", "windows"})
    void shouldExplodeArchive(final String osName) throws IOException, ArchiverException {
        final Path emptyDirPath = temporaryDirectoryPath.resolve("empty-dir");
        final Path nestedEmptyDirPath = emptyDirPath.resolve("nested-empty-dir");
        final Path rootDirPath = temporaryDirectoryPath.resolve("root-dir");
        final Path nestedDirPath = rootDirPath.resolve("nested-dir");
        final Path nestedFilePath = nestedDirPath.resolve("nested-file");
        final Path otherEmptyDirPath = rootDirPath.resolve("other-empty-dir");
        final Path rootFilePath = temporaryDirectoryPath.resolve("root-file");
        final ExplodeSettings settings = new ExplodeSettings(
            PlatformFixture.aPlatform(SystemUtils.getSystemJvmArch(), osName), archiveFilePath, temporaryDirectoryPath);

        when(fileManager.isDirectory(temporaryDirectoryPath)).thenReturn(true);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newDirectoryEntry("empty-dir/nested-empty-dir", 0700));
        when(fileManager.isDirectory(emptyDirPath)).thenReturn(false);
        entries.add(newDirectoryEntry("root-dir", 0750));
        when(fileManager.isDirectory(rootDirPath)).thenReturn(true);
        entries.add(newDirectoryEntry("root-dir/nested-dir", 0750));
        when(fileManager.isDirectory(nestedDirPath)).thenReturn(true);
        entries.add(newFileEntry("root-dir/nested-dir/nested-file", 0652, "nested"));
        entries.add(newDirectoryEntry("root-dir/other-empty-dir", 0555));
        entries.add(newFileEntry("root-file", 0403, "root"));

        if (!settings.getPlatform().isWindowsOs()) {
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
                if (((Path) invocation.getArgument(0)).endsWith("other-empty-dir") && ((Path) invocation.getArgument(1))
                    .endsWith("other-empty-dir")) {
                    return true;
                }
                return false;
            });
            // When processing "root-dir/invalid-symlink" entry and its unknown target
            when(fileManager.exists(argThat(path -> path.endsWith("unknown-target")))).thenReturn(false);
        }
        final ArchiverImpl archiver = new ArchiverImpl(fileManager, context, entries);

        archiver.explode(settings);

        final Map<Path, String> actualWrittenFilePaths = archiver.getWrittenFilePaths();
        final Map<Path, String> expectedWrittenFilePaths = new HashMap<>();
        expectedWrittenFilePaths.put(nestedFilePath, "nested");
        expectedWrittenFilePaths.put(rootFilePath, "root");
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

        if (!settings.getPlatform().isWindowsOs()) {
            verify(fileManager).createSymbolicLink(rootDirPath.resolve("invalid-symlink"), Paths.get("unknown-target"));
            verify(fileManager)
                .createSymbolicLink(rootDirPath.resolve("symlink-to-nested-file"), Paths.get("nested-dir/nested-file"));
            verify(fileManager)
                .createSymbolicLink(rootDirPath.resolve("symlink-to-empty-dir"), Paths.get("other-empty-dir"));

            verify(fileManager)
                .setPosixFilePermissions(nestedEmptyDirPath, PosixFilePermissions.fromString("rwx------"));
            verify(fileManager).setPosixFilePermissions(rootDirPath, PosixFilePermissions.fromString("rwxr-x---"));
            verify(fileManager).setPosixFilePermissions(nestedDirPath, PosixFilePermissions.fromString("rwxr-x---"));
            verify(fileManager).setPosixFilePermissions(nestedFilePath, PosixFilePermissions.fromString("rw-r-x-w-"));
            verify(fileManager)
                .setPosixFilePermissions(otherEmptyDirPath, PosixFilePermissions.fromString("r-xr-xr-x"));
            verify(fileManager).setPosixFilePermissions(rootFilePath, PosixFilePermissions.fromString("r------wx"));
        }

        verify(context).close();
        verifyNoMoreInteractions(fileManager, context);
    }
}
