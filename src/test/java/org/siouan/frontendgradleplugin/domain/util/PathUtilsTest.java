package org.siouan.frontendgradleplugin.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PathUtilsTest {

    @Test
    void shouldTellExtensionIsGzipWhenExtensionEndsWithGz() {
        assertThat(PathUtils.isGzipExtension(".gz")).isTrue();
    }

    @Test
    void shouldTellExtensionIsGzipWhenExtensionEndsWithGzip() {
        assertThat(PathUtils.isGzipExtension(".gzip")).isTrue();
    }

    @Test
    void shouldTellExtensionIsNotGzipWhenExtensionEndsNeitherWithGzOrGzip() {
        assertThat(PathUtils.isGzipExtension(".Z")).isFalse();
    }
}
