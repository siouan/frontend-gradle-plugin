package org.siouan.frontendgradleplugin.test.util;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.siouan.frontendgradleplugin.FrontendGradlePlugin;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx;
import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn;

/**
 * Class providing static utilities only to build custom tasks.
 */
public final class TaskTypes {

    private TaskTypes() {
    }

    @Nonnull
    public static String buildNodeTaskDefinition(@Nonnull final String taskName, @Nullable final String script) {
        return buildNodeTaskDefinition(taskName, FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, script);
    }

    @Nonnull
    public static String buildNodeTaskDefinition(@Nonnull final String taskName,
        @Nullable final String dependsOnTaskName, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNode.class,
            (dependsOnTaskName == null) ? emptySet() : singleton(dependsOnTaskName), script);
    }

    @Nonnull
    public static String buildNpxTaskDefinition(@Nonnull final String taskName, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNpx.class, emptySet(), script);
    }

    @Nonnull
    public static String buildNpxTaskDefinition(@Nonnull final String taskName, @Nonnull final String dependsOnTaskName,
        @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNpx.class, singleton(dependsOnTaskName), script);
    }

    @Nonnull
    public static String buildNpmTaskDefinition(@Nonnull final String taskName, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, emptySet(), script);
    }

    @Nonnull
    public static String buildNpmTaskDefinition(@Nonnull final String taskName, @Nonnull final String dependsOnTaskName,
        @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, singleton(dependsOnTaskName), script);
    }

    @Nonnull
    public static String buildNpmTaskDefinition(@Nonnull final String taskName,
        @Nonnull final Set<String> dependsOnTaskNames, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunNpm.class, dependsOnTaskNames, script);
    }

    @Nonnull
    public static String buildYarnTaskDefinition(@Nonnull final String taskName, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, emptySet(), script);
    }

    @Nonnull
    public static String buildYarnTaskDefinition(@Nonnull final String taskName,
        @Nonnull final String dependsOnTaskName, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, singleton(dependsOnTaskName), script);
    }

    @Nonnull
    public static String buildYarnTaskDefinition(@Nonnull final String taskName,
        @Nonnull final Set<String> dependsOnTaskNames, @Nullable final String script) {
        return buildTaskDefinition(taskName, RunYarn.class, dependsOnTaskNames, script);
    }

    @Nonnull
    public static Path createJavascriptFile(@Nonnull final Path scriptPath) throws IOException {
        try (final Writer buildFileWriter = Files.newBufferedWriter(scriptPath)) {
            buildFileWriter.append("console.log('Hello!');\n");
        }
        return scriptPath;
    }

    @Nonnull
    private static String buildTaskDefinition(@Nonnull final String taskName, @Nonnull final Class<?> taskTypeClass,
        @Nonnull final Set<String> dependsOnTaskNames, @Nullable final String script) {
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
        definition.append("}\n");
        return definition.toString();
    }
}
