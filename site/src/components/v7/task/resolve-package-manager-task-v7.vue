<template>
    <FgpTask
        name="resolvePackageManager"
        :inputs="inputs"
        :outputs="outputs"
        cacheable
    >
        <template #title>Resolve package manager</template>
        <template #packageJsonFile>
            <FgpPropertyLink name="packageJsonDirectory" /><FgpCode>/package.json</FgpCode>
        </template>
        <template #packageManagerSpecificationFile>
            <FgpPropertyLink name="cacheDirectory" /><FgpCode
                >/resolvePackageManager/package-manager-specification.txt</FgpCode
            >
        </template>
        <template #packageManagerExecutablePathFile>
            <FgpPropertyLink name="cacheDirectory" /><FgpCode
                >/resolvePackageManager/package-manager-executable-path.txt</FgpCode
            >
        </template>
        <template #skipConditions>
            <FgpPropertyLink name="packageJsonDirectory" /><FgpCode>/package.json</FgpCode>
            file does not exist.
        </template>
        <template #description>
            <p>
                The task identifies the name and the version of the package manager applicable to the project by parsing
                the
                <FgpNodejsLink path="/api/packages.html#packagemanager" label="packageManager" />
                property located in the <FgpCode>package.json</FgpCode> file (pointed by the
                <FgpPropertyLink name="packageJsonDirectory" /> property). For example, if the
                <FgpCode>package.json</FgpCode> file contains <FgpCode>"packageManager":&nbsp;"npm@10.9.0"</FgpCode>, the
                task resolves <FgpCode>npm</FgpCode> as the name of the package manager, and <FgpCode>10.9.0</FgpCode> as
                its version.
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
    { name: 'packageJsonFile', type: TaskPropertyType.REGULAR_FILE, binding: TaskPropertyBinding.CUSTOM },
    {
        name: 'nodeInstallDirectory',
        type: TaskPropertyType.FILE,
        binding: TaskPropertyBinding.PROPERTY,
        property: 'nodeInstallDirectory',
    },
];
const outputs = [
    { name: 'packageManagerSpecificationFile', type: TaskPropertyType.REGULAR_FILE, binding: TaskPropertyBinding.CUSTOM },
    { name: 'packageManagerExecutablePathFile', type: TaskPropertyType.REGULAR_FILE, binding: TaskPropertyBinding.CUSTOM },
];
</script>
