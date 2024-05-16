<template>
    <FgpProperty
        name="yarnDistributionServerUsername"
        type="java.lang.String"
        :required="false"
        :task-names="['installYarn']"
    >
        <p>
            This property may be used to download the distribution with a given identity (BASIC scheme server-side).
            When not <FgpCode>null</FgpCode>, the <FgpPropertyLink name="yarnDistributionServerPassword" /> property is
            also required.
        </p>
        <FgpWarning>
            SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </FgpWarning>
    </FgpProperty>
</template>
