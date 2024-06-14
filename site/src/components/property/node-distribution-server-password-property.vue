<template>
    <FgpProperty
        name="nodeDistributionServerPassword"
        type="java.lang.String"
        :required="false"
        :task-names="['installNode']"
    >
        <p>
            Set this property to authenticate the distribution download (BASIC scheme server-side). The property is
            ignored if the
            <FgpPropertyLink name="nodeDistributionServerUsername" /> property is <FgpCode>null</FgpCode>.
        </p>
        <FgpWarning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </FgpWarning>
    </FgpProperty>
</template>
