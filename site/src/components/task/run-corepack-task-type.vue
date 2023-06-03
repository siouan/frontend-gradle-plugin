<template>
    <fgp-task name="RunCorepack" :type="true" :inputs="inputs">
        <template #title>Run a custom command with <fgp-code>corepack</fgp-code></template>
        <template #executableType>
            type of executable derived from the package manager resolved by task
            <fgp-task-link name="resolvePackageManager" /> in file
            <fgp-property-link name="cacheDirectory" /><fgp-code>/resolvePackageManager/package-manager-name.txt</fgp-code>.
        </template>
        <template #description>
            <p>
                The plugin provides task type
                <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack</fgp-code> that allows creating
                a custom task to run a <fgp-code>corepack</fgp-code> command. The <fgp-code>script</fgp-code> property must
                be set with the corresponding command. The code hereafter shows the configuration required to output the
                version of <fgp-code>corepack</fgp-code>:
            </p>

            <fgp-gradle-scripts id="run-corepack-example" class="my-3">
                <template #groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register('corepackVersion', RunCorepack) {
    script = '--version'
}</fgp-code></pre>
                </template>
                <template #kotlin>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register&lt;RunCorepack&gt;("corepackVersion") {
    script.set("--version")
}</fgp-code></pre>
                </template>
            </fgp-gradle-scripts>
        </template>
    </fgp-task>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpGradleScripts from '@/components/gradle-scripts';
import fgpTask from '@/components/task/task';

export default Vue.component('fgp-run-corepack-task-type', {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask
    },
    data() {
        return {
            inputs: [
                { name: 'executableType', type: 'ET', binding: 'C' },
                { name: 'packageJsonDirectory', type: 'F', binding: 'P', property: 'packageJsonDirectory' },
                { name: 'nodeInstallDirectory', type: 'F', binding: 'P', property: 'nodeInstallDirectory' },
                { name: 'script', type: 'S', binding: 'P', property: 'script' }
            ]
        };
    }
});
</script>
