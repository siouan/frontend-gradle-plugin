<template>
    <FgpTask name="RunNodeTaskType" type :inputs="inputs" custom-environment-variables-supported>
        <template #title>Register a task running a custom command with <FgpCode>node</FgpCode></template>
        <template #description>
            <p>
                Task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType</FgpCode>
                allows to register a custom task executing a <FgpCode>node</FgpCode> command. The
                <FgpCode>args</FgpCode> property must be defined with the corresponding arguments, either in the build
                script or on the command line. The example hereafter shows how to register a task in a build script to
                output the version of <FgpCode>node</FgpCode>:
            </p>

            <FgpGradleScripts id="run-node-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType
tasks.register('customNodeTask', RunNodeTaskType) {
    dependsOn 'installNode'
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn 'installFrontend'</FgpCodeComment>
    args = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNodeTaskType
tasks.register&lt;RunNodeTaskType&gt;("customNodeTask") {
    dependsOn("installNode")
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn("installFrontend")</FgpCodeComment>
    args.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>args</FgpCode> property may be defined or overwritten on the command line: <FgpCode
                >gradle customNodeTask "--args=-v"</FgpCode>
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
