<template>
    <FgpProperty
        name="httpProxyPassword"
        type="java.lang.String"
        :required="false"
        :task-names="['installNode', 'installYarn']"
    >
        <p>
            Set this property to download distributions through the proxy server with an authenticated identity (BASIC
            scheme server-side). The property is ignored unless the
            <FgpPropertyLink name="httpProxyUsername" /> property is not <FgpCode>null</FgpCode>.
        </p>
        <FgpWarning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </FgpWarning>
    </FgpProperty>
</template>
