<template>
    <FgpProperty
        name="nodeVersion"
        type="java.lang.String"
        :required="false"
        :default-value="null"
        example="14.17.3"
        :task-names="['installNode']"
    >
        <p>
            The version number is used to build the exact URL to download the distribution, as long as the
            <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> property contains the
            <FgpCode>VERSION</FgpCode> token. The property is required when the
            <FgpPropertyLink name="nodeDistributionProvided" /> property is <FgpCode>false</FgpCode>.
        </p>
    </FgpProperty>
</template>
