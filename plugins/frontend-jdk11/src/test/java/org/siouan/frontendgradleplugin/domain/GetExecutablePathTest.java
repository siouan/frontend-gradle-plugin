package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.test.PathFixture.ANY_PATH;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetExecutablePathTest {

    private static final Path NODE_INSTALL_DIRECTORY_PATH = ANY_PATH.resolve("node");

    @Mock
    private ResolveCorepackExecutablePath getCorepackExecutablePath;

    @Mock
    private ResolveNodeExecutablePath getNodeExecutablePath;

    @Mock
    private ResolveNpmExecutablePath getNpmExecutablePath;

    @Mock
    private ResolvePnpmExecutablePath getPnpmExecutablePath;

    @Mock
    private ResolveYarnExecutablePath getYarnExecutablePath;

    @InjectMocks
    private GetExecutablePath usecase;

    @Test
    void should_resolve_node_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(GetExecutablePathCommand
            .builder()
            .executableType(ExecutableType.NODE)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).isEqualTo(executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_corepack_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("corepack");
        when(getCorepackExecutablePath.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(GetExecutablePathCommand
            .builder()
            .executableType(ExecutableType.COREPACK)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).isEqualTo(executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_npm_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("npm");
        when(getNpmExecutablePath.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(GetExecutablePathCommand
            .builder()
            .executableType(ExecutableType.NPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).isEqualTo(executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_pnpm_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("pnpm");
        when(getPnpmExecutablePath.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(GetExecutablePathCommand
            .builder()
            .executableType(ExecutableType.PNPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).isEqualTo(executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_yarn_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("yarn");
        when(getYarnExecutablePath.execute(ResolveExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(GetExecutablePathCommand
            .builder()
            .executableType(ExecutableType.YARN)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).isEqualTo(executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }
}
