package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PathUtilsTest {

    @Test
    void shouldReturnNoExtensionWhenGettingExtensionOfRootPath() {
        assertThat(PathUtils.getExtension(Paths.get("/"))).isEmpty();
    }

    @Test
    void shouldReturnNoExtensionWhenGettingExtensionOfEmptyPath() {
        assertThat(PathUtils.getExtension(Paths.get(""))).isEmpty();
    }

    @Test
    void shouldReturnNoExtensionWhenGettingExtensionOfFilePathWithoutExtension() {
        assertThat(PathUtils.getExtension(Paths.get("file"))).isEmpty();
    }

    @Test
    void shouldReturnExtensionWhenGettingExtensionOfFilePathWithExtension() {
        assertThat(PathUtils.getExtension(Paths.get("file.txt"))).contains(".txt");
    }

    @Test
    void shouldReturnLastExtensionWhenGettingExtensionOfFilePathWithConsecutiveExtensions() {
        assertThat(PathUtils.getExtension(Paths.get("file.txt.2"))).contains(".2");
    }

    @Test
    void shouldReturnEmptyStringWhenRemovingExtensionOfRootPath() {
        assertThat(PathUtils.removeExtension(Paths.get("/"))).isEqualTo(File.separator);
    }

    @Test
    void shouldReturnEmptyStringWhenRemovingExtensionOfEmptyPath() {
        assertThat(PathUtils.removeExtension(Paths.get(""))).isEmpty();
    }

    @Test
    void shouldReturnFileNameWhenRemovingExtensionOfFilePathWithoutExtension() {
        assertThat(PathUtils.removeExtension(Paths.get("file"))).isEqualTo("file");
    }

    @Test
    void shouldReturnFileNameWithoutExtensionWhenRemovingExtensionOfFilePathWithExtension() {
        assertThat(PathUtils.removeExtension(Paths.get("file.txt"))).contains("file");
    }

    @Test
    void shouldReturnFileNameWithoutLastExtensionWhenRemovingExtensionOfFilePathWithConsecutiveExtensions() {
        assertThat(PathUtils.removeExtension(Paths.get("file.txt.2"))).contains("file.txt");
    }

    @Test
    void shouldReturnTrueWhenCheckingGzExtensionIsAKnownGzipExtension() {
        assertThat(PathUtils.isGzipExtension(".gz")).isTrue();
    }

    @Test
    void shouldReturnTrueWhenCheckingGzipExtensionIsAKnownGzipExtension() {
        assertThat(PathUtils.isGzipExtension(".gzip")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenCheckingExtensionIsNotAKnownGzipExtension() {
        assertThat(PathUtils.isGzipExtension(".Z")).isFalse();
    }
}
