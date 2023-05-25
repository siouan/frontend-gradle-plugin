<template>
    <fgp-property name="nodeDistributionProvided" type="boolean" default-value="false" :tasks="['installNode']">
        <p>Whether the <fgp-nodejs-link /> distribution is already provided, and shall not be downloaded.</p>
        <ol>
            <li>
                When <fgp-code>false</fgp-code>, task <fgp-task-link name="installNode" /> downloads and installs a
                <fgp-nodejs-link /> distribution using properties <fgp-property-link name="nodeVersion" />,
                <fgp-property-link name="nodeDistributionUrlRoot" />,
                <fgp-property-link name="nodeDistributionUrlPathPattern" />.
            </li>
            <li>
                When <fgp-code>true</fgp-code>, the plugin relies on the following locations in this exact order to find
                <fgp-code>node</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code>pnpm</fgp-code>/<fgp-code>yarn</fgp-code> executables:
                <ol>
                    <li>
                        The directory pointed by the <fgp-property-link name="nodeInstallDirectory" /> property, if set.
                    </li>
                    <li>
                        The directory pointed by the <fgp-code>FGP_NODEJS_HOME</fgp-code> environment variable, if set.
                    </li>
                </ol>
                Other <fgp-code>node*</fgp-code> properties should not be used for clarity.
            </li>
        </ol>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpNodejsLink from "../link/nodejs-link";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-distribution-provided-property", {
    components: { fgpCode, fgpNodejsLink, fgpProperty, fgpPropertyLink }
});
</script>
