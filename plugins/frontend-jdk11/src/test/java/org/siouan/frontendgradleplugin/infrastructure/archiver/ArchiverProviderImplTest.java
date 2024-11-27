package org.siouan.frontendgradleplugin.infrastructure.archiver;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArchiverProviderImplTest {

    @Mock
    private TarArchiver tarArchiver;

    @Mock
    private ZipArchiver zipArchiver;

    @InjectMocks
    private ArchiverProviderImpl provider;

    @Test
    void should_return_zip_archiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".zip"))).contains(zipArchiver);
    }

    @Test
    void should_return_tar_archiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".tar.gz"))).contains(tarArchiver);
    }

    @Test
    void should_return_no_archiver() {
        assertThat(provider.findByArchiveFilePath(Paths.get(".Z"))).isEmpty();
    }
}
