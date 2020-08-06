<template>
    <fgp-property
        name="proxyPassword"
        type="java.lang.String"
        :required="false"
        :tasks="['installNode', 'installYarn']"
    >
        <p>
            Set this property to download distributions through the proxy server with an authenticated identity (BASIC
            scheme server-side). The property is ignored unless the <fgp-property-link name="proxyUsername" /> property
            is not <fgp-code>null</fgp-code>.
        </p>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-proxy-password-property", {
    components: { fgpProperty, fgpPropertyLink }
});
</script>
