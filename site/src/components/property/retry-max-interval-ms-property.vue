<template>
    <fgp-property
        name="retryMaxIntervalMs"
        type="java.lang.Integer"
        :required="true"
        default-value="30000"
        :tasks="['installNode']"
    >
        <p>
            Maximum interval (ms) between each download attempts (see property
            <fgp-property-link name="maxDownloadAttempts" />).
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-retry-max-interval-ms-property', {
    components: { fgpProperty }
});
</script>
