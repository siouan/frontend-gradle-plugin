<template>
    <FgpTask name="RunNpx" :type="true">
        <template v-slot:title>Run a custom command with <FgpCode>npx</FgpCode></template>
        <template v-slot:description>
            <blockquote class="blockquote">
                Requires Node.js 8.2.0+ on Unix-like O/S, Node.js 8.5.0+ on Windows O/S
            </blockquote>
            <p>
                The plugin provides the task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx</FgpCode> that allows creating a
                custom task to run a <FgpCode>npx</FgpCode> command. The <FgpCode>script</FgpCode> property must be
                set with the corresponding command. The task will fail if the <FgpPropertyLink name="yarnEnabled" />
                property is <FgpCode>true</FgpCode>, to prevent unpredictable behaviors with mixed installation of
                dependencies. Then, choose whether <FgpNodejsLink /> only is required, or if additional dependencies
                located in the <FgpCode>package.json</FgpCode> file should be installed: make the task either depends
                on <FgpTaskLink name="installNode" /> task or on <FgpTaskLink name="installFrontend" /> task. The
                code hereafter shows the configuration required to output the version of <FgpCode>npx</FgpCode>:
            </p>

            <FgpGradleScripts id="run-npx-example" class="my-3">
                <template v-slot:groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register('npxVersion', RunNpx) {
    <FgpCodeComment>// dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = '--version'
}</FgpCode></pre>
                </template>
                <template v-slot:kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpx
tasks.register&lt;RunNpx&gt;("npxVersion") {
    <FgpCodeComment>// dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("--version")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>
