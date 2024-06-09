<template>
    <FgpTask name="RunCorepack" type :inputs="inputs">
        <template #title>Run a custom command with <FgpCode>corepack</FgpCode></template>
        <template #description>
            <p>
                The plugin provides task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack</FgpCode>
                that allows creating a custom task to run a
                <FgpCode>corepack</FgpCode> command. The <FgpCode>script</FgpCode> property must be set with the
                corresponding command. The code hereafter shows the configuration required to output the version of
                <FgpCode>corepack</FgpCode>:
            </p>

            <FgpGradleScripts id="run-corepack-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register('corepackVersion', RunCorepack) {
    dependsOn tasks.named('installCorepack')
    script = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register&lt;RunCorepack&gt;("corepackVersion") {
    dependsOn(tasks.named("installCorepack"))
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
