package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for the {@link Utils} class.
 *
 * @since 1.1.0
 */
class UtilsTest {

    @TempDir
    protected File temporaryDirectory;

    @Test
    void shouldFailWhenSourceDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(() -> Utils.moveFiles(new File(temporaryDirectory, "src"), temporaryDirectory))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFailWhenDestDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(() -> Utils.moveFiles(temporaryDirectory, new File(temporaryDirectory, "dest")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotTouchFilePermissionsWhenOsIsWindows() throws IOException {
        assertThat(Utils.setFileExecutable(Paths.get("afile"), "Windows NT")).isFalse();
    }

    @Test
    void shouldNotTouchFilePermissionsWhenFileNotFound() throws IOException {
        assertThat(Utils.setFileExecutable(Paths.get("afile"), "Linux")).isFalse();
    }
}
