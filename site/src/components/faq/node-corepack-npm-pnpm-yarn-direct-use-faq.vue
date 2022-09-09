<template>
    <fgp-faq>
        <template v-slot:question>
            How to use <fgp-code>node</fgp-code>/<fgp-code>corepack</fgp-code>/<fgp-code>npm</fgp-code>/<fgp-code
            >pnpm</fgp-code>/<fgp-code>yarn</fgp-code> executables apart from Gradle when the <fgp-nodejs-link />
            distribution is downloaded by the plugin?
        </template>
        <template v-slot:answer>
            <ul>
                <li>
                    Create a <fgp-code>NODEJS_HOME</fgp-code> environment variable (or a name of your choice) containing
                    the real path set in the <fgp-property-link name="nodeInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the <fgp-code>PATH</fgp-code> environment variable:
                    <ul>
                        <li>On Unix-like O/S, add the <fgp-code>$NODEJS_HOME/bin</fgp-code> path.</li>
                        <li>On Windows O/S, add <fgp-code>%NODEJS_HOME%</fgp-code> path.</li>
                    </ul>
                </li>
            </ul>
        </template>
    </fgp-faq>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpFaq from "./faq";
import fgpNodejsLink from "../link/nodejs-link";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-node-corepack-npm-pnpm-yarn-direct-use-faq", {
    components: {
        fgpCode,
        fgpFaq,
        fgpNodejsLink,
        fgpPropertyLink
    }
});
</script>
