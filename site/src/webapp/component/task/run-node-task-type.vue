<template>
    <fgp-task name="RunNode" :type="true">
        <template v-slot:title>Run a custom command with <fgp-code>node</fgp-code></template>
        <template v-slot:description>
            The plugin provides the task type
            <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode</fgp-code> that allows creating a
            custom task to run a JS script. The <fgp-code>script</fgp-code> property must be set with the corresponding
            command. Then, choose whether <fgp-nodejs-link /> only is required, or if additional dependencies located in
            the <fgp-code>package.json</fgp-code> file should be installed: make the task either depends on
            <fgp-task-link name="installNode" /> task or on <fgp-task-link name="installFrontend" /> task. The code
            hereafter shows the configuration required to run a JS script:

            <fgp-gradle-scripts id="run-node-example" class="mt-3">
                <template v-slot:groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register('myScript', RunNode) {
    // dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')
    script = 'my-script.js'
}</fgp-code></pre>
                </template>
                <template v-slot:kotlin>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register&lt;RunNode&gt;("myScript") {
    // dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))
    script.set("my-script.js")
}</fgp-code></pre>
                </template>
            </fgp-gradle-scripts>
        </template>
    </fgp-task>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpGradleScripts from "../layout/gradle-scripts";
import fgpTask from "../layout/task";
import fgpTaskLink from "../link/task-link";

export default Vue.component("fgp-run-node-task-type", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask,
        fgpTaskLink
    }
});
</script>
