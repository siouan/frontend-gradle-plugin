package org.siouan.frontendgradleplugin.test;

import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_COREPACK_TASK_NAME;
import static org.siouan.frontendgradleplugin.FrontendGradlePlugin.INSTALL_NODE_TASK_NAME;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn;

/**
 * Class providing static utilities only to build custom tasks.
 */
public final class TaskTypes {

    private TaskTypes() {
    }

    public static String buildCorepackTaskDefinition(final String taskName, final String script) {
        return buildTaskDefinition(taskName, RunCorepack.class, Set.of(INSTALL_COREPACK_TASK_NAME), script);
    }

    public static String buildNodeTaskDefinition(final String taskName, final String script) {
        return buildTaskDefinition(taskName, RunNode.class, Set.of(INSTALL_NODE_TASK_NAME), script);
    }

    public static String buildNodeTaskDefinition(final String taskName, final String script,
        final Map<String, String> environmentVariables) {
        return buildTaskDefinition(taskName, RunNode.class, Set.of(INSTALL_NODE_TASK_NAME), script,
            environmentVariables);
    }

    public static String buildNpmTaskDefinition(final String taskName, final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, Set.of(), script);
    }

    public static String buildNpmTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, Set.of(dependsOnTaskName), script);
    }

    public static String buildNpmTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, dependsOnTaskNames, script);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final String script) {
        return buildTaskDefinition(taskName, RunPnpm.class, Set.of(), script);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String script) {
        return buildTaskDefinition(taskName, RunPnpm.class, Set.of(dependsOnTaskName), script);
    }

    public static String buildPnpmTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String script) {
        return buildTaskDefinition(taskName, RunPnpm.class, dependsOnTaskNames, script);
    }

    public static String buildYarnTaskDefinition(final String taskName, final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, Set.of(), script);
    }

    public static String buildYarnTaskDefinition(final String taskName, final String dependsOnTaskName,
        final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, Set.of(dependsOnTaskName), script);
    }

    public static String buildYarnTaskDefinition(final String taskName, final Set<String> dependsOnTaskNames,
        final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, dependsOnTaskNames, script);
    }

    public static Path createJavascriptFileLoggingProcessTitle(final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log(process.title);\n");
        }
        return scriptPath;
    }

    private static String buildTaskDefinition(final String taskName, final Class<?> taskTypeClass,
        final Set<String> dependsOnTaskNames, final String script) {
        return buildTaskDefinition(taskName, taskTypeClass, dependsOnTaskNames, script, Map.of());
    }

    private static String buildTaskDefinition(final String taskName, final Class<?> taskTypeClass,
        final Set<String> dependsOnTaskNames, final String script, final Map<String, String> environmentVariables) {
        final StringBuilder definition = new StringBuilder();
        definition.append("tasks.register('");
        definition.append(taskName);
        definition.append("', ");
        definition.append(taskTypeClass.getName());
        definition.append(") {\n");
        dependsOnTaskNames.forEach(dependsOnTaskName -> {
            definition.append("dependsOn tasks.named('");
            definition.append(dependsOnTaskName);
            definition.append("')\n");
        });
        if (script != null) {
            definition.append("script = '");
            definition.append(script);
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
