<template>
    <fgp-property
        name="retryIntervalMultiplier"
        type="java.lang.Double"
        :required="true"
        default-value="2.0"
        :tasks="['installNode']"
    >
        <p>
            Multiplier used to compute exponential intervals between download attempts.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-retry-interval-multiplier-property', {
    components: { fgpProperty }
});
</script>
