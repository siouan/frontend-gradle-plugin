<template>
    <fgp-faq>
        <template v-slot:question>
            How to use <fgp-code>yarn</fgp-code> executable apart from Gradle when the <fgp-yarn-link />
            distribution is downloaded by the plugin?
        </template>
        <template v-slot:answer>
            <ul>
                <li>
                    Create a <fgp-code>YARN_HOME</fgp-code> environment variable containing the real path set in the
                    <fgp-property-link name="yarnInstallDirectory" /> property.
                </li>
                <li>
                    Add the directory containing executables to the <fgp-code>PATH</fgp-code> environment variable:
                    <ul>
                        <li>On Unix-like O/S, add the <fgp-code>$YARN_HOME/bin</fgp-code> path.</li>
                        <li>On Windows O/S, add <fgp-code>%YARN_HOME%\bin</fgp-code> path.</li>
                    </ul>
                </li>
            </ul>
            <fgp-info>
                Applies only when the <fgp-property-link name="yarnDistributionProvided" /> property is
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
import fgpPropertyLink from "../link/property-link";
import fgpYarnLink from "../link/yarn-link";

export default Vue.component("fgp-yarn-direct-use-faq", {
    components: {
        fgpCode,
        fgpFaq,
        fgpInfo,
        fgpPropertyLink,
        fgpYarnLink
    }
});
</script>
