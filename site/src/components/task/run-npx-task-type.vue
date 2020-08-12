<template>
    <fgp-task name="RunNpx" :type="true">
        <template v-slot:title>Run a custom command with <fgp-code>npx</fgp-code></template>
        <template v-slot:description>
            <blockquote class="blockquote">
                Requires Node.js 8.2.0+ on Unix-like O/S, Node.js 8.5.0+ on Windows O/S
            </blockquote>
            <p>
                The plugin provides the task type
                <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx</fgp-code> that allows creating a
                custom task to run a <fgp-code>npx</fgp-code> command. The <fgp-code>script</fgp-code> property must be
                set with the corresponding command. The task will fail if the <fgp-property-link name="yarnEnabled" />
                property is <fgp-code>true</fgp-code>, to prevent unpredictable behaviors with mixed installation of
                dependencies. Then, choose whether <fgp-nodejs-link /> only is required, or if additional dependencies
                located in the <fgp-code>package.json</fgp-code> file should be installed: make the task either depends
                on <fgp-task-link name="installNode" /> task or on <fgp-task-link name="installFrontend" /> task. The
                code hereafter shows the configuration required to output the version of <fgp-code>npx</fgp-code>:
            </p>

            <fgp-gradle-scripts id="run-npx-example" class="my-3">
                <template v-slot:groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register('npxVersion', RunNpx) {
    dependsOn tasks.named('installNode')
    dependsOn tasks.named('installFrontend')
    script = '--version'
}</fgp-code></pre>
                </template>
                <template v-slot:kotlin>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register&lt;RunNpx&gt;("npxVersion") {
    dependsOn(tasks.named("installNode"))
    dependsOn(tasks.named("installFrontend"))
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

export default Vue.component("fgp-run-npx-task-type", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask,
        fgpTaskLink
    }
});
</script>
