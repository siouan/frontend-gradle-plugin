<template>
    <fgp-faq>
        <template v-slot:question>
            How to use <fgp-code>node</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code>npx</fgp-code> executables apart
            from Gradle when the <fgp-nodejs-link /> distribution is downloaded by the plugin?
        </template>
        <template v-slot:answer>
            <ul>
                <li>
                    Create a <fgp-code>NODEJS_HOME</fgp-code> environment variable containing the real path set in the
                    <fgp-property-link name="nodeInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the <fgp-code>PATH</fgp-code> environment variable:
                    <ul>
                        <li>On Unix-like O/S, add the <fgp-code>$NODEJS_HOME/bin</fgp-code> path.</li>
                        <li>On Windows O/S, add <fgp-code>%NODEJS_HOME%</fgp-code> path.</li>
                    </ul>
                </li>
            </ul>
            <fgp-info>
                Applies only when the <fgp-property-link name="nodeDistributionProvided" /> property is
                <fgp-code>false</fgp-code>.
            </fgp-info>
        </template>
    </fgp-faq>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpFaq from "./faq";
import fgpInfo from "../info";
import fgpNodejsLink from "../link/nodejs-link";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-npm-npx-direct-use-faq", {
    components: {
        fgpCode,
        fgpFaq,
        fgpInfo,
        fgpNodejsLink,
        fgpPropertyLink
    }
});
</script>
