<template>
    <fgp-task name="enableYarnBerry">
        <template v-slot:title>Enable Yarn Berry installation</template>
        <template v-slot:description>
            <p>
                The task enables Yarn Berry support as described in the
                <fgp-link href="https://yarnpkg.com/getting-started/install#per-project-install">installation notes</fgp-link>
                and
                <fgp-link href="https://yarnpkg.com/getting-started/migration#step-by-step">migration guide</fgp-link>
                of <fgp-yarn-link :version="2" />. This is done by executing command
                <fgp-code>yarn set version berry</fgp-code>, using the <fgp-code>yarn</fgp-code> executable globally
                installed by the <fgp-task-link name="installGlobalYarn" /> task.
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
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-enable-yarn-berry-task", {
    components: { fgpCode, fgpInfo, fgpTask, fgpTaskLink, fgpYarnLink }
});
</script>
