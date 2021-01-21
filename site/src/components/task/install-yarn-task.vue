<template>
    <fgp-task name="installYarn">
        <template v-slot:title>Install Yarn</template>
        <template v-slot:description>
            <fgp-section-title>Starting from release 6.0.0</fgp-section-title>
            <p>
                If the <fgp-property-link name="yarnEnabled" /> property is <fgp-code>true</fgp-code>, the task
                executes command <fgp-code>yarn set version &lt;yarnVersion&gt;</fgp-code> in the project directory.
                In other words, the task installs the relevant Yarn distribution in the current project, based on the
                value of the <fgp-property-link name="yarnVersion" /> property. Downloading and installing the
                distribution is entirely managed by the <fgp-yarn-link /> distribution globally installed after
                execution of the <fgp-task-link name="installGlobalYarn" />. To customize this process (proxy usage,
                etc), please refer to Yarn's
                <fgp-link href="https://yarnpkg.com/configuration/yarnrc">configuration options</fgp-link>.
            </p>
            <fgp-section-title>Prior to release 6.0.0</fgp-section-title>
            <p>
                If the <fgp-property-link name="yarnEnabled" /> property is <fgp-code>true</fgp-code>, the task
                downloads and install a <fgp-yarn-link /> distribution in the directory pointed by the
                <fgp-property-link name="yarnInstallDirectory" />. The URL used to download the distribution is resolved
                using the <fgp-property-link name="yarnDistributionUrlRoot" /> property and the
                <fgp-property-link name="yarnDistributionUrlPathPattern" /> property. By default, the plugin relies on
                the VM <fgp-java-network-properties-link>network properties</fgp-java-network-properties-link> to know
                if a proxy server shall be used when downloading the distribution. A custom proxy server may also be
                used by defining the <fgp-property-link name="httpsProxyHost" /> property (respectively the
                <fgp-property-link name="httpProxyHost" /> property) if the
                <fgp-property-link name="yarnDistributionUrlRoot" /> property uses the <fgp-code>https</fgp-code>
                protocol (resp. uses the <fgp-code>http</fgp-code> protocol).
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
import fgpCode from "../code";
import fgpGradleGuidesLink from "../link/gradle-guides-link";
import fgpGradleTaskOutcomeLink from "../link/gradle-task-outcome-link";
import fgpInfo from "../info";
import fgpLink from "../link/link";
import fgpSectionTitle from "../section-title";
import fgpTask from "./task";
import fgpTaskLink from "../link/task-link";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-install-yarn-task", {
    components: {
        fgpCode,
        fgpGradleGuidesLink,
        fgpGradleTaskOutcomeLink,
        fgpInfo,
        fgpLink,
        fgpSectionTitle,
        fgpTask,
        fgpTaskLink,
        fgpYarnLink
    }
});
</script>
