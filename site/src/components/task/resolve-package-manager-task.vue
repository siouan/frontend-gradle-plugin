<template>
    <fgp-task name="resolvePackageManager" :depending-tasks="['installNode']" :inputs="inputs" :outputs="outputs" cacheable>
        <template #title>Resolve package manager</template>
        <template #packageJsonFile>
            <fgp-property-link name="packageJsonDirectory" /><fgp-code>/package.json</fgp-code>
        </template>
        <template #packageManagerSpecificationFile>
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-specification.txt</fgp-code>
        </template>
        <template #packageManagerExecutablePathFile>
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-executable-path.txt</fgp-code>
        </template>
        <template #skipConditions>
            <fgp-property-link name="packageJsonDirectory" /><fgp-code>/package.json</fgp-code> file does not exist.
        </template>
        <template #description>
            <p>
                The task identifies the name and the version of the package manager applicable to the project by parsing
                the <fgp-nodejs-link path="/api/packages.html#packagemanager" label="packageManager" /> property located
                in the <fgp-code>package.json</fgp-code> file (pointed by the
                <fgp-property-link name="packageJsonDirectory" /> property). For example, if the
                <fgp-code>package.json</fgp-code> file contains <fgp-code>"packageManager":&nbsp;"npm@9.6.7"</fgp-code>,
                the task resolves <fgp-code>npm</fgp-code> as the name of the package manager, and
                <fgp-code>9.6.7</fgp-code> as its version.
            </p>
            <p>
                The task takes advantage of
                <fgp-gradle-guides-link path="/performance/#incremental_build"
                >Gradle incremental build</fgp-gradle-guides-link>, and is not executed again unless one of its
                inputs/outputs changed. Consequently, if the task takes part of a Gradle build, its outcome will be
                <fgp-gradle-task-outcome-link outcome="UP-TO-DATE" />.
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
                { name: 'packageJsonFile', type: 'RF', binding: 'C' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' }
            ],
            outputs: [
                { name: 'packageManagerSpecificationFile', type: 'RF', binding: 'C' },
                { name: 'packageManagerExecutablePathFile', type: 'RF', binding: 'C' }
            ]
        };
    }
});
</script>
