<template>
    <fgp-property name="yarnDistributionProvided" type="boolean" default-value="false" :tasks="['installYarn']">
        <p>Whether the <fgp-yarn-link /> distribution is already provided, and shall not be downloaded.</p>
        <ol>
            <li>
                When <fgp-code>false</fgp-code>, at least the <fgp-property-link name="yarnVersion" /> property must be
                set.
            </li>
            <li>
                When <fgp-code>true</fgp-code>, the plugin relies on the following locations in this exact order to find
                <fgp-code>yarn</fgp-code> executable:
                <ol>
                    <li>
                        The directory pointed by the <fgp-property-link name="yarnInstallDirectory" /> property, if set.
                    </li>
                    <li>The directory pointed by the <fgp-code>YARN_HOME</fgp-code> environment variable, if set.</li>
                    <li>Any directory in the <fgp-code>PATH</fgp-code> environment variable.</li>
                </ol>
                Other <fgp-code>yarn*</fgp-code> properties should not be used for clarity.
            </li>
        </ol>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-yarn-distribution-provided-property', {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
