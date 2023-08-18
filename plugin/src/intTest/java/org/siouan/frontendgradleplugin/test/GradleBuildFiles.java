package org.siouan.frontendgradleplugin.test;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gradle.api.logging.LogLevel;

/**
 * Class providing static utilities to create Gradle build files.
 */
public final class GradleBuildFiles {

    private static final String BUILD_FILE_NAME = "build.gradle";

    private GradleBuildFiles() {
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Map<String, ?> properties)
        throws IOException {
        createBuildFile(projectDirectory, true, true, Set.of(), properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final String additionalContent) throws IOException {
        createBuildFile(projectDirectory, true, true, Set.of(), Map.of(), additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     */
    public static void createBuildFile(final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied) throws IOException {
        createBuildFile(projectDirectory, pluginEnabled, pluginApplied, Set.of(), Map.of(), null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Set<String> plugins,
        final Map<String, ?> properties) throws IOException {
        createBuildFile(projectDirectory, plugins, properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Map<String, ?> properties,
        final String additionalContent) throws IOException {
        createBuildFile(projectDirectory, true, true, Set.of(), properties, additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied, final Map<String, ?> properties) throws IOException {
        createBuildFile(projectDirectory, pluginEnabled, pluginApplied, Set.of(), properties, null);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param pluginEnabled Whether this plugin must be added in the 'plugins' block.
     * @param pluginApplied Whether this plugin must be applied (only relevant if the {@code pluginEnabled} parameter is
     * {@code true}.
     * @param plugins Set of additional plugin definitions.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final boolean pluginEnabled,
        final boolean pluginApplied, final Set<String> plugins, final Map<String, ?> properties,
        final String additionalContent) throws IOException {
        final Set<String> pluginsWithFrontend = new HashSet<>(plugins);
        if (pluginEnabled) {
            pluginsWithFrontend.add("id 'org.siouan.frontend-jdk11' apply " + pluginApplied);
        }
        createBuildFile(projectDirectory, pluginsWithFrontend, properties, additionalContent);
    }

    /**
     * Creates a build file.
     *
     * @param projectDirectory Project directory.
     * @param plugins Set of plugin definitions.
     * @param properties Map of properties. If a value is a {@link Map} itself, then its entries are written under a
     * child node in the build file.
     * @param additionalContent Additional content to append at the end of the build file.
     */
    public static void createBuildFile(final Path projectDirectory, final Set<String> plugins,
        final Map<String, ?> properties, final String additionalContent) throws IOException {
        final Path buildFilePath = projectDirectory.resolve(BUILD_FILE_NAME);
        try (final Writer buildFileWriter = Files.newBufferedWriter(buildFilePath)) {
            if (!plugins.isEmpty()) {
                final Map<String, ?> pluginsBlock = new HashMap<>();
                plugins.forEach(p -> pluginsBlock.put(p, null));
                for (final Map.Entry<String, ?> property : Map.of("plugins", Collections.unmodifiableMap(pluginsBlock)).entrySet()) {
                    writeProperty(buildFileWriter, property.getKey(), property.getValue());
                }
            }
            if (!properties.isEmpty()) {
                for (final Map.Entry<String, ?> property : Map.of("frontend", properties).entrySet()) {
                    writeProperty(buildFileWriter, property.getKey(), property.getValue());
                }
            }
            if (additionalContent != null) {
                buildFileWriter.append(additionalContent);
            }
        }
    }

    private static void writeProperty(final Writer buildFileWriter, final String property, final Object value)
        throws IOException {
        buildFileWriter.append(property);
        if (value instanceof Map) {
            buildFileWriter.append(" {\n");
            for (final Object key : ((Map<?, ?>) value).keySet()) {
                writeProperty(buildFileWriter, key.toString(), ((Map<?, ?>) value).get(key));
            }
            buildFileWriter.append("}");
        } else if ((value instanceof Boolean) || (value instanceof Number)) {
            buildFileWriter.append(" = ");
            buildFileWriter.append(value.toString());
        } else if (value instanceof Path) {
            buildFileWriter.append(" = file(\"");
            buildFileWriter.append(value.toString().replace('\\', '/'));
            buildFileWriter.append("\")");
        } else if (value instanceof LogLevel) {
            buildFileWriter.append(" = ").append(LogLevel.class.getSimpleName()).append('.').append(value.toString());
        } else if (value != null) {
            buildFileWriter.append(" = '");
            buildFileWriter.append(value.toString());
            buildFileWriter.append('\'');
        }
        buildFileWriter.append('\n');
    }
}
