<template>
    <fgp-property
        name="maxDownloadAttempts"
        type="java.lang.Integer"
        :required="true"
        default-value="1"
        :tasks="['installNode']"
    >
        <p>
            Based on this property, the plugin may retry to download the <fgp-nodejs-link /> distribution in case of
            error. A value of <fgp-code>1</fgp-code> indicates the plugin will not retry download (one attempt only).
            This property applies to the distribution file download as well as its shasum file download (see task
            <fgp-task-link name="installNode" />) used to validate the distribution file's integrity. Connectivity
            errors (distribution server reachability, network failures) and
            <fgp-property-link name="retryHttpStatuses">some HTTP errors</fgp-property-link> trigger a retry of the
            download if more attempts are permitted thanks to this property. The retry strategy is based on the
            exponential backoff algorithm to determine the waiting time before the next download attempt:
            <math display="block" class="mt-1 text-monospace">
                <mrow>
                    <mi><fgp-code>waiting time (ms)</fgp-code></mi>
                    <mo>=</mo>
                    <mi class="text-info">min</mi>
                    <mo fence="true">(</mo>
                    <mi><fgp-property-link name="retryInitialIntervalMs" /></mi>
                    <mo class="text-danger">&times;</mo>
                    <msup>
                        <mi class="text-primary"><fgp-property-link name="retryIntervalMultiplier" /></mi>
                        <mrow>
                            <mo fence="true">(</mo>
                            <mi>attempted downloads</mi>
                            <mo class="text-danger">&minus;</mo>
                            <mn>1</mn>
                            <mo fence="true">)</mo>
                        </mrow>
                    </msup>
                    <mo separator="true">,</mo>
                    <mi><fgp-property-link name="retryMaxIntervalMs" /></mi>
                    <mo fence="true">)</mo>
                </mrow>
            </math>
        </p>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpProperty from '@/components/property/property';

export default Vue.component('fgp-max-download-attempts-property', {
    components: { fgpProperty }
});
</script>
