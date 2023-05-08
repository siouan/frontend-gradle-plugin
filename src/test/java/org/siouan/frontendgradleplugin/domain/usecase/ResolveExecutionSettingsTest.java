package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.model.ExecutableType;
import org.siouan.frontendgradleplugin.domain.model.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.model.GetExecutablePathQuery;
import org.siouan.frontendgradleplugin.domain.model.Platform;
import org.siouan.frontendgradleplugin.test.fixture.PathFixture;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.fixture.SystemPropertyFixture;

@ExtendWith(MockitoExtension.class)
class ResolveExecutionSettingsTest {

    private static final Path PACKAGE_JSON_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("frontend");

    private static final Path NODE_INSTALL_DIRECTORY_PATH = PathFixture.ANY_PATH.resolve("node");

    private static final String SCRIPT = " run script ";

    @Mock
    private GetNodeExecutablePath getNodeExecutablePath;

    @Mock
    private GetExecutablePath getExecutablePath;

    @InjectMocks
    private ResolveExecutionSettings usecase;

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNodeInDistributionAndOsIsWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.NODE);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenExecutableIsNotNodeInDistributionAndOsIsWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.NPM);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithWindowsCmdWhenNodeAndYarnExecutablesAreInInstallDirectoryAndOsIsWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Windows NT");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.YARN);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsOnly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.WINDOWS_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.WINDOWS_EXECUTABLE_AUTOEXIT_FLAG, "\"" + yarnExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNodeInPathAndOsIsNotWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(nodeExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NODE,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.NODE);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).isEmpty();
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, "\"" + nodeExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenExecutableIsNpmInPathAndOsIsNotWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path npmExecutablePath = nodeExecutablePath.resolveSibling("npm");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(npmExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.NPM,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.NPM);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, "\"" + npmExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }

    @Test
    void shouldResolveExecSettingsWithUnixShellWhenNodeAndYarnExecutablesAreInInstallDirectoryAndOsIsNotWindows() {
        final Platform platform = PlatformFixture.aPlatform(SystemPropertyFixture.getSystemJvmArch(), "Linux");
        final Path nodeExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("node");
        final Path yarnExecutablePath = NODE_INSTALL_DIRECTORY_PATH.resolve("yarn");
        when(getNodeExecutablePath.execute(NODE_INSTALL_DIRECTORY_PATH, platform)).thenReturn(nodeExecutablePath);
        when(getExecutablePath.execute(any(GetExecutablePathQuery.class))).thenReturn(yarnExecutablePath);

        final ExecutionSettings executionSettings = usecase.execute(PACKAGE_JSON_DIRECTORY_PATH, ExecutableType.YARN,
            NODE_INSTALL_DIRECTORY_PATH, platform, SCRIPT);

        final ArgumentCaptor<GetExecutablePathQuery> getExecutablePathQueryArgumentCaptor = ArgumentCaptor.forClass(
            GetExecutablePathQuery.class);
        verify(getExecutablePath).execute(getExecutablePathQueryArgumentCaptor.capture());
        assertThat(getExecutablePathQueryArgumentCaptor.getValue()).satisfies(getExecutablePathQuery -> {
            assertThat(getExecutablePathQuery.getExecutableType()).isEqualTo(ExecutableType.YARN);
            assertThat(getExecutablePathQuery.getNodeInstallDirectoryPath()).isEqualTo(NODE_INSTALL_DIRECTORY_PATH);
            assertThat(getExecutablePathQuery.getPlatform()).isEqualTo(platform);
        });
        assertThat(executionSettings.getWorkingDirectoryPath()).isEqualTo(PACKAGE_JSON_DIRECTORY_PATH);
        assertThat(executionSettings.getAdditionalExecutablePaths()).containsExactly(nodeExecutablePath.getParent());
        assertThat(executionSettings.getExecutablePath()).isEqualTo(ResolveExecutionSettings.UNIX_EXECUTABLE_PATH);
        assertThat(executionSettings.getArguments()).containsExactly(
            ResolveExecutionSettings.UNIX_EXECUTABLE_AUTOEXIT_FLAG, "\"" + yarnExecutablePath + "\" run script");
        verifyNoMoreInteractions(getNodeExecutablePath, getExecutablePath);
    }
}
