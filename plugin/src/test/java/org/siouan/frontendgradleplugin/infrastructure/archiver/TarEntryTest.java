package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

    @ParameterizedTest
    @MethodSource("generateArguments")
    void should_map_entry_to_directory_archive_entry(final String entryName, final int unixMode,
        final boolean directory, final boolean symbolicLink, final boolean regularFile) {
        when(lowLevelEntry.getName()).thenReturn(entryName);
        when(lowLevelEntry.getMode()).thenReturn(unixMode);
        when(lowLevelEntry.isDirectory()).thenReturn(directory);
        when(lowLevelEntry.isSymbolicLink()).thenReturn(symbolicLink);
        when(lowLevelEntry.isFile()).thenReturn(regularFile);

        final TarEntry entry = new TarEntry(lowLevelEntry);

        assertThat(entry.lowLevelEntry()).isEqualTo(lowLevelEntry);
        assertThat(entry.getName()).isEqualTo(entryName);
        assertThat(entry.getUnixMode()).isEqualTo(unixMode);
        assertThat(entry.isDirectory()).isEqualTo(directory);
        assertThat(entry.isSymbolicLink()).isEqualTo(symbolicLink);
        assertThat(entry.isFile()).isEqualTo(regularFile);
    }

    private static Stream<Arguments> generateArguments() {
        return Stream.of(Arguments.of(NAME, UNIX_MODE, true, false, false),
            Arguments.of(NAME, UNIX_MODE, false, true, false), Arguments.of(NAME, UNIX_MODE, false, false, true));
    }
}
