package org.siouan.frontendgradleplugin.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;

/**
 * Class providing assertions for Gradle builds.
 */
public final class GradleBuildAssertions {

    private GradleBuildAssertions() {
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param projectName Project name.
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(@Nullable final String projectName, @Nonnull final String taskName) {
        final String taskId = getTaskId(taskName);
        return (projectName == null) ? taskId : ':' + projectName + taskId;
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(@Nonnull final String taskName) {
        return ':' + taskName;
    }

    /**
     * Asserts a task was part of a build result, and was executed successfully.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSuccess(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.SUCCESS);
    }

    /**
     * Asserts a task was part of a build result, and was executed successfully.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskSuccess(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.SUCCESS);
    }

    /**
     * Asserts a task was part of a build result, and was not executed as it is already up-to-date.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskUpToDate(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.UP_TO_DATE);
    }

    /**
     * Asserts a task was part of a build result, and was not executed as it is already up-to-date.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskUpToDate(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.UP_TO_DATE);
    }

    /**
     * Asserts a task was part of a build result, and was skipped.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskSkipped(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.SKIPPED);
    }

    /**
     * Asserts a task was part of a build result, and was skipped.
     *
     * @param result Build result.
     * @param projectName Project name.
     * @param taskName Task name.
     */
    public static void assertTaskSkipped(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName) {
        assertTaskOutcome(result, projectName, taskName, TaskOutcome.SKIPPED);
    }

    /**
     * Asserts a task was part of a build result, and failed.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskFailed(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertTaskOutcome(result, null, taskName, TaskOutcome.FAILED);
    }

    /**
     * Asserts a task was not part of a build.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskIgnored(@Nonnull final BuildResult result, @Nonnull final String taskName) {
        assertThat(getBuildResultTask(result, getTaskId(taskName))).isEmpty();
    }

    /**
     * Asserts a task was part of a build result, and finishes with a given outcome.
     *
     * @param result Build result.
     * @param taskName Task name.
     * @param expectedOutcome Expected outcome.
     */
    private static void assertTaskOutcome(@Nonnull final BuildResult result, @Nullable final String projectName,
        @Nonnull final String taskName, @Nonnull final TaskOutcome expectedOutcome) {
        assertThat(getBuildResultTask(result, getTaskId(projectName, taskName))
            .map(BuildTask::getOutcome)
            .orElseThrow(() -> new RuntimeException("Task not found: " + taskName))).isEqualTo(expectedOutcome);
    }

    @Nonnull
    private static Optional<BuildTask> getBuildResultTask(@Nonnull final BuildResult result,
        @Nonnull final String taskId) {
        return Optional.ofNullable(result.task(taskId));
    }
}
