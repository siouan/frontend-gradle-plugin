<template>
    <fgp-task name="checkFrontend">
        <template v-slot:title>Check frontend application</template>
        <template v-slot:description>
            This task may be used to integrate a check script into a Gradle build. The check script shall be defined in
            the <fgp-code>package.json</fgp-code> file, and the <fgp-property-link name="checkScript" /> property shall
            be set with the corresponding <fgp-code>npm</fgp-code>/<fgp-code>yarn</fgp-code> command. A typical check
            script may lint frontend source files, execute tests, and perform additional analysis actions. This task
            depends on the <fgp-task-link name="installFrontend" /> task, and is skipped if the
            <fgp-property-link name="checkScript" /> property is <fgp-code>null</fgp-code>. Apart from direct execution,
            the task is also executed when the Gradle lifecycle
            <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks">check</fgp-gradle-docs-link>
            task is executed.
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpPropertyLink from "../link/property-link";
import fgpTask from "../layout/task";
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-check-frontend-task", {
    components: {
        fgpCode,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink
    }
});
</script>
