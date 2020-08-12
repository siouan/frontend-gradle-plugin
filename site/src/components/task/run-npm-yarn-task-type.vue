<template>
    <fgp-task name="RunNpmYarn" :type="true">
        <template v-slot:title
            >Run a custom command with <fgp-code>npm</fgp-code> or <fgp-code>yarn</fgp-code></template
        >
        <template v-slot:description>
            <p>
                The plugin provides the task type
                <fgp-code>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn</fgp-code> that allows
                creating a custom task to run a frontend script. The <fgp-code>script</fgp-code> property must be set
                with the corresponding command. Depending on the <fgp-property-link name="yarnEnabled" /> property, the
                task runs either <fgp-code>npm</fgp-code> or <fgp-code>yarn</fgp-code>. Then, choose the appropriate
                task dependency: make the task either depends on <fgp-task-link name="installNode" /> task or on
                <fgp-task-link name="installFrontend" /> task. The code hereafter shows the configuration required to
                run end-to-end tests:
            </p>

            <fgp-gradle-scripts id="run-npm-yarn-example" class="my-3">
                <template v-slot:groovy>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register('e2e', RunNpmYarn) {
    // dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')
    script = 'run e2e'
}</fgp-code></pre>
                </template>
                <template v-slot:kotlin>
                    <pre><fgp-code>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register&lt;RunNpmYarn&gt;("e2e") {
    // dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))
    script.set("run e2e")
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

export default Vue.component("fgp-run-npm-yarn-task-type", {
    components: {
        fgpCode,
        fgpGradleScripts,
        fgpTask,
        fgpTaskLink
    }
});
</script>
