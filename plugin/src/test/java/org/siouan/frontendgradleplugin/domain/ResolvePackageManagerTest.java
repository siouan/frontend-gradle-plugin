package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.LOCAL_PLATFORM;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolvePackageManagerTest {

    @Mock
    private ParsePackageManagerFromPackageJsonFile parsePackageManagerFromPackageJsonFile;

    @Mock
    private GetExecutablePath getExecutablePath;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ResolvePackageManager usecase;

    @Test
    void should_delete_output_files_when_package_json_file_does_not_exist()
        throws IOException, InvalidJsonFileException, MalformedPackageManagerSpecification,
        UnsupportedPackageManagerException {
        final Path nodeInstallDirectoryPath = Paths.get("node");
        final Platform platform = LOCAL_PLATFORM;
        final Path packageManagerSpecificationFilePath = Paths.get("name.txt");
        final Path packageManagerExecutablePathFilePath = Paths.get("executable-path.txt");

        usecase.execute(new ResolvePackageManagerCommand(null, nodeInstallDirectoryPath, platform,
            packageManagerSpecificationFilePath, packageManagerExecutablePathFilePath));

        final ArgumentCaptor<Path> pathArgumentCaptor = ArgumentCaptor.forClass(Path.class);
        verify(fileManager, times(2)).deleteIfExists(pathArgumentCaptor.capture());
        assertThat(pathArgumentCaptor.getAllValues()).containsExactly(packageManagerSpecificationFilePath,
            packageManagerExecutablePathFilePath);
        verifyNoMoreInteractions(parsePackageManagerFromPackageJsonFile, getExecutablePath, fileManager);
    }

    @Test
    void should_resolve_package_manager_and_cache_output_files()
        throws IOException, InvalidJsonFileException, MalformedPackageManagerSpecification,
        UnsupportedPackageManagerException {
        final Path packageJsonFilePath = Paths.get("package.json");
        final Path nodeInstallDirectoryPath = Paths.get("node");
        final Platform platform = LOCAL_PLATFORM;
        final Path packageManagerSpecificationFilePath = Paths.get("name.txt");
        final Path packageManagerExecutablePathFilePath = Paths.get("executable-path.txt");
        final PackageManagerType packageManagerType = PackageManagerType.PNPM;
        final String packageManagerVersion = "5.9.2";
        when(parsePackageManagerFromPackageJsonFile.execute(packageJsonFilePath)).thenReturn(
            PackageManager.builder().type(packageManagerType).version(packageManagerVersion).build());
        final Path executablePath = Paths.get("executable");
        when(fileManager.writeString(packageManagerSpecificationFilePath, "pnpm@5.9.2", StandardCharsets.UTF_8,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(packageManagerSpecificationFilePath);
        when(getExecutablePath.execute(any(GetExecutablePathCommand.class))).thenReturn(executablePath);
        when(fileManager.writeString(packageManagerExecutablePathFilePath, executablePath.toString(),
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            packageManagerExecutablePathFilePath);

        usecase.execute(new ResolvePackageManagerCommand(packageJsonFilePath, nodeInstallDirectoryPath, platform,
            packageManagerSpecificationFilePath, packageManagerExecutablePathFilePath));

        final ArgumentCaptor<GetExecutablePathCommand> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathCommand.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.PNPM);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(nodeInstallDirectoryPath);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        verifyNoMoreInteractions(parsePackageManagerFromPackageJsonFile, getExecutablePath, fileManager);
    }
}
