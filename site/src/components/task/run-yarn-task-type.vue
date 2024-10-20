<template>
    <FgpTask name="RunYarnTaskType" type :inputs="inputs" custom-environment-variables-supported>
        <template #title>Register a task running a custom command with <FgpCode>yarn</FgpCode></template>
        <template #description>
            <p>
                Task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType</FgpCode>
                allows to register a custom task executing a <FgpCode>yarn</FgpCode> command. The
                <FgpCode>args</FgpCode> property must be defined with the corresponding arguments, either in the build
                script or on the command line. The example hereafter shows how to register a task in a build script to
                output the version of <FgpCode>yarn</FgpCode>:
            </p>

            <FgpGradleScripts id="run-yarn-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType
tasks.register('customYarnTask', RunYarnTaskType) {
    dependsOn tasks.named('installPackageManager')
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn tasks.named('installFrontend')</FgpCodeComment>
    args = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunYarnTaskType
tasks.register&lt;RunYarnTaskType&gt;("customYarnTask") {
    dependsOn(tasks.named("installPackageManager")
    <FgpCodeComment>// If the command requires additional dependencies located in the 'package.json' file,
    // replace the previous task dependency with the one hereafter:
    //dependsOn(tasks.named("installFrontend")</FgpCodeComment>
    args.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>args</FgpCode> property may be defined or overwritten on the command line: <FgpCode
                >gradle customYarnTask "--args=-v"</FgpCode>
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
