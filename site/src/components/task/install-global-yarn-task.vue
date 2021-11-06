<template>
    <fgp-task name="installGlobalYarn">
        <template v-slot:title>
            Install <fgp-yarn-link /> globally in the <fgp-nodejs-link /> distribution
        </template>
        <template v-slot:description>
            <p>
                The task installs the latest <fgp-yarn-link /> release as a global <fgp-nodejs-link /> dependency. This
                is a step required to spawn per-project installations, as explained in the
                <fgp-link href="https://yarnpkg.com/getting-started/install#about-global-installs">installation notes</fgp-link>
                and
                <fgp-link href="https://yarnpkg.com/getting-started/migration#step-by-step">migration guide</fgp-link>
                of <fgp-yarn-link :version="2" />. The download and installation is done by executing command
                <fgp-code>npm install -g yarn</fgp-code>, using the <fgp-code>npm</fgp-code> executable located in the
                <fgp-nodejs-link /> distribution deployed by the <fgp-task-link name="installNode" /> task, or in the
                provided distribution if the <fgp-property-link name="nodeDistributionProvided" /> property is set to
                <fgp-code>true</fgp-code>.
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
import fgpNodejsLink from "../link/nodejs-link";
import fgpPropertyLink from "../link/property-link";
import fgpTask from "./task";
import fgpTaskLink from "../link/task-link";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-install-global-yarn-task", {
    components: { fgpCode, fgpInfo, fgpNodejsLink, fgpPropertyLink, fgpTask, fgpTaskLink, fgpYarnLink }
});
</script>
