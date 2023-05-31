package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParsePackageManagerTypeTest {

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ParsePackageManagerType usecase;

    @BeforeEach
    void setUp() {
        usecase = new ParsePackageManagerType(fileManager, mock(Logger.class));
    }

    @Test
    void should_fail_when_metadata_file_is_not_readable() throws IOException {
        final Path metadataFilePath = Path.of("");
        final Exception expectedException = new IOException();
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager);
    }

    @ParameterizedTest
    @ValueSource(strings = {"{}", "{\"packageManager\":\"invalid\"}", "{\"packageManager\":\"name@version\"}"})
    void should_fail_when_metadata_file_does_not_contain_a_valid_package_manager_specification(final String metadata)
        throws IOException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn(metadata);

        assertThatThrownBy(() -> usecase.execute(metadataFilePath)).isInstanceOf(
            UnsupportedPackageManagerException.class);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_package_manager_specification_with_no_other_specification_present()
        throws IOException, UnsupportedPackageManagerException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn(
            "{\"packageManager\":\"npm@9.6.6\"}");

        assertThat(usecase.execute(metadataFilePath)).isEqualTo(PackageManagerType.NPM);

        verifyNoMoreInteractions(fileManager);
    }

    @Test
    void should_return_package_manager_specification_with_other_specification_present_and_format()
        throws IOException, UnsupportedPackageManagerException {
        final Path metadataFilePath = Path.of("");
        when(fileManager.readString(metadataFilePath, StandardCharsets.UTF_8)).thenReturn(
            "{\n  \"name\":\"test\",\n  \"packageManager\": \"npm@9.6.6\",\n  \"version\": \"5.2.6\"\n}");

        assertThat(usecase.execute(metadataFilePath)).isEqualTo(PackageManagerType.NPM);

        verifyNoMoreInteractions(fileManager);
    }
}
