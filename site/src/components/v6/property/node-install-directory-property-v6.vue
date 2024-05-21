<template>
    <FgpProperty
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value='file("${projectDir}/node")'
        :task-names="['installNode']"
    >
        <p>
            Directory where the downloaded distribution shall be installed, or where a provided distribution is located
            if the <FgpPropertyLink name="nodeDistributionProvided" /> property is <FgpCode>true</FgpCode>.
        </p>
    </FgpProperty>
</template>
