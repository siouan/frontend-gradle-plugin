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
import org.siouan.frontendgradleplugin.domain.model.ExplodeSettings;

/**
 * Unit tests for the {@link TarArchiverContext} class.
 *
 * @since 1.1.3
 */
@ExtendWith(MockitoExtension.class)
class TarArchiverContextTest {

    @Mock
    private ExplodeSettings settings;

    @Mock
    private TarArchiveInputStream inputStream;

    @Test
    void shouldFailWhenClosingContextWithIOException() throws IOException {
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(inputStream).close();
        final TarArchiverContext context = new TarArchiverContext(settings, inputStream);

        assertThatThrownBy(context::close).isEqualTo(expectedException);

        assertThat(context.getSettings()).isEqualTo(settings);
        assertThat(context.getInputStream()).isEqualTo(inputStream);
        verifyNoMoreInteractions(inputStream);
    }

    @Test
    void shouldCloseContext() throws IOException {
        final TarArchiverContext context = new TarArchiverContext(settings, inputStream);

        context.close();

        assertThat(context.getSettings()).isEqualTo(settings);
        assertThat(context.getInputStream()).isEqualTo(inputStream);
        verify(inputStream).close();
        verifyNoMoreInteractions(inputStream);
    }
}
