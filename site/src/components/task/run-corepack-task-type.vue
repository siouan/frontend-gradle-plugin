<template>
    <FgpTask name="RunCorepackTaskType" type :inputs="inputs" custom-environment-variables-supported>
        <template #title>Run a custom command with <FgpCode>corepack</FgpCode></template>
        <template #description>
            <p>
                Task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepackTaskType</FgpCode>
                allows to register a custom task executing a <FgpCode>corepack</FgpCode> command. The
                <FgpCode>args</FgpCode> property must be defined with the corresponding arguments, either in the build
                script or on the command line. The example hereafter shows how to register a task in a build script to
                output the version of <FgpCode>corepack</FgpCode>:
            </p>

            <FgpGradleScripts id="run-corepack-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepackTaskType
tasks.register('customCorepackTask', RunCorepackTaskType) {
    dependsOn tasks.named('installCorepack')
    args = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunCorepackTaskType
tasks.register&lt;RunCorepackTaskType&gt;("customCorepackTask") {
    dependsOn(tasks.named("installCorepack"))
    args.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>args</FgpCode> property may be defined or overwritten on the command line: <FgpCode
                >gradle customCorepackTask --args="-v"</FgpCode>
            </p>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [
    {
        name: 'packageJsonDirectory',
        type: 'F',
        binding: TaskPropertyBinding.PROPERTY,
        property: 'packageJsonDirectory',
    },
    {
        name: 'nodeInstallDirectory',
        type: 'F',
        binding: TaskPropertyBinding.PROPERTY,
        property: 'nodeInstallDirectory',
    },
    { name: 'args', type: 'S', binding: TaskPropertyBinding.USER, commandLineOptionSupported: true },
];
</script>
