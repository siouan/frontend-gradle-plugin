<template>
    <FgpProperty
        name="nodeDistributionUrlRoot"
        type="java.lang.String"
        default-value="https://nodejs.org/dist/"
        :task-names="['installNode']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending the value of the
            <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> property to its value. By default, the plugin
            attempts to download the distribution from the <FgpNodejsLink /> website.
        </p>
    </FgpProperty>
</template>
