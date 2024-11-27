package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PathUtilsTest {

    @Test
    void should_return_no_extension_when_getting_extension_of_root_path() {
        assertThat(PathUtils.getExtension(Paths.get("/"))).isEmpty();
    }

    @Test
    void should_return_no_extension_when_getting_extension_of_empty_path() {
        assertThat(PathUtils.getExtension(Paths.get(""))).isEmpty();
    }

    @Test
    void should_return_no_extension_when_getting_extension_of_file_path_without_extension() {
        assertThat(PathUtils.getExtension(Paths.get("file"))).isEmpty();
    }

    @Test
    void should_return_extension_when_getting_extension_of_file_path_with_extension() {
        assertThat(PathUtils.getExtension(Paths.get("file.txt"))).contains(".txt");
    }

    @Test
    void should_return_last_extension_when_getting_extension_of_file_path_with_consecutive_extensions() {
        assertThat(PathUtils.getExtension(Paths.get("file.txt.2"))).contains(".2");
    }

    @Test
    void should_return_empty_string_when_removing_extension_of_root_path() {
        assertThat(PathUtils.removeExtension(Paths.get("/"))).isEqualTo(File.separator);
    }

    @Test
    void should_return_empty_string_when_removing_extension_of_empty_path() {
        assertThat(PathUtils.removeExtension(Paths.get(""))).isEmpty();
    }

    @Test
    void should_return_file_name_when_removing_extension_of_file_path_without_extension() {
        assertThat(PathUtils.removeExtension(Paths.get("file"))).isEqualTo("file");
    }

    @Test
    void should_return_file_name_without_extension_when_removing_extension_of_file_path_with_extension() {
        assertThat(PathUtils.removeExtension(Paths.get("file.txt"))).contains("file");
    }

    @Test
    void should_return_file_name_without_last_rxtension_when_removing_extension_of_file_path_with_consecutive_extensions() {
        assertThat(PathUtils.removeExtension(Paths.get("file.txt.2"))).contains("file.txt");
    }

    @Test
    void should_return_true_when_checking_gz_extension_is_a_known_gzip_extension() {
        assertThat(PathUtils.isGzipExtension(".gz")).isTrue();
    }

    @Test
    void should_return_true_when_checking_gzip_extension_is_a_known_gzip_extension() {
        assertThat(PathUtils.isGzipExtension(".gzip")).isTrue();
    }

    @Test
    void should_return_false_when_checking_extension_is_not_a_known_gzip_extension() {
        assertThat(PathUtils.isGzipExtension(".Z")).isFalse();
    }
}
