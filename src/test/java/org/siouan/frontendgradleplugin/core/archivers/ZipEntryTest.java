package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link ZipEntry} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class ZipEntryTest {

    private static final String NAME = "entry-name";

    private static final int UNIX_MODE = 287;

    @Mock
    private ZipArchiveEntry lowLevelEntry;

    @Test
    void shouldMapEntryToDirectoryArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = true;
        final boolean isSymbolicLink = false;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isUnixSymlink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.getUnixMode()).thenReturn(unixMode);

        final ZipEntry entry = new ZipEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(false);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }

    @Test
    void shouldMapEntryToSymbolicLinkArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = false;
        final boolean isSymbolicLink = true;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isUnixSymlink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.getUnixMode()).thenReturn(unixMode);

        final ZipEntry entry = new ZipEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(false);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }

    @Test
    void shouldMapEntryToFileArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = false;
        final boolean isSymbolicLink = false;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isUnixSymlink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.getUnixMode()).thenReturn(unixMode);

        final ZipEntry entry = new ZipEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(true);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }
}
