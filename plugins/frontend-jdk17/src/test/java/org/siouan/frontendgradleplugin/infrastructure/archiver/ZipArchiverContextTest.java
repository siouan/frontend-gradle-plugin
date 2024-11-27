package org.siouan.frontendgradleplugin.infrastructure.archiver;

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
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

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
    private ExplodeCommand settings;

    @Mock
    private Enumeration<ZipArchiveEntry> entries;

    @Test
    void should_fail_when_closing_context_with_io_exception() throws IOException {
        when(zipFile.getEntries()).thenReturn(entries);
        final Exception expectedException = mock(IOException.class);
        doThrow(expectedException).when(zipFile).close();
        final ZipArchiverContext context = new ZipArchiverContext(settings, zipFile);

        assertThatThrownBy(context::close).isEqualTo(expectedException);

        assertThat(context.getExplodeCommand()).isEqualTo(settings);
        assertThat(context.getEntries()).isEqualTo(entries);
        assertThat(context.getZipFile()).isEqualTo(zipFile);
        verifyNoMoreInteractions(zipFile);
    }

    @Test
    void should_close_context() throws IOException {
        when(zipFile.getEntries()).thenReturn(entries);
        final ZipArchiverContext context = new ZipArchiverContext(settings, zipFile);

        context.close();

        assertThat(context.getExplodeCommand()).isEqualTo(settings);
        assertThat(context.getEntries()).isEqualTo(entries);
        assertThat(context.getZipFile()).isEqualTo(zipFile);
        verify(zipFile).close();
        verifyNoMoreInteractions(zipFile);
    }
}
