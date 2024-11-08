<template>
    <FgpTask name="RunCorepack" type :inputs="inputs" custom-environment-variables-supported>
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
    dependsOn 'installCorepack'
    script = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepack
tasks.register&lt;RunCorepack&gt;("corepackVersion") {
    dependsOn("installCorepack")
    script.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>script</FgpCode> property may also be passed directly on the command line: <FgpCode
                >gradle corepackVersion --script="-v"</FgpCode>
            </p>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: TaskPropertyType.FILE,
        binding: TaskPropertyBinding.PROPERTY,
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: TaskPropertyType.FILE,
        binding: TaskPropertyBinding.PROPERTY,
        property: 'nodeInstallDirectory',
    },
    { name: 'script', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.PROPERTY, property: 'script', commandLineOptionSupported: true },
];
</script>
