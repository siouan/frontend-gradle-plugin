<template>
    <FgpProperty
        name="yarnDistributionServerPassword"
        type="java.lang.String"
        :required="false"
        :task-names="['installYarn']"
    >
        <p>
            Set this property to authenticate the distribution download (BASIC scheme server-side). The property is
            ignored unless the
            <FgpPropertyLink name="yarnDistributionServerUsername" /> property is defined.
        </p>
        <FgpWarning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </FgpWarning>
    </FgpProperty>
</template>
