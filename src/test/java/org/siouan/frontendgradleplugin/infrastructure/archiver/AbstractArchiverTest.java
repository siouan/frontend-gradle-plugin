package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.infrastructure.provider.fixture.ArchiveEntryImpl.newDirectoryEntry;
import static org.siouan.frontendgradleplugin.infrastructure.provider.fixture.ArchiveEntryImpl.newFileEntry;
import static org.siouan.frontendgradleplugin.infrastructure.provider.fixture.ArchiveEntryImpl.newSymbolicLinkEntry;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DirectoryNotFoundException;
import org.siouan.frontendgradleplugin.domain.exception.SlipAttackException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedEntryException;
import org.siouan.frontendgradleplugin.domain.model.ArchiverContext;
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.util.SystemUtils;
import org.siouan.frontendgradleplugin.infrastructure.provider.fixture.ArchiveEntryImpl;
import org.siouan.frontendgradleplugin.infrastructure.provider.fixture.ArchiverImpl;
import org.siouan.frontendgradleplugin.test.util.Helper;

/**
 * Unit tests for the {@link AbstractArchiver} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
public class AbstractArchiverTest {

    @TempDir
    Path targetDirectory;

    @Mock
    private ArchiverContext context;

    private List<ArchiveEntryImpl> entries;

    @BeforeEach
    void setUp() {
        entries = new ArrayList<>();
    }

    @Test
    void shouldFailExplodingWhenTargetDirectoryDoesNotExist() {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory.resolve("unknowndir"), platform);

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings)).isInstanceOf(
            DirectoryNotFoundException.class);
    }

    @Test
    void shouldFailExplodingWhenTargetIsNotADirectory() throws IOException {
        final Path targetFile = Files.createFile(targetDirectory.resolve("afile"));
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetFile, platform);

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings)).isInstanceOf(
            DirectoryNotFoundException.class);
        assertThat(targetFile).exists();
    }

    @Test
    void shouldFailExplodingWhenSlipAttackIsDetected() {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory, platform);
        when(context.getSettings()).thenReturn(settings);
        entries.add(ArchiveEntryImpl.newDirectoryEntry("../out-file", 0));

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings)).isInstanceOf(
            SlipAttackException.class);
    }

    @Test
    void shouldFailExplodingWhenContentInitializationFails() throws ArchiverException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory, platform);
        final ArchiverException expectedException = mock(ArchiverException.class);

        assertThatThrownBy(() -> new ArchiverImpl(context, entries, expectedException).explode(settings)).isEqualTo(
            expectedException);
        verify(context, never()).close();
    }

    @Test
    void shouldFailExplodingWhenEntryCannotBeWritten() throws ArchiverException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory, platform);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newFileEntry("unwritable-file", 0777, null));
        final ArchiverException expectedException = mock(ArchiverException.class);

        assertThatThrownBy(() -> new ArchiverImpl(context, entries, expectedException).explode(settings))
            .isInstanceOf(ArchiverException.class)
            .hasCause(expectedException);
        verify(context).close();
    }

    @Test
    void shouldFailExplodingWhenArchiveContentIsInvalid() throws ArchiverException {
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory, platform);
        when(context.getSettings()).thenReturn(settings);
        entries.add(ArchiveEntryImpl.newUnknownEntry("name"));

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings)).isInstanceOf(
            UnsupportedEntryException.class);
        verify(context).close();
    }

    @Test
    void shouldExplodeArchive() throws IOException, UnsupportedEntryException, SlipAttackException, ArchiverException {
        final Path targetPath = targetDirectory;
        final Platform platform = new Platform(SystemUtils.getSystemJvmArch(), SystemUtils.getSystemOsName());
        final ExplodeSettings settings = new ExplodeSettings(null, targetPath, platform);
        when(context.getSettings()).thenReturn(settings);
        entries.add(newDirectoryEntry("empty-dir/nested-empty-dir", 0700));
        entries.add(newDirectoryEntry("root-dir", 0750));
        entries.add(newDirectoryEntry("root-dir/nested-dir", 0750));
        entries.add(newFileEntry("root-dir/nested-dir/nested-file", 0652, "nested"));
        entries.add(newDirectoryEntry("root-dir/empty-dir", 0555));
        entries.add(newFileEntry("root-file", 0403, "root"));
        if (!platform.isWindowsOs()) {
            entries.add(newSymbolicLinkEntry("root-dir/invalid-symlink", "unknown-target"));
            entries.add(newSymbolicLinkEntry("root-dir/symlink-to-nested-file", "nested-dir/nested-file"));
            entries.add(newSymbolicLinkEntry("root-dir/symlink-to-empty-dir", "../root-dir/empty-dir"));
        }

        new ArchiverImpl(context, entries).explode(settings);

        // Entry 'empty-dir/nested-empty-dir/'
        final Path rootEmptyDirectory = targetPath.resolve("empty-dir");
        assertThat(Files.isDirectory(rootEmptyDirectory, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(Helper.getDirectorySize(rootEmptyDirectory)).isEqualTo(1);
        final Path nestedDirectoryInRootEmptyDirectory = rootEmptyDirectory.resolve("nested-empty-dir");
        assertThat(Files.isDirectory(nestedDirectoryInRootEmptyDirectory, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(nestedDirectoryInRootEmptyDirectory.toFile().list()).isEmpty();
        // Entry 'root-dir/'
        final Path rootDirectory = targetPath.resolve("root-dir");
        assertThat(Files.isDirectory(rootDirectory, LinkOption.NOFOLLOW_LINKS)).isTrue();
        if (platform.isWindowsOs()) {
            assertThat(Helper.getDirectorySize(rootDirectory)).isEqualTo(2);
        } else {
            assertThat(Helper.getDirectorySize(rootDirectory)).isEqualTo(5);
        }
        // Entry '+--> nested-dir/'
        final Path nestedDirectory = rootDirectory.resolve("nested-dir");
        assertThat(Files.isDirectory(nestedDirectory, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(Helper.getDirectorySize(nestedDirectory)).isEqualTo(1);
        // Entry '+--+--> nested-file'
        final Path nestedFile = nestedDirectory.resolve("nested-file");
        assertThat(Files.isRegularFile(nestedFile, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(Files.size(nestedFile)).isEqualTo(6);
        // Entry '+--> empty-dir/'
        final Path emptyDirectory = rootDirectory.resolve("empty-dir");
        assertThat(Files.isDirectory(emptyDirectory, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(emptyDirectory.toFile().list()).isEmpty();
        // Entry 'root-file'
        final Path rootFile = targetPath.resolve("root-file");
        assertThat(Files.isRegularFile(rootFile, LinkOption.NOFOLLOW_LINKS)).isTrue();
        assertThat(Files.size(rootFile)).isEqualTo(4);

        if (!platform.isWindowsOs()) {
            // Entry '+--> invalid-symlink'
            final Path invalidSymlink = rootDirectory.resolve("invalid-symlink");
            assertThat(Files.isSymbolicLink(invalidSymlink)).isTrue();
            assertThat(Files.exists(invalidSymlink)).isFalse();
            // Entry '+--> symlink-to-nested-file'
            final Path symlinkToNestedFile = rootDirectory.resolve("symlink-to-nested-file");
            assertThat(Files.isSymbolicLink(symlinkToNestedFile)).isTrue();
            assertThat(Files.isSameFile(symlinkToNestedFile, nestedFile)).isTrue();
            // Entry '+--> symlink-to-empty-dir'
            final Path symlinkToEmptyDirectory = rootDirectory.resolve("symlink-to-empty-dir");
            assertThat(Files.isSymbolicLink(symlinkToEmptyDirectory)).isTrue();
            assertThat(Files.isDirectory(symlinkToEmptyDirectory)).isTrue();
            assertThat(Files.isSameFile(symlinkToEmptyDirectory, emptyDirectory)).isTrue();

            final Map<Path, Set<PosixFilePermission>> expectedPermissions = new HashMap<>();
            expectedPermissions.put(nestedDirectoryInRootEmptyDirectory, PosixFilePermissions.fromString("rwx------"));
            expectedPermissions.put(rootDirectory, PosixFilePermissions.fromString("rwxr-x---"));
            expectedPermissions.put(nestedDirectory, PosixFilePermissions.fromString("rwxr-x---"));
            expectedPermissions.put(nestedFile, PosixFilePermissions.fromString("rw-r-x-w-"));
            expectedPermissions.put(emptyDirectory, PosixFilePermissions.fromString("r-xr-xr-x"));
            expectedPermissions.put(rootFile, PosixFilePermissions.fromString("r------wx"));
            assertFilesHavePermissions(expectedPermissions);
        }

        verify(context).close();
    }

    private void assertFilesHavePermissions(final Map<Path, Set<PosixFilePermission>> expectedPermissions) {
        expectedPermissions.forEach((path, expectedPermissionsForFile) -> {
            try {
                final Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(path,
                    LinkOption.NOFOLLOW_LINKS);
                assertThat(permissions)
                    .withFailMessage(
                        "\nExpecting:\n  <%s>\nto contain exactly in any order:\n  <%s>\nfor file:\n  <%s>",
                        permissions, expectedPermissionsForFile, path)
                    .containsExactlyInAnyOrderElementsOf(expectedPermissionsForFile);
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }
}
