<template>
    <fgp-task name="assembleFrontend" :inputs="inputs">
        <template #title>Assemble frontend artifacts</template>
        <template #skipConditions>
            property <fgp-property-link name="assembleScript" /> is not <fgp-code>null</fgp-code>.
        </template>
        <template #description>
            This task allows to execute a build script as part of a Gradle build. The build script shall be defined in
            the <fgp-code>package.json</fgp-code> file, and the <fgp-property-link name="assembleScript" /> property
            shall be set with the corresponding
            <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command. This task depends on
            the <fgp-task-link name="installFrontend" /> task, and is skipped if the
            <fgp-property-link name="assembleScript" /> property is <fgp-code>null</fgp-code>. Apart from direct
            execution, the task is also executed when the Gradle lifecycle
            <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks"
            >assemble</fgp-gradle-docs-link> task is executed.

            <div class="card my-3">
                <div class="card-body">
                    <h5 class="card-title">About
                        <fgp-gradle-docs-link path="/current/userguide/incremental_build.html">incremental build</fgp-gradle-docs-link>
                        and up-to-date checks</h5>
                    <p class="card-text">
                        If you execute this task several times in a row, you may notice the
                        <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command is always
                        executed: Gradle does not skip the task based on a previous execution with the
                        <fgp-gradle-task-outcome-link outcome="SUCCESS" /> outcome. This is the expected behaviour
                        because the task does not declare any input/output Gradle could track, to know the task is
                        already <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" /> (e.g. unlike task
                        <fgp-task-link name="installNode" />). The task provides the ability to plug the developer's own
                        Javascript build process to Gradle, and nothing more. Every Javascript build process is unique:
                        it depends on the project, the languages involved (e.g. TypeScript, JSX, ECMA script, SASS,
                        SCSS...), the directory layout, the build utilities (Webpack...), etc., chosen by the team.
                        Moreover, some build utilities are already able to build artifacts incrementally. The plugin
                        does not duplicate this logic. If you are about to tweak this task, take a look at these
                        <fgp-site-link path="#tweaking-tasks">recommendations</fgp-site-link>.
                    </p>
                </div>
            </div>
        </template>
    </fgp-task>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpGradleTaskOutcomeLink from '@/components/link/gradle-task-outcome-link';
import fgpPropertyLink from '@/components/link/property-link';
import fgpTask from '@/components/task/task';
import fgpTaskLink from '@/components/link/task-link';

export default Vue.component('fgp-assemble-frontend-task', {
    components: {
        fgpCode,
        fgpGradleTaskOutcomeLink,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink
    },
    data() {
        return {
            inputs: [
                { name: 'packageJsonDirectory', type: 'F', binding: 'P', property: 'packageJsonDirectory' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' },
                { name: 'script', type: 'S', binding: 'P', property: 'assembleScript' }
            ]
        };
    }
});
</script>
