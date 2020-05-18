<template>
    <fgp-task name="installNode">
        <template v-slot:title>Install <fgp-nodejs-link /></template>
        <template v-slot:description>
            The task downloads a <fgp-nodejs-link /> distribution, verifies its integrity, and installs it in the
            directory pointed by the <fgp-property-link name="nodeInstallDirectory" /> property. The URL used to
            download the distribution is resolved using the
            <fgp-property-link name="nodeDistributionUrlRoot" /> property and the
            <fgp-property-link name="nodeDistributionUrlPathPattern" /> property. Checking the distribution integrity
            consists of downloading a file providing the distribution shasum. This file is expected to be in the same
            remote web directory than the distribution. For example, if the distribution is located at
            <fgp-code>https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip</fgp-code>, the plugin attempts to download
            the shasum file located at <fgp-code>https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt</fgp-code>. Optionally,
            defining the <fgp-property-link name="proxyHost" /> property and the
            <fgp-property-link name="proxyPort" /> property allows to use a proxy server when downloading the
            distribution and the shasum.

            <p>
                If a <fgp-nodejs-link /> distribution is already installed in the local platform - either as a global
                installation or as an installation performed by another Gradle (sub-)project - and shall be used instead
                of a downloaded distribution, take a look at the
                <fgp-property-link name="nodeDistributionProvided" /> property instead: when <fgp-code>true</fgp-code>,
                this task is ignored if invoked during a Gradle build, and its outcome will always be
                <fgp-gradle-task-outcome-link outcome="SKIPPED" />.
            </p>

            The task takes advantage of
            <fgp-gradle-guides-link path="/performance/#incremental_build"
                >Gradle incremental build</fgp-gradle-guides-link
            >, and is not executed again unless one of its inputs/outputs changed. Consequently, if the task takes part
            of a Gradle build, its outcome will be <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />.

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
import fgpInfo from "../layout/info";
import fgpNodejsLink from "../link/nodejs-link";
import fgpTask from "../layout/task";

export default Vue.component("fgp-install-node-task", {
    components: { fgpGradleGuidesLink, fgpGradleTaskOutcomeLink, fgpInfo, fgpNodejsLink, fgpTask }
});
</script>
