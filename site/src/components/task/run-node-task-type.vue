<template>
    <FgpTask name="RunNode" type :inputs="inputs" custom-environment-variables-supported>
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
tasks.register('nodeVersion', RunNode) {
    <FgpCodeComment>// dependsOn tasks.named('installNode')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNode
tasks.register&lt;RunNode&gt;("nodeVersion") {
    <FgpCodeComment>// dependsOn(tasks.named("installNode"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: 'F',
        binding: 'P',
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: 'P',
        property: 'nodeInstallDirectory',
    },
    { name: 'script', type: 'S', binding: 'P', property: 'script' },
];
</script>
