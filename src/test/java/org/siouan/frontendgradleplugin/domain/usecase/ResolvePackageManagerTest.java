package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPackageManagerException;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;
import org.siouan.frontendgradleplugin.domain.model.PackageManagerType;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.domain.model.ResolvePackageManagerQuery;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;

@ExtendWith(MockitoExtension.class)
class ResolvePackageManagerTest {

    @Mock
    private ParsePackageManagerType parsePackageManagerType;

    @Mock
    private GetExecutablePath getExecutablePath;

    @Mock
    private FileManager fileManager;

    @InjectMocks
    private ResolvePackageManager usecase;

    @Test
    void should_resolve_package_manager_type_and_store_name_and_executable_file_path_in_cache()
        throws UnsupportedPackageManagerException, IOException {
        final Path metadataFilePath = Paths.get("package.json");
        final Path nodeInstallDirectoryPath = Paths.get("node");
        final Platform platform = PlatformFixture.LOCAL_PLATFORM;
        final Path packageManagerNameFilePath = Paths.get("name.txt");
        final Path packageManagerExecutablePathFilePath = Paths.get("executable-path.txt");
        final PackageManagerType packageManagerType = PackageManagerType.PNPM;
        when(parsePackageManagerType.execute(metadataFilePath)).thenReturn(packageManagerType);
        final Path executablePath = Paths.get("executable");
        when(fileManager.writeString(packageManagerNameFilePath, packageManagerType.getPackageManagerName(),
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            packageManagerNameFilePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(executablePath);
        when(fileManager.writeString(packageManagerExecutablePathFilePath, executablePath.toString(),
            StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)).thenReturn(
            packageManagerExecutablePathFilePath);

        usecase.execute(new ResolvePackageManagerQuery(metadataFilePath, nodeInstallDirectoryPath, platform,
            packageManagerNameFilePath, packageManagerExecutablePathFilePath));
        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.PNPM);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(nodeInstallDirectoryPath);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        verifyNoMoreInteractions(parsePackageManagerType, getExecutablePath, fileManager);
    }
}