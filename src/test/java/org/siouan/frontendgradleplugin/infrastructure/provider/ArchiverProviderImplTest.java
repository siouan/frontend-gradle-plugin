package org.siouan.frontendgradleplugin.infrastructure.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.infrastructure.archiver.TarArchiver;
import org.siouan.frontendgradleplugin.infrastructure.archiver.ZipArchiver;

@ExtendWith(MockitoExtension.class)
class ArchiverProviderImplTest {

    @Mock
    private TarArchiver tarArchiver;

    @Mock
    private ZipArchiver zipArchiver;

    @InjectMocks
    private ArchiverProviderImpl provider;

    @Test
    void shouldReturnZipArchiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".zip"))).containsInstanceOf(ZipArchiver.class);
    }

    @Test
    void shouldReturnTarArchiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".tar.gz"))).containsInstanceOf(TarArchiver.class);
    }

    @Test
    void shouldReturnNoArchiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".Z"))).isEmpty();
    }
}
