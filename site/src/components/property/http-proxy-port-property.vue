<template>
    <fgp-property name="httpProxyPort" type="int" default-value="443" :tasks="['installNode', 'installYarn']">
        <p>
            Port of the proxy server used for secure HTTP requests. This property is ignored unless the
            <fgp-property-link name="httpProxyHost" /> property is defined.
        </p>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-http-proxy-port-property", {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
