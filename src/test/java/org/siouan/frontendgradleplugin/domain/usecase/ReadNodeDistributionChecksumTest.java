package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

@ExtendWith(MockitoExtension.class)
class ReadNodeDistributionChecksumTest {

    private static final Path CHECKSUM_FILE_PATH = Paths.get("checksums.txt");

    private static final String DISTRIBUTION_FILENAME = "node-10.0.0.zip";

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ReadNodeDistributionChecksum usecase;

    @Test
    void shouldFailWhenChecksumFileCannotBeOpened() throws IOException {
        final Exception expectedException = new IOException();
        when(fileManager.newBufferedReader(CHECKSUM_FILE_PATH)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(CHECKSUM_FILE_PATH, DISTRIBUTION_FILENAME)).isInstanceOf(
            IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void shouldReturnNoChecksumWhenNotFound() throws IOException {
        final BufferedReader bufferedReader = mock(BufferedReader.class);
        when(fileManager.newBufferedReader(CHECKSUM_FILE_PATH)).thenReturn(bufferedReader);
        when(bufferedReader.lines()).thenReturn(Stream.empty());

        assertThat(usecase.execute(CHECKSUM_FILE_PATH, DISTRIBUTION_FILENAME)).isEmpty();

        verify(bufferedReader).close();
        verifyNoMoreInteractions(fileManager, bufferedReader);
    }

    @Test
    void shouldReturnChecksumWhenFound() throws IOException {
        final String checksum1 = "ht7kuyfff74vz9";
        final String checksum2 = "523ab86h853e86";
        final String checksum3 = "6htskfy72291ds";
        final BufferedReader bufferedReader = mock(BufferedReader.class);
        when(fileManager.newBufferedReader(CHECKSUM_FILE_PATH)).thenReturn(bufferedReader);
        when(bufferedReader.lines()).thenReturn(
            Stream.of(checksum1 + "  node-10.1.0.zip", checksum2 + "  " + DISTRIBUTION_FILENAME,
                checksum3 + "  node-10.0.2.zip"));

        assertThat(usecase.execute(CHECKSUM_FILE_PATH, DISTRIBUTION_FILENAME)).contains(checksum2);

        verify(bufferedReader).close();
        verifyNoMoreInteractions(fileManager, bufferedReader);
    }
}
