<template>
    <FgpTask name="assembleFrontend" :inputs="inputs">
        <template #title>Assemble frontend artifacts</template>
        <template #skipConditions>
            property <FgpPropertyLink name="assembleScript" /> is not <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            This task allows to execute a build script as part of a Gradle build. The build script shall be defined in
            the <FgpCode>package.json</FgpCode> file, and the <FgpPropertyLink name="assembleScript" /> property
            shall be set with the corresponding
            <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode> command. This task depends on
            the <FgpTaskLink name="installFrontend" /> task, and is skipped if the
            <FgpPropertyLink name="assembleScript" /> property is <FgpCode>null</FgpCode>. Apart from direct
            execution, the task is also executed when the Gradle lifecycle
            <FgpGradleDocsLink path="/current/userguide/base_plugin.html#sec:base_tasks"
            >assemble</FgpGradleDocsLink> task is executed.

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">About task execution and up-to-date checks</h5>
                    <p class="card-text">
                        If you execute this task several times in a row, you may notice the
                        <FgpCode>npm</FgpCode>/<FgpCode>pnpm</FgpCode>/<FgpCode>yarn</FgpCode> command is always
                        executed: Gradle does not skip the task based on a previous execution with the
                        <FgpGradleTaskOutcomeLink outcome="SUCCESS" /> outcome. This is the expected behaviour
                        because the task does not declare any input/output Gradle could track, to know the task is
                        already <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" /> (e.g. unlike task
                        <FgpTaskLink name="installNode" />). The task provides the ability to plug the developer's own
                        Javascript build process to Gradle, and nothing more. Every Javascript build process is unique:
                        it depends on the project, the languages involved (e.g. TypeScript, JSX, ECMA script, SASS,
                        SCSS...), the directory layout, the build utilities (Webpack...), etc., chosen by the team.
                        Moreover, some build utilities are already able to build artifacts incrementally. The plugin
                        does not duplicate this logic. If you are about to tweak this task, take a look at these
                        <FgpSiteLink :path="`${$config.public.paths.tasks}#tweaking-tasks`">recommendations</FgpSiteLink>.
                    </p>
                </div>
            </div>
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
    { name: 'script', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.PROPERTY, property: 'assembleScript' },
];
</script>
