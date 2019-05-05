package org.siouan.frontendgradleplugin.core.archivers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ArchiverFactoryImpl} class.
 *
 * @since 1.1.3
 */
class ArchiverFactoryImplTest {

    @Test
    void shouldReturnZipArchiver() {
        assertThat(new ArchiverFactoryImpl().get(".zip")).containsInstanceOf(ZipArchiver.class);
    }

    @Test
    void shouldReturnTarArchiver() {
        assertThat(new ArchiverFactoryImpl().get(".tar.gz")).containsInstanceOf(TarArchiver.class);
    }

    @Test
    void shouldReturnNoArchiver() {
        assertThat(new ArchiverFactoryImpl().get(".Z")).isEmpty();
    }
}
