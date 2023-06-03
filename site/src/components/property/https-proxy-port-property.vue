<template>
    <fgp-property name="httpsProxyPort" type="int" default-value="443" :tasks="['installNode', 'installYarn']">
        <p>
            Port of the proxy server used for secure HTTP requests. This property is ignored unless the
            <fgp-property-link name="httpsProxyHost" /> property is defined.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-https-proxy-port-property', {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
