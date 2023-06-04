package org.siouan.frontendgradleplugin.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;
import org.siouan.frontendgradleplugin.FrontendGradlePlugin;

/**
 * Class providing assertions for Gradle builds.
 */
public final class GradleBuildAssertions {

    private static final List<String> TASK_NAMES = List.of(FrontendGradlePlugin.INSTALL_NODE_TASK_NAME,
        FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME, FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME,
        FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, FrontendGradlePlugin.CLEAN_TASK_NAME,
        FrontendGradlePlugin.ASSEMBLE_TASK_NAME, FrontendGradlePlugin.CHECK_TASK_NAME,
        FrontendGradlePlugin.PUBLISH_TASK_NAME, FrontendGradlePlugin.GRADLE_CLEAN_TASK_NAME,
        FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME,
        FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME);

    private GradleBuildAssertions() {
    }

    public static void assertAssembleTaskOutcomes(final BuildResult result,
        final PluginTaskOutcome installNodeTaskOutcome, final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome assembleTaskOutcome, final PluginTaskOutcome gradleAssembleTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, null, assembleTaskOutcome, null, null, null,
            gradleAssembleTaskOutcome, null, null, null, null);
    }

    public static void assertCheckTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome checkTaskOutcome, final PluginTaskOutcome gradleCheckTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, null, null, checkTaskOutcome, null, null,
            null, gradleCheckTaskOutcome, null, null, null);
    }

    public static void assertCleanTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome cleanTaskOutcome, final PluginTaskOutcome gradleCleanTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, cleanTaskOutcome, null, null, null,
            gradleCleanTaskOutcome, null, null, null, null, null);
    }

    public static void assertPublishTaskOutcomes(final BuildResult result,
        final PluginTaskOutcome installNodeTaskOutcome, final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome publishTaskOutcome, final PluginTaskOutcome gradlePublishTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, null, null, null, publishTaskOutcome, null,
            null, null, gradlePublishTaskOutcome, null, null);
    }

    public static void assertTaskFailed(final BuildResult result, final String taskName) {
        assertTaskOutcomes(result, null, null, null, null, null, null, null, null, null, null, null, null, null,
            taskName, PluginTaskOutcome.FAILED);
    }

    public static void assertTaskOutcome(final BuildResult result, final String taskName,
        final PluginTaskOutcome taskOutcome) {
        assertTaskOutcomes(result, null, null, null, null, null, null, null, null, null, null, null, null, null,
            taskName, taskOutcome);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final String taskName, final PluginTaskOutcome taskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, null, null, null, null, null, null, null, null, null,
            null, null, taskName, taskOutcome);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome, null, null, null,
            null, null, null, null, null, null, null, null, null);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, null, null, null, null, null, null, null, null, null, null, null);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final String taskName,
        final PluginTaskOutcome taskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, null, null, null, null, null, null, null, null, null, taskName,
            taskOutcome);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, null, null, null, null, null, null, null,
            null, null, null);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final String taskName, final PluginTaskOutcome taskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, null, null, null, null, null, null, null,
            null, taskName, taskOutcome);
    }

    public static void assertTaskOutcomes(final BuildResult result, final PluginTaskOutcome installNodeTaskOutcome,
        final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome cleanTaskOutcome, final PluginTaskOutcome assembleTaskOutcome,
        final PluginTaskOutcome checkTaskOutcome, final PluginTaskOutcome publishTaskOutcome,
        final PluginTaskOutcome gradleCleanTaskOutcome, final PluginTaskOutcome gradleAssembleTaskOutcome,
        final PluginTaskOutcome gradleCheckTaskOutcome, final PluginTaskOutcome gradlePublishTaskOutcome) {
        assertTaskOutcomes(result, null, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, cleanTaskOutcome, assembleTaskOutcome,
            checkTaskOutcome, publishTaskOutcome, gradleCleanTaskOutcome, gradleAssembleTaskOutcome,
            gradleCheckTaskOutcome, gradlePublishTaskOutcome, null, null);
    }

    public static void assertTaskOutcomes(final BuildResult result, final String projectName,
        final PluginTaskOutcome installNodeTaskOutcome, final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome cleanTaskOutcome, final PluginTaskOutcome assembleTaskOutcome,
        final PluginTaskOutcome checkTaskOutcome, final PluginTaskOutcome publishTaskOutcome,
        final PluginTaskOutcome gradleCleanTaskOutcome, final PluginTaskOutcome gradleAssembleTaskOutcome,
        final PluginTaskOutcome gradleCheckTaskOutcome, final PluginTaskOutcome gradlePublishTaskOutcome) {
        assertTaskOutcomes(result, projectName, installNodeTaskOutcome, resolvePackageManagerTaskOutcome,
            installPackageManagerTaskOutcome, installFrontendTaskOutcome, cleanTaskOutcome, assembleTaskOutcome,
            checkTaskOutcome, publishTaskOutcome, gradleCleanTaskOutcome, gradleAssembleTaskOutcome,
            gradleCheckTaskOutcome, gradlePublishTaskOutcome, null, null);
    }

    public static void assertTaskOutcomes(final BuildResult result, final String projectName,
        final PluginTaskOutcome installNodeTaskOutcome, final PluginTaskOutcome resolvePackageManagerTaskOutcome,
        final PluginTaskOutcome installPackageManagerTaskOutcome, final PluginTaskOutcome installFrontendTaskOutcome,
        final PluginTaskOutcome cleanTaskOutcome, final PluginTaskOutcome assembleTaskOutcome,
        final PluginTaskOutcome checkTaskOutcome, final PluginTaskOutcome publishTaskOutcome,
        final PluginTaskOutcome gradleCleanTaskOutcome, final PluginTaskOutcome gradleAssembleTaskOutcome,
        final PluginTaskOutcome gradleCheckTaskOutcome, final PluginTaskOutcome gradlePublishTaskOutcome,
        final String taskName, final PluginTaskOutcome taskOutcome) {
        final Map<String, PluginTaskOutcome> outcomeByTaskNames = new HashMap<>();
        outcomeByTaskNames.put(FrontendGradlePlugin.INSTALL_NODE_TASK_NAME, installNodeTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.RESOLVE_PACKAGE_MANAGER_TASK_NAME,
            resolvePackageManagerTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.INSTALL_PACKAGE_MANAGER_TASK_NAME,
            installPackageManagerTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.INSTALL_FRONTEND_TASK_NAME, installFrontendTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.CLEAN_TASK_NAME, cleanTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.ASSEMBLE_TASK_NAME, assembleTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.CHECK_TASK_NAME, checkTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.PUBLISH_TASK_NAME, publishTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.GRADLE_CLEAN_TASK_NAME, gradleCleanTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.GRADLE_ASSEMBLE_TASK_NAME, gradleAssembleTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.GRADLE_CHECK_TASK_NAME, gradleCheckTaskOutcome);
        outcomeByTaskNames.put(FrontendGradlePlugin.GRADLE_PUBLISH_TASK_NAME, gradlePublishTaskOutcome);
        if (taskName != null) {
            outcomeByTaskNames.put(taskName, taskOutcome);
        }
        Stream.concat(TASK_NAMES.stream(), Stream.ofNullable(taskName)).forEach(aTaskName -> {
            final PluginTaskOutcome anOutcome = outcomeByTaskNames.get(aTaskName);
            if (anOutcome != null) {
                if (anOutcome == PluginTaskOutcome.IGNORED) {
                    assertTaskIgnored(result, aTaskName);
                } else {
                    assertTaskOutcome(result, projectName, aTaskName, anOutcome.getNativeOutcome());
                }
            }
        });
    }

    /**
     * Asserts a task was not part of a build.
     *
     * @param result Build result.
     * @param taskName Task name.
     */
    public static void assertTaskIgnored(final BuildResult result, final String taskName) {
        assertThat(getBuildResultTask(result, getTaskId(taskName))).isEmpty();
    }

    /**
     * Asserts a task was part of a build result, and finishes with a given outcome.
     *
     * @param result Build result.
     * @param taskName Task name.
     * @param taskOutcome Expected outcome.
     */
    private static void assertTaskOutcome(final BuildResult result, final String projectName, final String taskName,
        final TaskOutcome taskOutcome) {
        assertThat(getBuildResultTask(result, getTaskId(projectName, taskName))
            .map(BuildTask::getOutcome)
            .orElseThrow(() -> new RuntimeException("Task not found: " + taskName)))
            .withFailMessage("Task '" + taskName + "' was expected to be " + taskOutcome)
            .isEqualTo(taskOutcome);
    }

    private static Optional<BuildTask> getBuildResultTask(final BuildResult result, final String taskId) {
        return Optional.ofNullable(result.task(taskId));
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param projectName Project name.
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(final String projectName, final String taskName) {
        final String taskId = getTaskId(taskName);
        return (projectName == null) ? taskId : ':' + projectName + taskId;
    }

    /**
     * Gets the task ID for future reference with Gradle API.
     *
     * @param taskName Task name.
     * @return Task ID.
     */
    public static String getTaskId(final String taskName) {
        return ':' + taskName;
    }
}
