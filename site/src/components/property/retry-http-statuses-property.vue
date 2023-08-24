<template>
    <fgp-property
        name="retryHttpStatuses"
        type="java.util.Set<java.lang.Integer>"
        :required="true"
        default-value="[408, 429, 500, 502, 503, 504]"
        :tasks="['installNode']"
    >
        <p>
            This property identifies HTTP error responses that may trigger a new download attempt based on their status
            code.
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-retry-http-statuses-property', {
    components: { fgpProperty }
});
</script>
