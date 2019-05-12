package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link TarEntry} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class TarEntryTest {

    private static final String NAME = "entry-name";

    private static final int UNIX_MODE = 287;

    @Mock
    private TarArchiveEntry lowLevelEntry;

    @Test
    void shouldMapEntryToDirectoryArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = true;
        final boolean isSymbolicLink = false;
        final boolean isFile = false;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isSymbolicLink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.isFile()).thenReturn(isFile);
        when(lowLevelEntry.getMode()).thenReturn(unixMode);

        final TarEntry entry = new TarEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(isFile);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }

    @Test
    void shouldMapEntryToSymbolicLinkArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = false;
        final boolean isSymbolicLink = true;
        final boolean isFile = false;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isSymbolicLink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.isFile()).thenReturn(isFile);
        when(lowLevelEntry.getMode()).thenReturn(unixMode);

        final TarEntry entry = new TarEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(isFile);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }

    @Test
    void shouldMapEntryToFileArchiveEntry() {
        final String name = NAME;
        final int unixMode = UNIX_MODE;
        final boolean isDirectory = false;
        final boolean isSymbolicLink = false;
        final boolean isFile = true;
        when(lowLevelEntry.getName()).thenReturn(name);
        when(lowLevelEntry.isDirectory()).thenReturn(isDirectory);
        when(lowLevelEntry.isSymbolicLink()).thenReturn(isSymbolicLink);
        when(lowLevelEntry.isFile()).thenReturn(isFile);
        when(lowLevelEntry.getMode()).thenReturn(unixMode);

        final TarEntry entry = new TarEntry(lowLevelEntry);

        assertThat(entry.getLowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(name);
        assertThat(entry.isDirectory()).isEqualTo(isDirectory);
        assertThat(entry.isSymbolicLink()).isEqualTo(isSymbolicLink);
        assertThat(entry.isFile()).isEqualTo(isFile);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
    }
}
