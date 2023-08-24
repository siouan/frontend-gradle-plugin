<template>
    <fgp-property
        name="retryInitialIntervalMs"
        type="java.lang.Integer"
        :required="true"
        default-value="1000"
        :tasks="['installNode']"
    >
        <p>
            Interval (ms) between the first and the second download attempt, if permitted with the
            <fgp-property-link name="maxDownloadAttempts" /> property.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-retry-initial-interval-ms-property', {
    components: { fgpProperty }
});
</script>
