<template>
    <FgpProperty
        name="yarnInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value="project.layout.projectDirectory.dir(&quot;yarn&quot;)"
        :task-names="['installYarn']"
    >
        <p>
            Directory where the downloaded distribution shall be installed, or where a provided distribution is located
            if the
            <FgpPropertyLink name="yarnDistributionProvided" /> property is <FgpCode>true</FgpCode>.
        </p>
    </FgpProperty>
</template>
