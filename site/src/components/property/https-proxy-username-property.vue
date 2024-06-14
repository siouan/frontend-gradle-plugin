<template>
    <FgpProperty
        name="httpsProxyUsername"
        type="java.lang.String"
        :required="false"
        :task-names="['installNode', 'installYarn']"
    >
        <p>
            This property may be used to download distributions through the proxy server with a given identity (BASIC
            scheme server-side). This property is ignored when no proxy server is used (see the
            <FgpPropertyLink name="httpsProxyHost" /> property). Otherwise, the
            <FgpPropertyLink name="httpsProxyPassword" /> property is also required.
        </p>
        <FgpWarning>
            SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </FgpWarning>
    </FgpProperty>
</template>
