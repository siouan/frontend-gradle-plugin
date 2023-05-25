package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.fixture.PathFixture.ANY_PATH;
import static org.siouan.frontendgradleplugin.test.fixture.PlatformFixture.LOCAL_PLATFORM;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.gradle.process.ExecOperations;
import org.gradle.process.ExecResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.ExecutableType;
import org.siouan.frontendgradleplugin.domain.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.ResolveExecutionSettings;
import org.siouan.frontendgradleplugin.domain.ResolveExecutionSettingsCommand;

@ExtendWith(MockitoExtension.class)
class GradleScriptRunnerAdapterTest {

    private static final String SCRIPT = "script";

    @Mock
    private ExecOperations execOperations;

    @Mock
    private ResolveExecutionSettings resolveExecutionSettings;

    @InjectMocks
    private GradleScriptRunnerAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new GradleScriptRunnerAdapter(resolveExecutionSettings, mock(Logger.class));
    }

    @Test
    void should_run_script_when_settings_are_resolved() {
        final Path nodeInstallationDirectory = ANY_PATH.resolve("node");
        final ScriptProperties scriptProperties = new ScriptProperties(execOperations, ANY_PATH.resolve("frontend"),
            ExecutableType.NPM, nodeInstallationDirectory, SCRIPT, LOCAL_PLATFORM);
        final Set<Path> executablePaths = Set.of();
        final List<String> arguments = List.of();
        final ExecutionSettings executionSettings = ExecutionSettings
            .builder()
            .workingDirectoryPath(ANY_PATH.resolve("work"))
            .additionalExecutablePaths(executablePaths)
            .executablePath(nodeInstallationDirectory.resolve("npm"))
            .arguments(arguments)
            .build();
        when(resolveExecutionSettings.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(scriptProperties.getPackageJsonDirectoryPath())
            .executableType(scriptProperties.getExecutableType())
            .nodeInstallDirectoryPath(scriptProperties.getNodeInstallDirectoryPath())
            .platform(scriptProperties.getPlatform())
            .script(scriptProperties.getScript())
            .build())).thenReturn(executionSettings);
        final ExecResult execResult = mock(ExecResult.class);
        when(execOperations.exec(any(ExecSpecAction.class))).thenReturn(execResult);
        when(execResult.rethrowFailure()).thenReturn(execResult);

        adapter.execute(scriptProperties);

        final ArgumentCaptor<ExecSpecAction> execSpecActionArgumentCaptor = ArgumentCaptor.forClass(
            ExecSpecAction.class);
        verify(execOperations).exec(execSpecActionArgumentCaptor.capture());
        assertThat(execSpecActionArgumentCaptor.getValue()).satisfies(execSpecAction -> {
            assertThat(execSpecAction.getExecutionSettings()).satisfies(eS -> {
                assertThat(eS.getWorkingDirectoryPath()).isEqualTo(executionSettings.getWorkingDirectoryPath());
                assertThat(eS.getAdditionalExecutablePaths()).isEqualTo(
                    executionSettings.getAdditionalExecutablePaths());
                assertThat(eS.getExecutablePath()).isEqualTo(executionSettings.getExecutablePath());
            });
            assertThat(execSpecAction.getAfterConfiguredConsumer()).isNotNull();
        });
        verify(execResult).assertNormalExitValue();
        verifyNoMoreInteractions(resolveExecutionSettings, execOperations, execResult);
    }
}
