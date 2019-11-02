package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Utils} class.
 *
 * @since 1.1.0
 */
class UtilsTest {

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
