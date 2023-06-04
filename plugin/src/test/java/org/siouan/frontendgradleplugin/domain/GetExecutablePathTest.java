package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.fixture.PathFixture.ANY_PATH;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.aPlatform;

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
    private ResolveGlobalCorepackExecutablePath getCorepackExecutablePath;

    @Mock
    private ResolveGlobalNodeExecutablePath getNodeExecutablePath;

    @Mock
    private ResolveGlobalNpmExecutablePath getNpmExecutablePath;

    @Mock
    private ResolveGlobalPnpmExecutablePath getPnpmExecutablePath;

    @Mock
    private ResolveGlobalYarnExecutablePath getYarnExecutablePath;

    @InjectMocks
    private GetExecutablePath usecase;

    @Test
    void should_resolve_node_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("node");
        when(getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathCommand(ExecutableType.NODE, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_corepack_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("corepack");
        when(getCorepackExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathCommand(ExecutableType.COREPACK, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_npm_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("npm");
        when(getNpmExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathCommand(ExecutableType.NPM, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_pnpm_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("pnpm");
        when(getPnpmExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathCommand(ExecutableType.PNPM, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }

    @Test
    void should_resolve_yarn_executable_path() {
        final Platform platform = aPlatform();
        final Path executablePath = Paths.get("yarn");
        when(getYarnExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(executablePath);

        assertThat(usecase.execute(
            new GetExecutablePathCommand(ExecutableType.YARN, NODE_INSTALL_DIRECTORY_PATH, platform))).isEqualTo(
            executablePath);

        verifyNoMoreInteractions(getCorepackExecutablePath, getNodeExecutablePath, getNpmExecutablePath,
            getPnpmExecutablePath, getYarnExecutablePath);
    }
}
