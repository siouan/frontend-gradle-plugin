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
    File temporaryDirectory;

    @Test
    void shouldFailWhenSourceDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(
            () -> Utils.moveFiles(temporaryDirectory.toPath().resolve("src"), temporaryDirectory.toPath()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFailWhenDestDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(
            () -> Utils.moveFiles(temporaryDirectory.toPath(), temporaryDirectory.toPath().resolve("dest")))
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

    @Test
    void shouldTellExtensionIsGzipWhenExtensionEndsWithGz() {
        assertThat(Utils.isGzipExtension(".gz")).isTrue();
    }

    @Test
    void shouldTellExtensionIsGzipWhenExtensionEndsWithGzip() {
        assertThat(Utils.isGzipExtension(".gzip")).isTrue();
    }

    @Test
    void shouldTellExtensionIsNotGzipWhenExtensionEndsNeitherWithGzOrGzip() {
        assertThat(Utils.isGzipExtension(".Z")).isFalse();
    }
}
