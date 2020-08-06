<template>
    <fgp-task name="installYarn">
        <template v-slot:title>Install <fgp-yarn-link /></template>
        <template v-slot:description>
            <p>
                If the <fgp-property-link name="yarnEnabled" /> property is <fgp-code>true</fgp-code>, the task
                downloads and install a <fgp-yarn-link /> distribution in the directory pointed by the
                <fgp-property-link name="yarnInstallDirectory" />. The URL used to download the distribution is resolved
                using the <fgp-property-link name="yarnDistributionUrlRoot" /> property and the
                <fgp-property-link name="yarnDistributionUrlPathPattern" /> property. Optionally, defining the
                <fgp-property-link name="proxyHost" /> property and the <fgp-property-link name="proxyPort" /> property
                allows to use a proxy server when downloading the distribution and the shasum.
            </p>
            <p>
                If a <fgp-yarn-link /> distribution is already installed in the local platform - either as a global
                installation or as an installation performed by another Gradle (sub-)project - and shall be used instead
                of a downloaded distribution, take a look at the
                <fgp-property-link name="yarnDistributionProvided" /> property instead: when <fgp-code>true</fgp-code>,
                this task is ignored if invoked during a Gradle build, and its outcome will always be
                <fgp-gradle-task-outcome-link outcome="SKIPPED" />.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                    >Gradle incremental build</fgp-gradle-guides-link
                >, and is not executed again unless one of its inputs/outputs changed. Consequently, if the task takes
                part of a Gradle build, its outcome will be <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />.
            </p>
            <fgp-info>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </fgp-info>
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpGradleGuidesLink from "../link/gradle-guides-link";
import fgpGradleTaskOutcomeLink from "../link/gradle-task-outcome-link";
import fgpInfo from "../info";
import fgpTask from "./task";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-install-yarn-task", {
    components: { fgpGradleGuidesLink, fgpGradleTaskOutcomeLink, fgpInfo, fgpTask, fgpYarnLink }
});
</script>
