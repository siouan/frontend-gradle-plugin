<template>
    <FgpTask name="RunPnpm" type :inputs="inputs">
        <template #title>Run a custom command with <FgpCode>pnpm</FgpCode></template>
        <template #description>
            <p>
                The plugin provides task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm</FgpCode>
                that allows creating a custom task to run a
                <FgpCode>pnpm</FgpCode> command. The <FgpCode>script</FgpCode> property must be set with the
                corresponding command. Then, choose whether additional dependencies located in the
                <FgpCode>package.json</FgpCode> file should be installed: make the task either depends on
                <FgpTaskLink name="installPackageManager" /> task or on <FgpTaskLink name="installFrontend" /> task. The
                code hereafter shows the configuration required to output the version of <FgpCode>pnpm</FgpCode>:
            </p>

            <FgpGradleScripts id="run-pnpm-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm
tasks.register('pnpmVersion', RunPnpm) {
    <FgpCodeComment>// dependsOn tasks.named('installPackageManager')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = '--version'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpm
tasks.register&lt;RunPnpm&gt;("pnpmVersion") {
    <FgpCodeComment>// dependsOn(tasks.named("installPackageManager"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("--version")
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
