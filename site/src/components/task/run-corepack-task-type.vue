<template>
    <fgp-task name="RunCorepack" :type="true">
        <template v-slot:title>Run a custom command with <fgp-code>corepack</fgp-code></template>
        <template v-slot:description>
            <p>
                The plugin provides task type
                <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack</fgp-code> that allows creating
                a custom task to run a <fgp-code>corepack</fgp-code> command. The <fgp-code>script</fgp-code> property must
                be set with the corresponding command. The code hereafter shows the configuration required to output the
                version of <fgp-code>corepack</fgp-code>:
            </p>

            <fgp-gradle-scripts id="run-corepack-example" class="my-3">
                <template v-slot:groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register('corepackVersion', RunCorepack) {
    script = '--version'
}</fgp-code></pre>
                </template>
                <template v-slot:kotlin>
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
import Vue from "vue";
import fgpCode from "../code";
import fgpGradleScripts from "../gradle-scripts";
import fgpTask from "./task";

export default Vue.component("fgp-run-corepack-task-type", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask
    }
});
</script>
