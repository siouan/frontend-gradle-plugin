<template>
    <FgpProperty
        name="httpsProxyHost"
        type="java.lang.String"
        :required="false"
        example="127.0.0.1"
        :task-names="['installNode', 'installYarn']"
    >
        <p>
            IP address or domain name of a HTTP proxy server to use when downloading distributions with a secure HTTP
            protocol (e.g. the
            <FgpPropertyLink name="nodeDistributionUrlRoot" /> property and/or the
            <FgpPropertyLink name="yarnDistributionUrlRoot" /> property use the <FgpCode>https</FgpCode> protocol). The
            <FgpSiteLink :path="`${$config.public.paths.configuration}#proxy-resolution-process`">proxy resolution process</FgpSiteLink>
            hereafter explains how this setting apply.
        </p>
    </FgpProperty>
</template>
