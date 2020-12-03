<template>
    <fgp-property
        name="httpProxyUsername"
        type="java.lang.String"
        :required="false"
        :tasks="['installNode', 'installYarn']"
    >
        <p>
            This property may be used to download distributions through the proxy server with a given identity (BASIC
            scheme server-side). This property is ignored when no proxy server is used (see the
            <fgp-property-link name="httpProxyHost" /> property). Otherwise, the
            <fgp-property-link name="httpProxyPassword" /> property is also required.
        </p>
        <fgp-warning>
            SECURITY: do not to store a plain text username in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";
import fgpWarning from "../warning";

export default Vue.component("fgp-http-proxy-username-property", {
    components: { fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
