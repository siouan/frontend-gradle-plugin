<template>
    <fgp-task name="installNode" :inputs="inputs" :outputs="outputs">
        <template #title>Install <fgp-nodejs-link /></template>
        <template #nodeExecutableFile>
            <fgp-property-link name="nodeInstallDirectory" /><fgp-code>/node.exe</fgp-code> or
            <fgp-property-link name="nodeInstallDirectory" /><fgp-code>/bin/node</fgp-code> depending on the O/S.
        </template>
        <template #skipConditions>
            property <fgp-property-link name="nodeDistributionProvided" /> is <fgp-code>true</fgp-code>.
        </template>
        <template #description>
            <p>
                The task downloads a <fgp-nodejs-link /> distribution, verifies its integrity, and installs it in the
                directory pointed by the <fgp-property-link name="nodeInstallDirectory" /> property. The URL used to
                download the distribution is resolved using the
                <fgp-property-link name="nodeDistributionUrlRoot" /> property and the
                <fgp-property-link name="nodeDistributionUrlPathPattern" /> property. Checking the distribution
                integrity consists of downloading a file providing the distribution shasum. This file is expected to be
                in the same remote web directory than the distribution archive. For example, if the distribution is
                located at <fgp-code>https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip</fgp-code>, the plugin
                attempts to download the shasum file located at
                <fgp-code>https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt</fgp-code>. By default, the plugin relies on the
                VM <fgp-java-network-properties-link>network properties</fgp-java-network-properties-link> to know if a
                proxy server shall be used when downloading the distribution and the shasum. A custom proxy server may
                also be used by defining <fgp-property-link name="httpsProxyHost" /> property (respectively
                <fgp-property-link name="httpProxyHost" /> property) if the
                <fgp-property-link name="nodeDistributionUrlRoot" /> property uses the <fgp-code>https</fgp-code>
                protocol (resp. uses the <fgp-code>http</fgp-code> protocol). In case of connectivity/HTTP error,
                download of the distribution file and the shasum file may be retried using property
                <fgp-property-link name="maxDownloadAttempts" />.
            </p>
            <p>
                If a <fgp-nodejs-link /> distribution is already installed in the system - either as a global
                installation or as an installation performed by another Gradle (sub-)project - and shall be used instead
                of a downloaded distribution, take a look at the
                <fgp-property-link name="nodeDistributionProvided" /> property instead: when <fgp-code>true</fgp-code>,
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
import Vue from 'vue';
import fgpGradleGuidesLink from '@/components/link/gradle-guides-link';
import fgpGradleTaskOutcomeLink from '@/components/link/gradle-task-outcome-link';
import fgpInfo from '@/components/info';
import fgpNodejsLink from '@/components/link/nodejs-link';
import fgpPropertyLink from '@/components/link/property-link';
import fgpTask from '@/components/task/task';

export default Vue.component('fgp-install-node-task', {
    components: { fgpGradleGuidesLink, fgpGradleTaskOutcomeLink, fgpInfo, fgpNodejsLink, fgpPropertyLink, fgpTask },
    data() {
        return {
            inputs: [
                { name: 'nodeVersion', type: 'S', binding: 'P', property: 'nodeVersion' },
                { name: 'nodeDistributionUrlRoot', type: 'S', binding: 'P', property: 'nodeDistributionUrlRoot' },
                { name: 'nodeDistributionUrlPathPattern', type: 'S', binding: 'P', property: 'nodeDistributionUrlPathPattern' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' }
            ],
            outputs: [
                { name: 'nodeExecutableFile', type: 'RF', binding: 'C' }
            ]
        };
    }
});
</script>
