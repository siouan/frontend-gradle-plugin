<template>
    <fgp-property
        name="nodeInstallDirectory"
        type="java.io.File"
        :required="false"
        default-value="null"
        :tasks="['installNode', 'resolvePackageManager', 'installPackageManager', 'installFrontend', 'cleanFrontend', 'assembleFrontend', 'checkFrontend', 'publishFrontend']"
    >
        <p>
            When property <fgp-property-link name="nodeDistributionProvided" /> is <fgp-code>false</fgp-code>, the
            downloaded distribution is installed in the directory pointed by this property (defaults to
            <fgp-code>file("${projectDir}/node")</fgp-code>). When property
            <fgp-property-link name="nodeDistributionProvided" /> is <fgp-code>true</fgp-code>, this property may be
            used to point the directory where the distribution is already installed. If <fgp-code>null</fgp-code>, the
            plugin tries to find the installation directory with the <fgp-code>FGP_NODEJS_HOME</fgp-code> environment
            variable.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-node-install-directory-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
