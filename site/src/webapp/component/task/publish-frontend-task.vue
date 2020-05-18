<template>
    <fgp-task name="publishFrontend">
        <template v-slot:title>Publish frontend artifacts</template>
        <template v-slot:description>
            This task may be used to integrate a publish script into a Gradle build. The publish script shall be defined
            in the <fgp-code>package.json</fgp-code> file, and the <fgp-property-link name="publishScript" /> property
            shall be set with the corresponding <fgp-code>npm</fgp-code>/<fgp-code>yarn</fgp-code> command. This task
            depends on the <fgp-task-link name="assembleFrontend" /> task, and is skipped either if the
            <fgp-property-link name="assembleScript" /> property is <fgp-code>null</fgp-code>, or if the
            <fgp-property-link name="publishScript" /> property is <fgp-code>null</fgp-code>. Apart from direct
            execution, the task is also executed when the Gradle <fgp-code>publish</fgp-code> task is executed.
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpPropertyLink from "../link/property-link";
import fgpTask from "../layout/task";
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-publish-frontend-task", {
    components: {
        fgpCode,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink
    }
});
</script>
