<template>
    <fgp-task name="installPackageManager" :inputs="inputs" :outputs="outputs">
        <template #title>Install package manager</template>
        <template #packageManagerSpecificationFile>
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-specification.txt</fgp-code>
        </template>
        <template #packageManagerExecutableFile>
            <fgp-property-link name="nodeInstallDirectory" /><fgp-code>/[npm|pnpm|yarn].cmd</fgp-code> or
            <fgp-property-link name="nodeInstallDirectory" /><fgp-code>/bin/[npm|pnpm|yarn]</fgp-code> depending on the O/S.
        </template>
        <template #skipConditions>
            file <fgp-property-link name="cacheDirectory" /><fgp-code
            >/resolvePackageManager/package-manager-executable-path.txt</fgp-code> does not exist.
        </template>
        <template #description>
            <p>
                The task installs the package manager resolved with task <fgp-task-link name="resolvePackageManager" />,
                by executing command <fgp-code>corepack enable &lt;package-manager&gt;</fgp-code>.
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
import fgpTaskLink from '@/components/link/task-link';

export default Vue.component('fgp-install-package-manager-task', {
    components: { fgpCode, fgpInfo, fgpTask, fgpTaskLink },
    data() {
        return {
            inputs: [
                { name: 'packageManagerSpecificationFile', type: 'RF', binding: 'C' },
            ],
            outputs: [
                { name: 'packageManagerExecutableFile', type: 'RF', binding: 'C' }
            ]
        };
    }
});
</script>
