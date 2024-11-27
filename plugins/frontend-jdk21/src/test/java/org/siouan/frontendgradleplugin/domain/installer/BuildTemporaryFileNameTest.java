package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuildTemporaryFileNameTest {

    @InjectMocks
    private BuildTemporaryFileName usecase;

    @Test
    void should_return_temporary_fileName() {
        assertThat(usecase.execute("/home/user/file.tar.gz")).isEqualTo("/home/user/file.tar.gz.tmp");
    }
}
