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
class ParsePackageManagerFromPackageJsonFileTest {

    @Mock
    private FileManager fileManager;

    @Mock
    private ParsePackageManagerSpecification parsePackageManagerSpecification;

    @InjectMocks
    private ParsePackageManagerFromPackageJsonFile usecase;

    @BeforeEach
    void setUp() {
        usecase = new ParsePackageManagerFromPackageJsonFile(fileManager, parsePackageManagerSpecification,
            mock(Logger.class));
    }

    @Test
    void should_fail_when_package_json_file_is_not_readable() throws IOException {
        final Path packageJsonFilePath = Path.of("");
        final Exception expectedException = new IOException();
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(packageJsonFilePath)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }

    @ParameterizedTest
    @ValueSource(strings = {"iglvre464", "{}"})
    void should_fail_when_package_json_file_does_not_contain_valid_json_with_a_package_manager_specification(
        final String packageJsonFileContent) throws IOException {
        final Path packageJsonFilePath = Path.of("");
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenReturn(packageJsonFileContent);

        assertThatThrownBy(() -> usecase.execute(packageJsonFilePath)).isInstanceOf(InvalidJsonFileException.class);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }

    @Test
    void should_fail_when_package_json_file_does_not_contain_a_valid_package_manager_specification()
        throws IOException, MalformedPackageManagerSpecification, UnsupportedPackageManagerException {
        final Path packageJsonFilePath = Path.of("");
        final String packageJsonFileContent = "{\"packageManager\":\"anything\"}";
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenReturn(packageJsonFileContent);
        final Exception expectedException = new MalformedPackageManagerSpecification("gverqe");
        when(parsePackageManagerSpecification.execute("anything")).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(packageJsonFilePath)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }

    @Test
    void should_fail_when_package_json_file_contains_a_package_manager_specification_with_an_unknown_package_manager()
        throws IOException, MalformedPackageManagerSpecification, UnsupportedPackageManagerException {
        final Path packageJsonFilePath = Path.of("");
        final String packageJsonFileContent = "{\"packageManager\":\"anything\"}";
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenReturn(packageJsonFileContent);
        final Exception expectedException = new UnsupportedPackageManagerException("gverqe");
        when(parsePackageManagerSpecification.execute("anything")).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(packageJsonFilePath)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }

    @Test
    void should_return_package_manager_specification_with_no_other_specification_present()
        throws IOException, InvalidJsonFileException, MalformedPackageManagerSpecification,
        UnsupportedPackageManagerException {
        final Path packageJsonFilePath = Path.of("");
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenReturn(
            "{\"packageManager\":\"npm@9.6.7\"}");
        final PackageManager packageManager = PackageManagerFixture.aPackageManager();
        when(parsePackageManagerSpecification.execute("npm@9.6.7")).thenReturn(packageManager);

        assertThat(usecase.execute(packageJsonFilePath)).isEqualTo(packageManager);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }

    @Test
    void should_return_package_manager_specification_with_other_specification_present_and_format()
        throws IOException, InvalidJsonFileException, MalformedPackageManagerSpecification,
        UnsupportedPackageManagerException {
        final Path packageJsonFilePath = Path.of("");
        when(fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8)).thenReturn(
            "{\n  \"name\":\"test\",\n  \"packageManager\": \"npm@9.6.7\",\n  \"version\": \"5.2.6\"\n}");
        final PackageManager packageManager = PackageManagerFixture.aPackageManager();
        when(parsePackageManagerSpecification.execute("npm@9.6.7")).thenReturn(packageManager);

        assertThat(usecase.execute(packageJsonFilePath)).isEqualTo(packageManager);

        verifyNoMoreInteractions(fileManager, parsePackageManagerSpecification);
    }
}
