package org.siouan.frontendgradleplugin.infrastructure.gradle;

import lombok.RequiredArgsConstructor;
import org.gradle.process.ExecSpec;
import org.siouan.frontendgradleplugin.domain.ExecutionSettings;
import org.siouan.frontendgradleplugin.domain.Logger;
import org.siouan.frontendgradleplugin.domain.ResolveExecutionSettings;
import org.siouan.frontendgradleplugin.domain.ResolveExecutionSettingsCommand;

/**
 * An adapter that delegates running a script to the executor available in a Gradle project.
 *
 * @since 2.0.0
 */
@RequiredArgsConstructor
public class GradleScriptRunnerAdapter {

    private final ResolveExecutionSettings resolveExecutionSettings;

    private final Logger logger;

    /**
     * Executes the given script with the executor available in the project. The executor automatically throws an
     * exception if the script execution fails, or does not terminate with a non-zero exit value.
     *
     * @param scriptProperties Script properties.
     */
    public void execute(final ScriptProperties scriptProperties) {
        final ExecutionSettings executionSettings = resolveExecutionSettings.execute(ResolveExecutionSettingsCommand
            .builder()
            .packageJsonDirectoryPath(scriptProperties.packageJsonDirectoryPath())
            .executableType(scriptProperties.executableType())
            .nodeInstallDirectoryPath(scriptProperties.nodeInstallDirectoryPath())
            .platform(scriptProperties.platform())
            .script(scriptProperties.script())
            .build());
        logger.debug("Execution settings: {}", executionSettings);

        scriptProperties
            .execOperations()
            .exec(ExecSpecAction
                .builder()
                .executionSettings(executionSettings)
                .afterConfiguredConsumer(this::logExecSpecBeforeExecution)
                .build())
            .rethrowFailure()
            .assertNormalExitValue();
    }

    private void logExecSpecBeforeExecution(final ExecSpec execSpec) {
        logger.debug("Execution environment: {}", execSpec.getEnvironment());
        logger.info("Running '{}' with arguments: [{}]", execSpec.getExecutable(),
            String.join("], [", execSpec.getArgs()));
    }
}
