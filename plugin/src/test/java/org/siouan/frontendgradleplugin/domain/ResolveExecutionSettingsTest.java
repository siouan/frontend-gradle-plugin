package org.siouan.frontendgradleplugin.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.PathFixture.ANY_PATH;
import static org.siouan.frontendgradleplugin.domain.PlatformFixture.aPlatform;
import static org.siouan.frontendgradleplugin.domain.SystemPropertiesFixture.getSystemJvmArch;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResolveExecutionSettingsTest {

    private static final Path PACKAGE_JSON_DIRECTORY_PATH = ANY_PATH.resolve("frontend");

    private static final Path NODE_INSTALL_DIRECTORY_PATH = ANY_PATH.resolve("node");

    private static final String SCRIPT = " run script ";

    @Mock
    private ResolveGlobalNodeExecutablePath getNodeExecutablePath;

    @Mock
    private GetExecutablePath getExecutablePath;

    @InjectMocks
    private ResolveExecutionSettings usecase;

    @Test
    void should_resolve_exec_settings_with_windows_cmd_when_executable_is_node_in_distribution_and_os_is_windows() {
        final Platform platform = aPlatform(getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(GetExecutablePathCommand.builder()
                .executableType(ExecutableType.NODE)
                .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
                .platform(platform)
            .build())).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(PACKAGE_JSON_DIRECTORY_PATH)
            .executableType(ExecutableType.NODE)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .script(SCRIPT)
            .build());

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void should_resolve_exec_settings_with_windows_cmd_when_executable_is_not_node_in_distribution_and_os_is_windows() {
        final Platform platform = aPlatform(getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(GetExecutablePathCommand.builder()
            .executableType(ExecutableType.NPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(PACKAGE_JSON_DIRECTORY_PATH)
            .executableType(ExecutableType.NPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .script(SCRIPT)
            .build());

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void should_resolve_exec_settings_with_unix_shell_when_executable_is_node_in_path_and_os_is_not_windows() {
        final Platform platform = aPlatform(getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(GetExecutablePathCommand.builder()
            .executableType(ExecutableType.NODE)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(PACKAGE_JSON_DIRECTORY_PATH)
            .executableType(ExecutableType.NODE)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .script(SCRIPT)
            .build());

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void should_resolve_exec_settings_with_unix_shell_when_executable_is_npm_in_path_and_os_is_not_windows() {
        final Platform platform = aPlatform(getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = nodeExecutablePath.resolveSibling("npm");
        when(getNodeExecutablePath.execute(ResolveGlobalExecutablePathCommand
            .builder()
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(GetExecutablePathCommand.builder()
            .executableType(ExecutableType.NPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .build())).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(PACKAGE_JSON_DIRECTORY_PATH)
            .executableType(ExecutableType.NPM)
            .nodeInstallDirectoryPath(NODE_INSTALL_DIRECTORY_PATH)
            .platform(platform)
            .script(SCRIPT)
            .build());

        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }
}
