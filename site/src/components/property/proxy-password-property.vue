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
        <fgp-warning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";
import fgpWarning from "../warning";

export default Vue.component("fgp-proxy-password-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
