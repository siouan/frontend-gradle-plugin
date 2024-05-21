<template>
    <FgpTask name="publishFrontend" :inputs="inputs">
        <template #title>Publish frontend artifacts</template>
        <template #skipConditions>
            property <FgpPropertyLink name="assembleScript" /> is <FgpCode>null</FgpCode> or property
            <FgpPropertyLink name="publishScript" /> is <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            <p>
                This task may be used to integrate a publish script into a Gradle build. The publish script shall be
                defined in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="publishScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                command. This task depends on the
                <FgpTaskLink name="assembleFrontend" /> task, and is skipped either if the
                <FgpPropertyLink name="assembleScript" /> property is <FgpCode>null</FgpCode>, or if the
                <FgpPropertyLink name="publishScript" /> property is <FgpCode>null</FgpCode>. Apart from direct
                execution, the task is also executed when the Gradle <FgpCode>publish</FgpCode> task is executed.
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
