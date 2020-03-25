package org.siouan.frontendgradleplugin.infrastructure.provider;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.siouan.frontendgradleplugin.infrastructure.archiver.TarArchiver;
import org.siouan.frontendgradleplugin.infrastructure.archiver.ZipArchiver;

/**
 * @since 1.1.3
 */
class ArchiverProviderImplTest {

    @Test
    void shouldReturnZipArchiver() {
        assertThat(new ArchiverProviderImpl().findByFilenameExtension(".zip")).containsInstanceOf(ZipArchiver.class);
    }

    @Test
    void shouldReturnTarArchiver() {
        assertThat(new ArchiverProviderImpl().findByFilenameExtension(".tar.gz")).containsInstanceOf(TarArchiver.class);
    }

    @Test
    void shouldReturnNoArchiver() {
        assertThat(new ArchiverProviderImpl().findByFilenameExtension(".Z")).isEmpty();
    }
}
