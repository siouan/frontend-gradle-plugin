<template>
    <fgp-property name="proxyPort" type="int" default-value="8080" :tasks="['installNode', 'installYarn']">
        Port of the proxy server. This property is ignored unless the <fgp-property-link name="proxyHost" /> property is
        defined.
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-proxy-port-property", {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
