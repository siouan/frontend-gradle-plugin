package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HashFileTest {

    private static final String DATA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    private static final String DATA_SHA256_HASH = "a58dd8680234c1f8cc2ef2b325a43733605a7f16f288e072de8eae81fd8d6433";

    @TempDir
    Path temporaryDirectory;

    @InjectMocks
    private HashFile hashFile;

    @Test
    void shouldFailWhenFileCannotBeRead() {
        assertThatThrownBy(() -> hashFile.execute(Paths.get("/dummy"))).isInstanceOf(IOException.class);
    }

    @Test
    void shouldReturnValidSha256HashWithDefaultBufferingCapacity() throws IOException {
        final Path temporaryFile = Files.write(temporaryDirectory.resolve("file-for-hashing.txt"), DATA.getBytes());
        final String hash = hashFile.execute(temporaryFile);
        assertThat(hash).isEqualTo(DATA_SHA256_HASH);
    }
}
