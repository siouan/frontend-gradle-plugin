package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.Platform;

@ExtendWith(MockitoExtension.class)
class FileUtilsTest {

    @Test
    void shouldNotTouchFilePermissionsWhenOsIsWindows() throws IOException {
        assertThat(
            FileUtils.setFileExecutable(Paths.get("afile"), new Platform(SystemUtils.getSystemJvmArch(), "Windows NT")))
            .isFalse();
    }

    @Test
    void shouldNotTouchFilePermissionsWhenFileNotFound() throws IOException {
        assertThat(FileUtils.setFileExecutable(Paths.get("afile"),
            new Platform(SystemUtils.getSystemJvmArch(), "Linux"))).isFalse();
    }
}
