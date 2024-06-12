<template>
    <FgpProperty name="httpProxyPort" type="int" default-value="443" :task-names="['installNode', 'installYarn']">
        <p>
            Port of the proxy server used for secure HTTP requests. This property is ignored unless the
            <FgpPropertyLink name="httpProxyHost" /> property is defined.
        </p>
    </FgpProperty>
</template>
