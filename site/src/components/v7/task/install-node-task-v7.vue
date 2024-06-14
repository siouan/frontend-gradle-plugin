<template>
    <FgpTask name="installNode" :inputs="inputs" :outputs="outputs">
        <template #title>Install <FgpNodejsLink /></template>
        <template #nodeExecutableFile>
            <FgpPropertyLink name="nodeInstallDirectory" /><FgpCode>/node.exe</FgpCode> or
            <FgpPropertyLink name="nodeInstallDirectory" /><FgpCode>/bin/node</FgpCode>
            depending on the O/S.
        </template>
        <template #skipConditions>
            property <FgpPropertyLink name="nodeDistributionProvided" /> is <FgpCode>true</FgpCode>.
        </template>
        <template #description>
            <p>
                The task downloads a <FgpNodejsLink /> distribution, verifies its integrity, and installs it in the
                directory pointed by the <FgpPropertyLink name="nodeInstallDirectory" /> property. The URL used to
                download the distribution is resolved using the
                <FgpPropertyLink name="nodeDistributionUrlRoot" /> property and the
                <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> property. Checking the distribution integrity
                consists of downloading a file providing the distribution shasum. This file is expected to be in the
                same remote web directory than the distribution archive. For example, if the distribution is located at
                <FgpCode>https://nodejs.org/dist/vX.Y.Z/node-vX.Y.Z-win-x64.zip</FgpCode>, the plugin attempts to
                download the shasum file located at <FgpCode>https://nodejs.org/dist/vX.Y.Z/SHASUMS256.txt</FgpCode>. By
                default, the plugin relies on the VM
                <FgpJavaNetworkPropertiesLink>network properties</FgpJavaNetworkPropertiesLink>
                to know if a proxy server shall be used when downloading the distribution and the shasum. A custom proxy
                server may also be used by defining <FgpPropertyLink name="httpsProxyHost" /> property (respectively
                <FgpPropertyLink name="httpProxyHost" /> property) if the
                <FgpPropertyLink name="nodeDistributionUrlRoot" /> property uses the <FgpCode>https</FgpCode> protocol
                (resp. uses the <FgpCode>http</FgpCode> protocol).
            </p>
            <p>
                If a <FgpNodejsLink /> distribution is already installed in the system - either as a global installation
                or as an installation performed by another Gradle (sub-)project - and shall be used instead of a
                downloaded distribution, take a look at the <FgpPropertyLink name="nodeDistributionProvided" /> property
                instead: when <FgpCode>true</FgpCode>, this task is ignored if invoked during a Gradle build, and its
                outcome will always be <FgpGradleTaskOutcomeLink outcome="SKIPPED" />.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                    >Gradle incremental build</fgp-gradle-guides-link
                >, and is not executed again unless one of its inputs/outputs changed. Consequently, if the task takes
                part of a Gradle build, its outcome will be <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" />.
            </p>

            <FgpInfo> This task should not be executed directly. Gradle executes it if the build requires it. </FgpInfo>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    { name: 'nodeVersion', type: 'S', binding: 'P', property: 'nodeVersion' },
    {
        name: 'nodeDistributionUrlRoot',
        type: 'S',
        binding: 'P',
        property: 'nodeDistributionUrlRoot',
    },
    {
        name: 'nodeDistributionUrlPathPattern',
        type: 'S',
        binding: 'P',
        property: 'nodeDistributionUrlPathPattern',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: 'P',
        property: 'nodeInstallDirectory',
    },
];
const outputs = [{ name: 'nodeExecutableFile', type: 'RF', binding: 'C' }];
</script>
