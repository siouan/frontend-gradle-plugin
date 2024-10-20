<template>
    <FgpTask name="RunNpm" type :inputs="inputs" custom-environment-variables-supported>
        <template #title>Run a custom command with <FgpCode>npm</FgpCode></template>
        <template #description>
            <p>
                The plugin provides task type
                <FgpCode>org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm</FgpCode>
                that allows creating a custom task to run a
                <FgpCode>npm</FgpCode> command. The <FgpCode>script</FgpCode> property must be set with the
                corresponding command. Then, choose whether additional dependencies located in the
                <FgpCode>package.json</FgpCode> file should be installed: make the task either depends on
                <FgpTaskLink name="installPackageManager" /> task or on <FgpTaskLink name="installFrontend" /> task. The
                code hereafter shows the configuration required to output the version of <FgpCode>npm</FgpCode>:
            </p>

            <FgpGradleScripts id="run-npm-example">
                <template #groovy>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm
tasks.register('npmVersion', RunNpm) {
    <FgpCodeComment>// dependsOn tasks.named('installPackageManager')
    // dependsOn tasks.named('installFrontend')</FgpCodeComment>
    script = '-v'
}</FgpCode></pre>
                </template>
                <template #kotlin>
                    <pre><FgpCode>import org.siouan.frontendgradleplugin.infrastructure.gradle.RunNpm
tasks.register&lt;RunNpm&gt;("npmVersion") {
    <FgpCodeComment>// dependsOn(tasks.named("installPackageManager"))
    // dependsOn(tasks.named("installFrontend"))</FgpCodeComment>
    script.set("-v")
}</FgpCode></pre>
                </template>
            </FgpGradleScripts>

            <p>
                The <FgpCode>script</FgpCode> property may also be passed directly on the command line: <FgpCode
                >gradle npmVersion --script="-v"</FgpCode>
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
    { name: 'script', type: 'S', binding: TaskPropertyBinding.PROPERTY, property: 'script', commandLineOptionSupported: true },
];
</script>
