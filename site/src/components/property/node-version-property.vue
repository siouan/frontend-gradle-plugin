<template>
    <FgpProperty
        name="nodeVersion"
        type="java.lang.String"
        :required="false"
        :default-value="null"
        example="22.11.0"
        :task-names="['installNode']"
    >
        <p>
            The version number is used to build the exact URL to download the distribution. Though it is not required,
            it must be defined when
            <FgpPropertyLink name="nodeDistributionProvided" /> property is <FgpCode>false</FgpCode> and
            <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> property contains the
            <FgpCode>VERSION</FgpCode> token.
        </p>
    </FgpProperty>
</template>
