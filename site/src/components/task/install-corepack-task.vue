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
               The task overrides the default version of <FgpCorepackLink /> embedded in the <FgpNodejsLink />
               distribution if the <FgpPropertyLink name="corepackVersion" /> property is not <FgpCode>null</FgpCode>.
               To do so, the plugin executes command <FgpCode>npm install -g
               corepack[@&lt;corepackVersion&gt;]</FgpCode>.
               Apart from a specific version number, if this property is set with the <FgpCode>latest</FgpCode> value,
               the plugin will always install the latest version available. Note that this task will not be executed
               again and update Corepack automatically if a newer version is released after this task has run once
               successfully.
           </p>
            <p>
               As the task touches the <FgpNodejsLink /> install directory, please pay attention when using a shared
               <FgpNodejsLink /> distribution (<FgpPropertyLink name="nodeDistributionProvided" /> is <FgpCode
               >true</FgpCode>), since this task will impact other Gradle (sub-)projects. Depending on the project, this
               may be the expected behavior or have a side-effect.
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
    type: 'F',
    binding: 'P',
    property: 'packageJsonDirectory'
}, {
    name: 'nodeInstallDirectory',
    type: 'F',
    binding: 'P',
    property: 'nodeInstallDirectory'
}, {
    name: 'corepackVersion',
    type: 'RF',
    binding: 'P',
    property: 'corepackVersion'
}];
const outputs = [{
    name: 'corepackModuleDirectory',
    type: 'D',
    binding: 'C'
}];
</script>
