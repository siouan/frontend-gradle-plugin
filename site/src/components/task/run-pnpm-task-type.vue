<template>
    <fgp-task name="RunPnpm" :type="true">
        <template v-slot:title>Run a custom command with <fgp-code>pnpm</fgp-code></template>
        <template v-slot:description>
            <p>
                The plugin provides task type
                <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm</fgp-code> that allows creating
                a custom task to run a <fgp-code>pnpm</fgp-code> command. The <fgp-code>script</fgp-code> property must
                be set with the corresponding command. Then, choose whether additional dependencies located in the
                <fgp-code>package.json</fgp-code> file should be installed: make the task either depends on
                <fgp-task-link name="installPackageManager" /> task or on <fgp-task-link name="installFrontend" /> task.
                The code hereafter shows the configuration required to output the version of <fgp-code>pnpm</fgp-code>:
            </p>

            <fgp-gradle-scripts id="run-pnpm-example" class="my-3">
                <template v-slot:groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm
tasks.register('pnpmVersion', RunPnpm) {
    <fgp-code-comment>// dependsOn tasks.named('installPackageManager')
    // dependsOn tasks.named('installFrontend')</fgp-code-comment>
    script = '--version'
}</fgp-code></pre>
                </template>
                <template v-slot:kotlin>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm
tasks.register&lt;RunPnpm&gt;("pnpmVersion") {
    <fgp-code-comment>// dependsOn(tasks.named("installPackageManager"))
    // dependsOn(tasks.named("installFrontend"))</fgp-code-comment>
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
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-run-pnpm-task-type", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask,
        fgpTaskLink
    }
});
</script>
