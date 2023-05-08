package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.model.PackageManagerType;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;

@ExtendWith(MockitoExtension.class)
class ParsePackageManagerTypeTest {

    @Mock
    private FileManager fileManager;

    @Mock
    private Logger logger;

    @InjectMocks
    private ParsePackageManagerType usecase;

    @Test
    void should_fail_when_metadata_file_is_not_readable() throws IOException {
        final Path metadataFilePath = Path.of("");
        final Exception expectedException = new IOException();
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, logger);
    }

    @Test
    void should_fail_when_metadata_file_does_not_contain_a_package_manager_specification() throws IOException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn("{}");

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isInstanceOf(UnsupportedPackageManagerException.class);

        verifyNoMoreInteractions(fileManager, logger);
    }

    @Test
    void should_fail_when_metadata_file_contains_an_invalid_package_manager_specification() throws IOException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn("{\"packageManager\":\"invalid\"}");

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isInstanceOf(UnsupportedPackageManagerException.class);

        verifyNoMoreInteractions(fileManager, logger);
    }

    @Test
    void should_fail_when_metadata_file_refers_to_an_unsupported_package_manager() throws IOException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn("{\"packageManager\":\"name@version\"}");

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isInstanceOf(UnsupportedPackageManagerException.class);

        verify(logger).info(anyString(), eq("name"));
        verifyNoMoreInteractions(fileManager, logger);
    }

    @Test
    void should_return_package_manager_specification_with_no_other_specification_present()
        throws IOException, UnsupportedPackageManagerException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn("{\"packageManager\":\"npm@8.15.0\"}");

        assertThat(usecase.execute(metadataFilePath)).isEqualTo(PackageManagerType.NPM);

        verify(logger).info(anyString(), eq("npm"));
        verifyNoMoreInteractions(fileManager, logger);
    }

    @Test
    void should_return_package_manager_specification_with_other_specification_present_and_format()
        throws IOException, UnsupportedPackageManagerException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8))
            .thenReturn("{\n  \"name\":\"test\",\n  \"packageManager\": \"npm@8.15.0\",\n  \"version\": \"5.2.6\"\n}");

        assertThat(usecase.execute(metadataFilePath)).isEqualTo(PackageManagerType.NPM);

        verify(logger).info(anyString(), eq("npm"));
        verifyNoMoreInteractions(fileManager, logger);
    }
}
