<template>
    <FgpTask name="RunPnpmTaskType" type :inputs="inputs" custom-environment-variables-supported>
        <template #title>Register a task running a custom command with <FgpCode>pnpm</FgpCode></template>
        <template #description>
            <p>
                Task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType</FgpCode>
                allows to register a custom task executing a <FgpCode>pnpm</FgpCode> command. The
                <FgpCode>args</FgpCode> property must be defined with the corresponding arguments, either in the build
                script or on the command line. The example hereafter shows how to register a task in a build script to
                output the version of <FgpCode>pnpm</FgpCode>:
            </p>

            <FgpGradleScripts id="run-pnpm-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType
tasks.register('customPnpmTask', RunPnpmTaskType) {
    dependsOn 'installPackageManager'
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn 'installFrontend'</FgpCodeComment>
    args = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunPnpmTaskType
tasks.register&lt;RunPnpmTaskType&gt;("customPnpmTask") {
    dependsOn("installPackageManager")
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn("installFrontend")</FgpCodeComment>
    args.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>args</FgpCode> property may be defined or overwritten on the command line: <FgpCode
                >gradle customPnpmTask "--args=-v"</FgpCode>
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
    { name: 'args', type: TaskPropertyType.STRING, binding: TaskPropertyBinding.USER, commandLineOptionSupported: true },
];
</script>
