<template>
    <FgpTask
        name="runCorepack"
        :depending-task-names="['installCorepack']"
        :inputs="inputs"
        example="gradle runCorepack &quot;--args=enable yarn&quot;"
    >
        <template #title>Run an instant command with <FgpCode>corepack</FgpCode> from Gradle command line</template>
        <template #description>
            <p>
                This task allows to execute a <FgpCode>corepack</FgpCode> command from a Gradle command line. To define
                custom inputs, outputs, or environment, it is recommended to register a custom task using
                <FgpTaskLink name="RunCorepackTaskType" /> rather than overriding the default configuration of this task.
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
