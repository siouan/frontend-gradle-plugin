<template>
    <fgp-task name="resolvePackageManager">
        <template v-slot:title>Resolve package manager</template>
        <template v-slot:description>
            <p>
                The task identifies the package manager applicable to the project by parsing the
                <fgp-nodejs-link path="/api/packages.html#packagemanager" label="packageManager" /> property located in
                the <fgp-code>package.json</fgp-code> file (pointed by the
                <fgp-property-link name="packageJsonDirectory" /> property). For example, if the
                <fgp-code>package.json</fgp-code> file contains <fgp-code>"packageManager":&nbsp;"npm@9.6.6"</fgp-code>,
                the task resolves <fgp-code>npm</fgp-code> as the package manager.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                >Gradle incremental build</fgp-gradle-guides-link>, and is not executed again unless one of its
                inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be
                <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />. The task is skipped if the
                <fgp-code>package.json</fgp-code> file does not exist in the project, and its outcome will be
                <fgp-gradle-task-outcome-link outcome="SKIPPED" />.
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

export default Vue.component("fgp-resolve-package-manager-task", {
    components: { fgpCode, fgpInfo, fgpTask }
});
</script>
