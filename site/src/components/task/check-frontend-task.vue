<template>
    <FgpTask
        name="checkFrontend"
        :depending-task-names="['installFrontend']"
        :inputs="inputs"
        custom-environment-variables-supported
    >
        <template #title>Check frontend application</template>
        <template #skipConditions>
            <FgpPropertyLink name="packageJsonDirectory" /><FgpCode>/package.json</FgpCode> file does not exist, or
            <FgpPropertyLink name="checkScript" /> is <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            <p>
                This task may be used to integrate a check script into a Gradle build. The check script shall be defined
                in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="checkScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                command. A typical check script may lint source files, execute tests, and/or perform additional analysis
                actions. Apart from direct execution, the task is also executed when the Gradle lifecycle
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">check</FgpGradleDocsLink>
                task is executed.
            </p>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: TaskPropertyType.FILE,
        binding: TaskPropertyBinding.PROPERTY,
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: TaskPropertyType.FILE,
        binding: TaskPropertyBinding.PROPERTY,
        property: 'nodeInstallDirectory',
    },
    { name: 'script', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.PROPERTY, property: 'checkScript' },
];
</script>
