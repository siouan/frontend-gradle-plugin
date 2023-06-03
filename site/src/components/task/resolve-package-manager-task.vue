<template>
    <fgp-task name="resolvePackageManager" :inputs="inputs" :outputs="outputs">
        <template #title>Resolve package manager</template>
        <template #metadataFile>
            <fgp-property-link name="packageJsonDirectory" /><fgp-code>/package.json</fgp-code>
        </template>
        <template #packageManagerNameFile>
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-name.txt</fgp-code>
        </template>
        <template #packageManagerExecutablePathFile>
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-executable-path.txt</fgp-code>
        </template>
        <template #skipConditions>
            file <fgp-property-link name="packageJsonDirectory" /><fgp-code>/package.json</fgp-code> does not exist.
        </template>
        <template #description>
            <p>
                The task identifies the package manager applicable to the project by parsing the
                <fgp-nodejs-link path="/api/packages.html#packagemanager" label="packageManager" /> property located in
                the <fgp-code>package.json</fgp-code> file (pointed by the
                <fgp-property-link name="packageJsonDirectory" /> property). For example, if the
                <fgp-code>package.json</fgp-code> file contains <fgp-code>"packageManager":&nbsp;"npm@9.6.6"</fgp-code>,
                the task resolves <fgp-code>npm</fgp-code> as the package manager.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                >Gradle incremental build</fgp-gradle-guides-link>, and is not executed again unless one of its
                inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be
                <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />. The task is skipped if the
                <fgp-code>package.json</fgp-code> file does not exist in the project, and its outcome will be
                <fgp-gradle-task-outcome-link outcome="SKIPPED" />.
            </p>
            <fgp-info>
                This task should not be executed directly. Gradle executes it if the build requires it.
            </fgp-info>
        </template>
    </fgp-task>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpInfo from '@/components/info';
import fgpTask from '@/components/task/task';

export default Vue.component('fgp-resolve-package-manager-task', {
    components: { fgpCode, fgpInfo, fgpTask },
    data() {
        return {
            inputs: [
                { name: 'metadataFile', type: 'RF', binding: 'C' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' }
            ],
            outputs: [
                { name: 'packageManagerNameFile', type: 'RF', binding: 'C' },
                { name: 'packageManagerExecutablePathFile', type: 'RF', binding: 'C' }
            ]
        };
    }
});
</script>
