<template>
    <fgp-property
        name="assembleScript"
        type="java.lang.String"
        :required="false"
        example="run assemble"
        :tasks="['assembleFrontend', 'publishFrontend']"
    >
        <p>Script called to build frontend artifacts.</p>
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpInfo from '@/components/info';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-assemble-script-property', {
    components: { fgpInfo, fgpProperty }
});
</script>
