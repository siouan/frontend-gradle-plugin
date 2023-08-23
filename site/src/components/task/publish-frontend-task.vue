<template>
    <fgp-task name="publishFrontend" :depending-tasks="['assembleFrontend']" :inputs="inputs">
        <template #title>Publish frontend artifacts</template>
        <template #skipConditions>
            task <fgp-task-link name="installFrontend" /> was skipped or property
            <fgp-property-link name="assembleScript" /> is <fgp-code>null</fgp-code> or property
            <fgp-property-link name="publishScript" /> is <fgp-code>null</fgp-code>.
        </template>
        <template #description>
            <p>
                This task may be used to integrate a publish script into a Gradle build. The publish script shall be
                defined in the <fgp-code>package.json</fgp-code> file, and the
                <fgp-property-link name="publishScript" /> property shall be set with the corresponding
                <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command. This task depends
                on the <fgp-task-link name="assembleFrontend" /> task, and is skipped either if the
                <fgp-property-link name="assembleScript" /> property is <fgp-code>null</fgp-code>, or if the
                <fgp-property-link name="publishScript" /> property is <fgp-code>null</fgp-code>. Apart from direct
                execution, the task is also executed when the Gradle <fgp-code>publish</fgp-code> task is executed.
            </p>
        </template>
    </fgp-task>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpPropertyLink from '@/components/link/property-link';
import fgpTask from '@/components/task/task';
import fgpTaskLink from '@/components/link/task-link';

export default Vue.component('fgp-publish-frontend-task', {
    components: {
        fgpCode,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink
    },
    data() {
        return {
            inputs: [
                { name: 'packageJsonDirectory', type: 'F', binding: 'P', property: 'packageJsonDirectory' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' },
                { name: 'script', type: 'S', binding: 'P', property: 'publishScript' }
            ]
        };
    }
});
</script>
