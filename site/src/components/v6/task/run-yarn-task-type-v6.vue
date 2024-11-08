<template>
    <FgpTask name="RunYarn" type>
        <template #title>Run a custom command with <FgpCode>yarn</FgpCode></template>
        <template #description>
            <p>
                The plugin provides the task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn</FgpCode> that allows
                creating a custom task to run a frontend script. The <FgpCode>script</FgpCode> property must be set
                with the corresponding command. The <FgpPropertyLink name="yarnEnabled" /> property must be set to
                <FgpCode>true</FgpCode>. Then, choose the appropriate task dependency: make the task either depends
                on <FgpTaskLink name="installNode" /> task or on <FgpTaskLink name="installFrontend" /> task. The
                code hereafter shows the configuration required to run end-to-end tests:
            </p>

            <FgpGradleScripts id="run-yarn-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn
tasks.register('yarnVersion', RunYarn) {
    <FgpCodeComment>// dependsOn 'installNode'
    // dependsOn 'installFrontend'</FgpCodeComment>
    script = '--version'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarn
tasks.register&lt;RunYarn&gt;("yarnVersion") {
    <FgpCodeComment>// dependsOn("installNode")
    // dependsOn("installFrontend")</FgpCodeComment>
    script.set("--version")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>
