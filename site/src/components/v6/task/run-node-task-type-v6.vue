<template>
    <FgpTask name="RunNode" type>
        <template #title>Run a custom command with <FgpCode>node</FgpCode></template>
        <template #description>
            <p>
                The plugin provides task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode</FgpCode>
                that allows creating a custom task to run a JS script. The
                <FgpCode>script</FgpCode> property must be set with the corresponding command. Then, choose whether
                <FgpNodejsLink /> only is required, or if additional dependencies located in the
                <FgpCode>package.json</FgpCode> file should be installed: make the task either depends on
                <FgpTaskLink name="installNode" /> task or on <FgpTaskLink name="installFrontend" /> task. The code
                hereafter shows the configuration required to run a JS script:
            </p>

            <FgpGradleScripts id="run-node-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register('myScript', RunNode) {
    <FgpCodeComment>// dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = 'my-script.js'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register&lt;RunNode&gt;("myScript") {
    <FgpCodeComment>// dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("my-script.js")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>
