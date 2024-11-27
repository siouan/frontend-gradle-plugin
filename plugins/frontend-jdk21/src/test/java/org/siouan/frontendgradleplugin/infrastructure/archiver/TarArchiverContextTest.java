package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.installer.archiver.ExplodeCommand;

/**
 * Unit tests for the {@link TarArchiverContext} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class TarArchiverContextTest {

    @Mock
    private ExplodeCommand settings;

    @Mock
    private TarArchiveInputStream inputStream;

    @Test
    void should_fail_when_closing_context_with_io_exception() throws IOException {
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(inputStream).close();
        final TarArchiverContext context = new TarArchiverContext(settings, inputStream);

        assertThatThrownBy(context::close).isEqualTo(expectedException);

        assertThat(context.getExplodeCommand()).isEqualTo(settings);
        assertThat(context.getInputStream()).isEqualTo(inputStream);
        verifyNoMoreInteractions(inputStream);
    }

    @Test
    void should_close_context() throws IOException {
        final TarArchiverContext context = new TarArchiverContext(settings, inputStream);

        context.close();

        assertThat(context.getExplodeCommand()).isEqualTo(settings);
        assertThat(context.getInputStream()).isEqualTo(inputStream);
        verify(inputStream).close();
        verifyNoMoreInteractions(inputStream);
    }
}
