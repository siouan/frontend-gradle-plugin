package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.core.ExplodeSettings;

/**
 * Unit tests for the {@link ZipArchiverContext} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class ZipArchiverContextTest {

    @Mock
    ZipFile zipFile;

    @Mock
    private ExplodeSettings settings;

    @Test
    void shouldFailWhenClosingContextWithIOException() throws IOException {
        final Enumeration<ZipArchiveEntry> entries = mock(Enumeration.class);
        when(zipFile.getEntries()).thenReturn(entries);
        final Exception expectedException = mock(IOException.class);
        doThrow(expectedException).when(zipFile).close();

        final ZipArchiverContext context = new ZipArchiverContext(settings, zipFile);
        assertThatThrownBy(context::close).isInstanceOf(ArchiverException.class).hasCause(expectedException);

        assertThat(context.getSettings()).isEqualTo(settings);
        assertThat(context.getEntries()).isEqualTo(entries);
        assertThat(context.getZipFile()).isEqualTo(zipFile);
        verify(zipFile).getEntries();
        verify(zipFile).close();
        verifyNoMoreInteractions(zipFile);
    }

    @Test
    void shouldCloseContext() throws IOException, ArchiverException {
        final Enumeration<ZipArchiveEntry> entries = mock(Enumeration.class);
        when(zipFile.getEntries()).thenReturn(entries);
        final ZipArchiverContext context = new ZipArchiverContext(settings, zipFile);
        context.close();

        assertThat(context.getSettings()).isEqualTo(settings);
        assertThat(context.getEntries()).isEqualTo(entries);
        assertThat(context.getZipFile()).isEqualTo(zipFile);
        verify(zipFile).getEntries();
        verify(zipFile).close();
        verifyNoMoreInteractions(zipFile);
    }
}
