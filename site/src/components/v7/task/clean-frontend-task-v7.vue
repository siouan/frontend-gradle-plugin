<template>
    <FgpTask name="cleanFrontend" :inputs="inputs">
        <template #title>Clean frontend artifacts</template>
        <template #skipConditions>
            property <FgpPropertyLink name="cleanScript" /> is not <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            <p>
                This task does nothing by default, considering frontend artifacts (minimified Javascript, CSS, HTML
                files...) are generated in the
                <FgpCode>${project.buildDir}</FgpCode> directory. If it is not the case, this task may be useful to
                clean the relevant directory. A clean script shall be defined in the
                <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="cleanScript" /> property shall be
                set with the corresponding <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode>
                command. This task depends on the
                <FgpTaskLink name="installFrontend" /> task, and is skipped if the
                <FgpPropertyLink name="cleanScript" /> property is <FgpCode>null</FgpCode>. Apart from direct execution,
                the task is also executed when the Gradle lifecycle
                <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks">clean</FgpGradleDocsLink>
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
    { name: 'script', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.PROPERTY, property: 'cleanScript' },
];
</script>
