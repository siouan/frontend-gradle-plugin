<template>
    <FgpTask
        name="installPackageManager"
        :depending-task-names="['resolvePackageManager']"
        :inputs="inputs"
        :outputs="outputs"
    >
        <template #title>Install package manager</template>
        <template #packageManagerSpecificationFile>
            <FgpPropertyLink name="cacheDirectory" /><FgpCode
                >/resolvePackageManager/package-manager-specification.txt</FgpCode
            >
        </template>
        <template #packageManagerExecutableFile>
            the package manager executable, whose path is provided by file
            <FgpPropertyLink name="cacheDirectory" /><FgpCode
                >/resolvePackageManager/package-manager-executable-path.txt</FgpCode
            >
            (generally <FgpPropertyLink name="nodeInstallDirectory" /><FgpCode>/[npm|pnpm|yarn].cmd</FgpCode> or
            <FgpPropertyLink name="nodeInstallDirectory" /><FgpCode>/bin/[npm|pnpm|yarn]</FgpCode>
            depending on the O/S).
        </template>
        <template #skipConditions>
            <FgpPropertyLink name="packageJsonDirectory" /><FgpCode>/package.json</FgpCode>
            file does not exist.
        </template>
        <template #description>
            <p>
                The task installs the package manager resolved with task
                <FgpTaskLink name="resolvePackageManager" />, by executing command
                <FgpCode>corepack enable &lt;package-manager&gt;</FgpCode>.
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
    {
        name: 'packageJsonDirectory',
        type: 'F',
        binding: 'P',
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: 'P',
        property: 'nodeInstallDirectory',
    },
    { name: 'packageManagerSpecificationFile', type: 'RF', binding: 'C' }
];
const outputs = [{ name: 'packageManagerExecutableFile', type: 'RF', binding: 'C' }];
</script>
