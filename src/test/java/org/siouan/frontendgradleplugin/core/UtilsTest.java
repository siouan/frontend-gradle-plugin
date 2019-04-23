package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;

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
    public void shouldFailWhenSourceDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(() -> Utils.moveFiles(new File(temporaryDirectory, "src"), temporaryDirectory))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailWhenDestDirectoryIsNotAValidDirectory() {
        assertThatThrownBy(() -> Utils.moveFiles(temporaryDirectory, new File(temporaryDirectory, "dest")))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
