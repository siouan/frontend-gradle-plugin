package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.core.archivers.AbstractArchiverTest.ArchiveEntryImpl.newDirectoryEntry;
import static org.siouan.frontendgradleplugin.core.archivers.AbstractArchiverTest.ArchiveEntryImpl.newFileEntry;
import static org.siouan.frontendgradleplugin.core.archivers.AbstractArchiverTest.ArchiveEntryImpl.newSymbolicLinkEntry;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;
import org.siouan.frontendgradleplugin.core.Utils;
import org.siouan.frontendgradleplugin.util.Helper;

/**
 * Unit tests for the {@link AbstractArchiver} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class AbstractArchiverTest {

    @TempDir
    File targetDirectory;

    @Mock
    private ArchiverContext context;

    private List<ArchiveEntryImpl> entries;

    @BeforeEach
    void setUp() {
        entries = new ArrayList<>();
    }

    @Test
    void shouldFailWhenTargetDirectoryDoesNotExist() {
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory.toPath().resolve("unknowndir"),
            Utils.getSystemOsName());

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings))
            .isInstanceOf(DirectoryNotFoundException.class);
    }

    @Test
    void shouldFailWhenTargetIsNotADirectory() throws IOException {
        final Path targetFile = Files.createFile(targetDirectory.toPath().resolve("afile"));
        final ExplodeSettings settings = new ExplodeSettings(null, targetFile, Utils.getSystemOsName());

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings))
            .isInstanceOf(DirectoryNotFoundException.class);
        assertThat(targetFile).exists();
    }

    @Test
    void shouldFailWhenSlipAttackIsDetected() {
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory.toPath(), Utils.getSystemOsName());
        when(context.getSettings()).thenReturn(settings);
        entries.add(ArchiveEntryImpl.newDirectoryEntry("../out-file", 0));

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings))
            .isInstanceOf(SlipAttackException.class);
    }

    @Test
    void shouldFailWhenArchiveContentIsInvalid() {
        final ExplodeSettings settings = new ExplodeSettings(null, targetDirectory.toPath(), Utils.getSystemOsName());
        when(context.getSettings()).thenReturn(settings);
        entries.add(ArchiveEntryImpl.newUnknownEntry("name"));

        assertThatThrownBy(() -> new ArchiverImpl(context, entries).explode(settings))
            .isInstanceOf(UnsupportedEntryException.class);
    }

    @Test
    void shouldExplodeArchive() throws IOException, UnsupportedEntryException, SlipAttackException, ArchiverException {
        final Path targetPath = targetDirectory.toPath();
        final ExplodeSettings settings = new ExplodeSettings(null, targetPath, Utils.getSystemOsName());
        when(context.getSettings()).thenReturn(settings);
        entries.add(newDirectoryEntry("empty-dir/nested-empty-dir", 0700));
        entries.add(newDirectoryEntry("root-dir", 0750));
        entries.add(newDirectoryEntry("root-dir/nested-dir", 0750));
        entries.add(newFileEntry("root-dir/nested-dir/nested-file", 0652, "nested"));
        entries.add(newDirectoryEntry("root-dir/empty-dir", 0555));
        entries.add(newFileEntry("root-file", 0403, "root"));
        if (!Utils.isWindowsOs(Utils.getSystemOsName())) {
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
        if (Utils.isWindowsOs(Utils.getSystemOsName())) {
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

        if (!Utils.isWindowsOs(Utils.getSystemOsName())) {
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
    }

    private void assertFilesHavePermissions(final Map<Path, Set<PosixFilePermission>> expectedPermissions) {
        expectedPermissions.forEach((path, expectedPermissionsForFile) -> {
            try {
                final Set<PosixFilePermission> permissions = Files
                    .getPosixFilePermissions(path, LinkOption.NOFOLLOW_LINKS);
                assertThat(permissions).withFailMessage(
                    "\nExpecting:\n  <%s>\nto contain exactly in any order:\n  <%s>\nfor file:\n  <%s>", permissions,
                    expectedPermissionsForFile, path).containsExactlyInAnyOrderElementsOf(expectedPermissionsForFile);
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    /**
     * Test implementation of an archive entry.
     */
    static class ArchiveEntryImpl implements ArchiveEntry {

        private final String name;

        private final ArchiveEntryType type;

        private final int unixMode;

        private final String data;

        ArchiveEntryImpl(final String name, final ArchiveEntryType type, final int unixMode, final String data) {
            this.name = name;
            this.type = type;
            this.unixMode = unixMode;
            this.data = data;
        }

        static ArchiveEntryImpl newSymbolicLinkEntry(final String name, final String targetPath) {
            return new ArchiveEntryImpl(name, ArchiveEntryType.SYMBOLIC_LINK, 0, targetPath);
        }

        static ArchiveEntryImpl newDirectoryEntry(final String name, final int unixMode) {
            return new ArchiveEntryImpl(name, ArchiveEntryType.DIRECTORY, unixMode, null);
        }

        static ArchiveEntryImpl newFileEntry(final String name, final int unixMode, final String content) {
            return new ArchiveEntryImpl(name, ArchiveEntryType.FILE, unixMode, content);
        }

        static ArchiveEntryImpl newUnknownEntry(final String name) {
            return new ArchiveEntryImpl(name, ArchiveEntryType.UNKNOWN, 0, null);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isSymbolicLink() {
            return type.equals(ArchiveEntryType.SYMBOLIC_LINK);
        }

        @Override
        public boolean isDirectory() {
            return type.equals(ArchiveEntryType.DIRECTORY);
        }

        @Override
        public boolean isFile() {
            return type.equals(ArchiveEntryType.FILE);
        }

        @Override
        public int getUnixMode() {
            return unixMode;
        }

        String getData() {
            return data;
        }

        /**
         * Internal type of an entry.
         */
        enum ArchiveEntryType {

            SYMBOLIC_LINK, DIRECTORY, FILE, UNKNOWN
        }
    }

    /**
     * Test implementation of an archiver.
     */
    private static class ArchiverImpl extends AbstractArchiver<ArchiverContext, ArchiveEntryImpl> {

        private final ArchiverContext context;

        private final Iterator<ArchiveEntryImpl> entries;

        ArchiverImpl(final ArchiverContext context, final Collection<ArchiveEntryImpl> entries) {
            this.context = context;
            this.entries = entries.iterator();
        }

        @Override
        ArchiverContext initializeContext(final ExplodeSettings settings) {
            return context;
        }

        @Override
        Optional<ArchiveEntryImpl> getNextEntry(final ArchiverContext context) {
            return Optional.ofNullable(entries.hasNext() ? entries.next() : null);
        }

        @Override
        String getSymbolicLinkTarget(final ArchiverContext context, final ArchiveEntryImpl entry) {
            return entry.getData();
        }

        @Override
        void writeRegularFile(final ArchiverContext context, final ArchiveEntryImpl entry, final Path targetFile)
            throws IOException {
            try (final PrintWriter writer = new PrintWriter(targetFile.toFile())) {
                writer.print(entry.getData());
            }
        }
    }
}
