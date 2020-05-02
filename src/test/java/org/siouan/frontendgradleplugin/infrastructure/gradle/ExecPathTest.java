package org.siouan.frontendgradleplugin.infrastructure.gradle;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@Disabled
class ExecPathTest {

    private static final String BUILD_FILE_NAME = "build.gradle";

    private static final String COMMAND_NAME = "mycommand";

    private static final String TASK_NAME = "myTask";

    private static final String TASK_DEFINITION =
        "tasks.register('" + TASK_NAME + "', Exec) {\ncommandLine = '" + COMMAND_NAME + "'\n}\n";

    private static Path BIN1_DIRECTORY_PATH;

    private static Path BIN2_DIRECTORY_PATH;

    private static Path BIN1_COMMAND_FILE_PATH;

    private static Path BIN2_COMMAND_FILE_PATH;

    private static String DEFAULT_PATH;

    @TempDir
    Path projectDirectoryPath;

    @BeforeAll
    static void setUpClass() throws IOException {
        BIN1_DIRECTORY_PATH = Files.createTempDirectory("bin1-");
        BIN2_DIRECTORY_PATH = Files.createTempDirectory("bin2-");
        BIN1_COMMAND_FILE_PATH = writeCommand(BIN1_DIRECTORY_PATH.resolve(COMMAND_NAME));
        BIN2_COMMAND_FILE_PATH = writeCommand(BIN2_DIRECTORY_PATH.resolve(COMMAND_NAME));
        DEFAULT_PATH = System.getenv("PATH");
    }

    @AfterAll
    static void tearDownClass() throws IOException {
        Files.deleteIfExists(BIN1_COMMAND_FILE_PATH);
        Files.deleteIfExists(BIN2_COMMAND_FILE_PATH);
        Files.deleteIfExists(BIN1_DIRECTORY_PATH);
        Files.deleteIfExists(BIN2_DIRECTORY_PATH);
    }

    private static Path writeCommand(final Path filePath) throws IOException {
        try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            bufferedWriter.append("#!/bin/sh");
            bufferedWriter.newLine();
            bufferedWriter.append("echo  \"$0: $PATH\"");
            bufferedWriter.newLine();
        }
        return Files.setPosixFilePermissions(filePath,
            EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_EXECUTE,
                PosixFilePermission.GROUP_READ, PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OTHERS_READ,
                PosixFilePermission.OTHERS_EXECUTE));
    }

    @BeforeEach
    void setUp() throws IOException {
        createBuildFile(projectDirectoryPath);
    }

    @Order(1)
    @Test
    void shouldRunTaskWithCommandInBin1() {
        final BuildResult result = runGradle(projectDirectoryPath, BIN1_DIRECTORY_PATH);

        assertThat(result.task(':' + TASK_NAME).getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
    }

    @Order(2)
    @Test
    void shouldRunTaskWithCommandInBin2() {
        final BuildResult result = runGradle(projectDirectoryPath, BIN2_DIRECTORY_PATH);

        assertThat(result.task(':' + TASK_NAME).getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
    }

    private void createBuildFile(final Path projectDirectory) throws IOException {
        final Path buildFilePath = projectDirectory.resolve(BUILD_FILE_NAME);
        try (final BufferedWriter buildFileWriter = Files.newBufferedWriter(buildFilePath)) {
            buildFileWriter.append(TASK_DEFINITION);
        }
    }

    private BuildResult runGradle(final Path projectDirectory, final Path binDirectory) {
        final Map<String, String> environment = new HashMap<>();
        environment.put("PATH", binDirectory + File.pathSeparator + DEFAULT_PATH);
        return GradleRunner
            .create()
            .withGradleVersion("5.1")
            .withProjectDir(projectDirectory.toFile())
            .withArguments(TASK_NAME, "-s")
            .withEnvironment(environment)
            .forwardOutput()
            .build();
    }
}
