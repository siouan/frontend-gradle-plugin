<template>
    <fgp-task name="installPackageManager">
        <template v-slot:title>Install package manager</template>
        <template v-slot:description>
            <p>
                The task installs the package manager resolved with task <fgp-task-link name="resolvePackageManager" />,
                by executing command <fgp-code>corepack enable &lt;package-manager&gt;</fgp-code>.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                >Gradle incremental build</fgp-gradle-guides-link>, and is not executed again unless one of its
                inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be
                <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />. The task is skipped if task
                <fgp-task-link name="resolvePackageManager" /> was skipped.
            </p>
            <fgp-info>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </fgp-info>
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpInfo from "../info";
import fgpTask from "./task";
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-install-package-manager-task", {
    components: { fgpCode, fgpInfo, fgpTask, fgpTaskLink }
});
</script>
