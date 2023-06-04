<template>
    <fgp-property
        name="installScript"
        type="java.lang.String"
        default-value="install"
        example="ci"
        :tasks="['installFrontend']"
    >
        <p>This property may be used to customize the command line to install frontend dependencies.</p>
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpInfo from '@/components/info';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-install-script-property', {
    components: { fgpInfo, fgpProperty }
});
</script>
