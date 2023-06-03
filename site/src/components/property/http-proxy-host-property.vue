<template>
    <fgp-property
        name="httpProxyHost"
        type="java.lang.String"
        :required="false"
        example="127.0.0.1"
        :tasks="['installNode', 'installYarn']"
    >
        <p>
            IP address or domain name of a HTTP proxy server to use when downloading distributions with a non-secure
            HTTP protocol (e.g. the <fgp-property-link name="nodeDistributionUrlRoot" /> property and/or the
            <fgp-property-link name="yarnDistributionUrlRoot" /> property use the <fgp-code>http</fgp-code> protocol).
            The <fgp-site-link path="#proxy-resolution-process">proxy resolution process</fgp-site-link> hereafter
            explains how this setting apply.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-http-proxy-host-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
