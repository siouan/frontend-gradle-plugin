<template>
    <fgp-property name="nodeDistributionProvided" type="boolean" default-value="false" :tasks="['installNode']">
        Whether the <fgp-nodejs-link /> distribution is already provided, and shall not be downloaded.
        <ol>
            <li>
                When <fgp-code>false</fgp-code>, at least the <fgp-property-link name="nodeVersion" /> property must be
                set.
            </li>
            <li>
                When <fgp-code>true</fgp-code>, the plugin relies on the following locations in this exact order to find
                <fgp-code>node</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code>npx</fgp-code> executables:
                <ol>
                    <li>
                        The directory pointed by the <fgp-property-link name="nodeInstallDirectory" /> property, if set.
                    </li>
                    <li>The directory pointed by the <fgp-code>NODEJS_HOME</fgp-code> environment variable, if set.</li>
                    <li>Any directory in the <fgp-code>PATH</fgp-code> environment variable.</li>
                </ol>
                Other <fgp-code>node*</fgp-code> properties should not be used for clarity.
            </li>
        </ol>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../layout/code";
import fgpNodejsLink from "../link/nodejs-link";
import fgpProperty from "../layout/property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-distribution-provided-property", {
    components: { fgpCode, fgpNodejsLink, fgpProperty, fgpPropertyLink }
});
</script>
