<template>
    <FgpTask
        name="installCorepack"
        :depending-task-names="['installNode']"
        :inputs="inputs"
        :outputs="outputs"
        custom-environment-variables-supported
    >
        <template #title>Install/upgrade Corepack</template>
        <template #corepackModuleDirectory>
            <FgpPropertyLink name="nodeInstallDirectory" /><FgpCode >/node_modules/corepack</FgpCode>.
        </template>
        <template #skipConditions>
            <FgpPropertyLink name="corepackVersion" /> property is <FgpCode>null</FgpCode>.
        </template>
        <template #description>
            <p>
                The purpose of this task is to allow replacing the default version of <FgpCorepackLink /> embedded in
                <FgpNodejsLink />. This may be necessary in case of <FgpCorepackLink /> fails to install the package
                manager used by the project, or to continuously benefit from the latest improvements and bug fixes.
                By default, this task is not executed unless property <FgpPropertyLink name="corepackVersion" /> is
                defined with a non-<FgpCode>null</FgpCode> value. In the latter case, the plugin executes command
                <FgpCode>npm install -g corepack[@&lt;corepackVersion&gt;]</FgpCode>. Apart from a specific version
                number, if this property is set with the <FgpCode>latest</FgpCode> value, the plugin installs the latest
                version available. Note that this task will not be executed again and update Corepack automatically if a
                newer version is released after this task has run once successfully. Since the project may use a
                package manager that is not <FgpNpmLink />, the task disables Corepack strict behavior when running
                <FgpCode>npm</FgpCode> (see environment variable
                <FgpCorepackLink path="#environment-variables" label="COREPACK_ENABLE_STRICT" />).
           </p>
            <p>
               As the task touches the <FgpNodejsLink /> install directory, please pay attention when using a shared
               <FgpNodejsLink /> distribution (<FgpPropertyLink name="nodeDistributionProvided" /> is <FgpCode
               >true</FgpCode>), because this task will impact other projects using <FgpCorepackLink />. This may be
               desirable or not depending on your situation.
           </p>
           <p>
               The task takes advantage of <fgp-gradle-guides-link path="/performance/#incremental_build"
               >Gradle incremental build</fgp-gradle-guides-link>, and is not executed again unless one of its
               inputs/outputs changed. In this case, the task outcome will be
               <FgpGradleTaskOutcomeLink outcome="UP-TO-DATE" />.
           </p>

           <FgpInfo> This task should not be executed directly. Gradle executes it if the build requires it. </FgpInfo>
        </template>
    </FgpTask>
</template>

<script setup lang="ts">
const inputs = [{
    name: 'packageJsonDirectory',
    type: TaskPropertyType.FILE,
    binding: TaskPropertyBinding.PROPERTY,
    property: 'packageJsonDirectory'
}, {
    name: 'nodeInstallDirectory',
    type: TaskPropertyType.FILE,
    binding: TaskPropertyBinding.PROPERTY,
    property: 'nodeInstallDirectory'
}, {
    name: 'corepackVersion',
    type: TaskPropertyType.STRING,
    binding: TaskPropertyBinding.PROPERTY,
    property: 'corepackVersion'
}];
const outputs = [{
    name: 'corepackModuleDirectory',
    type: TaskPropertyType.DIRECTORY',
    binding: TaskPropertyBinding.CUSTOM
}];
</script>
