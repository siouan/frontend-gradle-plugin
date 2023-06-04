<template>
    <fgp-property
        name="httpProxyPassword"
        type="java.lang.String"
        :required="false"
        :tasks="['installNode', 'installYarn']"
    >
        <p>
            Set this property to download distributions through the proxy server with an authenticated identity (BASIC
            scheme server-side). The property is ignored unless the <fgp-property-link name="httpProxyUsername" />
            property is not <fgp-code>null</fgp-code>.
        </p>
        <fgp-warning>
            SECURITY: do not to store a plain text password in the build script. Store it in an environment variable or
            an external user property, and use one or the other as the value of this property.
        </fgp-warning>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';
import fgpWarning from '@/components/warning';

export default Vue.component('fgp-http-proxy-password-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink, fgpWarning }
});
</script>
