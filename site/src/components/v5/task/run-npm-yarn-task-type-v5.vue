<template>
    <FgpTask name="RunNpmYarn" type>
        <template #title>Run a custom command with <FgpCode>npm</FgpCode> or <FgpCode>yarn</FgpCode></template>
        <template #description>
            <p>
                The plugin provides the task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn</FgpCode> that allows
                creating a custom task to run a frontend script. The <FgpCode>script</FgpCode> property must be set
                with the corresponding command. Depending on the <FgpPropertyLink name="yarnEnabled" /> property, the
                task runs either <FgpCode>npm</FgpCode> or <FgpCode>yarn</FgpCode>. Then, choose the appropriate
                task dependency: make the task either depends on <FgpTaskLink name="installNode" /> task or on
                <FgpTaskLink name="installFrontend" /> task. The code hereafter shows the configuration required to
                run end-to-end tests:
            </p>

            <FgpGradleScripts id="run-npm-yarn-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register('yarnVersion', RunNpmYarn) {
    <FgpCodeComment>// dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = '--version'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpmYarn
tasks.register&lt;RunNpmYarn&gt;("yarnVersion") {
    <FgpCodeComment>// dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("--version")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>
