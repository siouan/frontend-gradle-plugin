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
                <ol class="mb-3">
                    <li>
                        The directory pointed by the <fgp-property-link name="nodeInstallDirectory" /> property, if set.
                    </li>
                    <li>
                        The directory pointed by the <fgp-code>FGP_NODEJS_HOME</fgp-code> environment variable, if set.
                    </li>
                </ol>
                <fgp-warning>CAUTION: globally installed distribution is modified when using the plugin and
                <fgp-corepack-link />. Executables <fgp-code>npm</fgp-code>, <fgp-code>pnpm</fgp-code>,
                <fgp-code>yarn</fgp-code> that may already exists within <fgp-nodejs-link /> install directory are
                overwritten when task <fgp-task-link name="installPackageManager" /> is run.</fgp-warning>
                Other <fgp-code>node*</fgp-code> properties should not be used for clarity.
            </li>
        </ol>
    </fgp-property>
</template>

<script>
import Vue from 'vue';
import fgpCode from '@/components/code';
import fgpNodejsLink from '@/components/link/nodejs-link';
import fgpProperty from '@/components/property/property';
import fgpPropertyLink from '@/components/link/property-link';

export default Vue.component('fgp-node-distribution-provided-property', {
    components: { fgpCode, fgpNodejsLink, fgpProperty, fgpPropertyLink }
});
</script>
