<template>
    <FgpProperty
        name="yarnVersion"
        type="java.lang.String"
        :required="false"
        example="3.5.1"
        :task-names="['installYarn']"
    >
        <p>
            The version number is used to build the exact URL to download the distribution, as long as the
            <FgpPropertyLink name="yarnDistributionUrlPathPattern" /> property contains the
            <FgpCode>VERSION</FgpCode> token. The property is required when the
            <FgpPropertyLink name="yarnEnabled" /> property is <FgpCode>true</FgpCode> and the
            <FgpPropertyLink name="yarnDistributionProvided" /> property is <FgpCode>false</FgpCode>.
        </p>
    </FgpProperty>
</template>
