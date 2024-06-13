<template>
    <FgpTask
        name="publishFrontend"
        :depending-task-names="['assembleFrontend']"
        :inputs="inputs"
        custom-environment-variables-supported
    >
        <template #title>Publish frontend artifacts</template>
        <template #skipConditions>
            <FgpPropertyLink name="packageJsonDirectory" /><FgpCode>/package.json</FgpCode> file does not exist, or
            <FgpPropertyLink name="assembleScript" /> is <FgpCode>null</FgpCode>, or
            <FgpPropertyLink name="publishScript" /> is <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            <p>
                This task may be used to integrate a publish script into a Gradle build. The publish script shall be
                defined in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="publishScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                command. Apart from direct execution, the task is also executed when the Gradle <FgpCode
                >publish</FgpCode> task is executed.
            </p>
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
    { name: 'script', type: 'S', binding: 'P', property: 'publishScript' },
];
</script>
