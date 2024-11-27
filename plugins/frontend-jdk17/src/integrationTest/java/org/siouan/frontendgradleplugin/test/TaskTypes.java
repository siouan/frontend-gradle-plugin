package org.siouan.frontendgradleplugin.test;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_COREPACK_TASK_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_NODE_TASK_NAME;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepackTaskType;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmTaskType;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType;

/**
 * Class providing static utilities only to build custom tasks.
 */
public final class TaskTypes {

    private TaskTypes() {
    }

    public static String buildCorepackTaskDefinition(final String taskName, final String executableArgs) {
        return buildTaskDefinition(taskName, RunCorepackTaskType.class, Set.of(INSTALL_COREPACK_TASK_NAME),
            executableArgs);
    }

    public static String buildNodeTaskDefinition(final String taskName, final String executableArgs) {
        return buildTaskDefinition(taskName, RunNodeTaskType.class, Set.of(INSTALL_NODE_TASK_NAME), executableArgs);
    }

    public static String buildNodeTaskDefinition(final String taskName, final String executableArgs,
        final Map<String, String> environmentVariables) {
        return buildTaskDefinition(taskName, RunNodeTaskType.class, Set.of(INSTALL_NODE_TASK_NAME), executableArgs,
            environmentVariables);
    }

    public static String buildNpmTaskDefinition(final String taskName, final String executableArgs) {
        return buildTaskDefinition(taskName, RunNpmTaskType.class, Set.of(), executableArgs);
    }

    public static String buildNpmTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunNpmTaskType.class, Set.of(dependsOnTaskName), executableArgs);
    }

    public static String buildNpmTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunNpmTaskType.class, dependsOnTaskNames, executableArgs);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final String executableArgs) {
        return buildTaskDefinition(taskName, RunPnpmTaskType.class, Set.of(), executableArgs);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunPnpmTaskType.class, Set.of(dependsOnTaskName), executableArgs);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunPnpmTaskType.class, dependsOnTaskNames, executableArgs);
    }

    public static String buildYarnTaskDefinition(final String taskName, final String executableArgs) {
        return buildTaskDefinition(taskName, RunYarnTaskType.class, Set.of(), executableArgs);
    }

    public static String buildYarnTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunYarnTaskType.class, Set.of(dependsOnTaskName), executableArgs);
    }

    public static String buildYarnTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String executableArgs) {
        return buildTaskDefinition(taskName, RunYarnTaskType.class, dependsOnTaskNames, executableArgs);
    }

    public static Path createJavascriptFileLoggingProcessTitle(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log(process.title);\n");
        }
        return scriptPath;
    }

    private static String buildTaskDefinition(final String taskName, final Class<?> taskTypeClass,
        final Set<String> dependsOnTaskNames, final String executableArgs) {
        return buildTaskDefinition(taskName, taskTypeClass, dependsOnTaskNames, executableArgs, Map.of());
    }

    private static String buildTaskDefinition(final String taskName, final Class<?> taskTypeClass,
        final Set<String> dependsOnTaskNames, final String executableArgs,
        final Map<String, String> environmentVariables) {
        final StringBuilder definition = new StringBuilder();
        definition.append("tasks.register('");
        definition.append(taskName);
        definition.append("', ");
        definition.append(taskTypeClass.getName());
        definition.append(") {\n");
        dependsOnTaskNames.forEach(dependsOnTaskName -> {
            definition.append("dependsOn '");
            definition.append(dependsOnTaskName);
            definition.append("'\n");
        });
        if (executableArgs != null) {
            definition.append("args = '");
            definition.append(executableArgs);
            definition.append("'\n");
        }
        if (!environmentVariables.isEmpty()) {
            environmentVariables.forEach((variableName, variableValue) -> definition
                .append("environmentVariables.put(\"")
                .append(variableName)
                .append("\", \"")
                .append(variableValue)
                .append("\")\n"));
        }
        definition.append("}\n");
        return definition.toString();
    }
}
