<template>
    <FgpTask
        name="runNode"
        :depending-task-names="['installNode']"
        :inputs="inputs"
        example="gradle runNode &quot;--args=-e console.log(1)&quot;"
    >
        <template #title>Run an instant command with <FgpCode>node</FgpCode> from Gradle command line</template>
        <template #description>
            <p>
                This task allows to execute a <FgpCode>node</FgpCode> command from a Gradle command line. It is
                recommended to register a custom task using <FgpTaskLink name="RunNodeTaskType" /> to define custom
                inputs, outputs, or environment, rather than overriding the default configuration of this task.
            </p>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: 'F',
        binding: TaskPropertyBinding.PROPERTY,
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: TaskPropertyBinding.PROPERTY,
        property: 'nodeInstallDirectory',
    },
    { name: 'args', type: 'S', binding: TaskPropertyBinding.USER, commandLineOptionSupported: true },
];
</script>
