<template>
    <FgpTask name="RunNpm" type>
        <template #title>Run a custom command with <FgpCode>npm</FgpCode></template>
        <template #description>
            <p>
                The plugin provides the task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm</FgpCode> that allows
                creating a custom task to run a frontend script. The <FgpCode>script</FgpCode> property must be set
                with the corresponding command. Then, choose the appropriate task dependency: make the task either
                depends on <FgpTaskLink name="installNode" /> task or on <FgpTaskLink name="installFrontend" />
                task. The code hereafter shows the configuration required to run a linter:
            </p>

            <FgpGradleScripts id="run-npm-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm
tasks.register('npmVersion', RunNpm) {
    <FgpCodeComment>// dependsOn 'installNode'
    // dependsOn 'installFrontend'</FgpCodeComment>
    script = '--version'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm
tasks.register&lt;RunNpm&gt;("npmVersion") {
    <FgpCodeComment>// dependsOn("installNode")
    // dependsOn("installFrontend")</FgpCodeComment>
    script.set("--version")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>
