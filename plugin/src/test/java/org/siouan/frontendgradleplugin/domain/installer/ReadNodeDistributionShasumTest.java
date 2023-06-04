package org.siouan.frontendgradleplugin.domain.installer;

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
import org.siouan.frontendgradleplugin.domain.FileManager;

@ExtendWith(MockitoExtension.class)
class ReadNodeDistributionShasumTest {

    private static final Path SHASUMS_FILE_PATH = Paths.get("shasums.txt");

    private static final String DISTRIBUTION_FILENAME = "node-10.0.0.zip";

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ReadNodeDistributionShasum usecase;

    @Test
    void should_fail_when_shasum_file_cannot_be_opened() throws IOException {
        final Exception expectedException = new IOException();
        when(fileManager.newBufferedReader(SHASUMS_FILE_PATH)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(ReadNodeDistributionShasumCommand
            .builder()
            .distributionFileName(DISTRIBUTION_FILENAME)
            .nodeDistributionShasumFilePath(SHASUMS_FILE_PATH)
            .build())).isInstanceOf(IOException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_no_shasum_when_not_found() throws IOException {
        final BufferedReader bufferedReader = mock(BufferedReader.class);
        when(fileManager.newBufferedReader(SHASUMS_FILE_PATH)).thenReturn(bufferedReader);
        when(bufferedReader.lines()).thenReturn(Stream.empty());

        assertThat(usecase.execute(ReadNodeDistributionShasumCommand
            .builder()
            .distributionFileName(DISTRIBUTION_FILENAME)
            .nodeDistributionShasumFilePath(SHASUMS_FILE_PATH)
            .build())).isEmpty();

        verify(bufferedReader).close();
        verifyNoMoreInteractions(fileManager, bufferedReader);
    }

    @Test
    void should_return_shasum_when_found() throws IOException {
        final String shasum1 = "ht7kuyfff74vz9";
        final String shasum2 = "523ab86h853e86";
        final String shasum3 = "6htskfy72291ds";
        final BufferedReader bufferedReader = mock(BufferedReader.class);
        when(fileManager.newBufferedReader(SHASUMS_FILE_PATH)).thenReturn(bufferedReader);
        when(bufferedReader.lines()).thenReturn(
            Stream.of(shasum1 + "  node-10.1.0.zip", shasum2 + "  " + DISTRIBUTION_FILENAME,
                shasum3 + "  node-10.0.2.zip"));

        assertThat(usecase.execute(ReadNodeDistributionShasumCommand
            .builder()
            .distributionFileName(DISTRIBUTION_FILENAME)
            .nodeDistributionShasumFilePath(SHASUMS_FILE_PATH)
            .build())).contains(shasum2);

        verify(bufferedReader).close();
        verifyNoMoreInteractions(fileManager, bufferedReader);
    }
}
