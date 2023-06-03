<template>
    <fgp-task name="cleanFrontend" :inputs="inputs">
        <template #title>Clean frontend artifacts</template>
        <template #executableType>
            type of executable derived from the package manager resolved by task
            <fgp-task-link name="resolvePackageManager" /> in file
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-name.txt</fgp-code>.
        </template>
        <template #skipConditions>
            property <fgp-property-link name="cleanScript" /> is not <fgp-code>null</fgp-code>.
        </template>
        <template #description>
            <p>
                This task does nothing by default, considering frontend artifacts (minimified Javascript, CSS, HTML
                files...) are generated in the <fgp-code>${project.buildDir}</fgp-code> directory. If it is not the
                case, this task may be useful to clean the relevant directory. A clean script shall be defined in the
                <fgp-code>package.json</fgp-code> file, and the <fgp-property-link name="cleanScript" /> property shall
                be set with the corresponding
                <fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> command. This task depends
                on the <fgp-task-link name="installFrontend" /> task, and is skipped if the
                <fgp-property-link name="cleanScript" /> property is <fgp-code>null</fgp-code>. Apart from direct
                execution, the task is also executed when the Gradle lifecycle
                <fgp-gradle-docs-link path="/current/userguide/base_plugin.html#sec:base_tasks">clean</fgp-gradle-docs-link>
                task is executed.
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

export default Vue.component('fgp-clean-frontend-task', {
    components: {
        fgpCode,
        fgpPropertyLink,
        fgpTask,
        fgpTaskLink
    },
    data() {
        return {
            inputs: [
                { name: 'executableType', type: 'ET', binding: 'C' },
                { name: 'packageJsonDirectory', type: 'F', binding: 'P', property: 'packageJsonDirectory' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' },
                { name: 'script', type: 'S', binding: 'P', property: 'cleanScript' }
            ]
        };
    }
});
</script>
