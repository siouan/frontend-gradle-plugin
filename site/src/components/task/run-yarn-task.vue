<template>
    <FgpTask
        name="runYarn"
        :depending-task-names="['installPackageManager']"
        :inputs="inputs"
        example="gradle runYarn &quot;--args=add jest -D&quot;"
    >
        <template #title>Run an instant command with <FgpCode>yarn</FgpCode> from Gradle command line</template>
        <template #description>
            <p>
                This task allows to execute a <FgpCode>yarn</FgpCode> command from a Gradle command line. To define
                custom inputs, outputs, or environment, it is recommended to register a custom task using
                <FgpTaskLink name="RunYarnTaskType" /> rather than overriding the default configuration of this task.
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
    { name: 'args', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.USER, commandLineOptionSupported: true },
];
</script>
