<template>
    <fgp-faq>
        <template v-slot:question>
            What should I do when error
            <fgp-code>UnsupportedPlatformException</fgp-code>
            occurs?
        </template>
        <template v-slot:answer>
            By default, the plugin uses an automatic resolution process to "guess" the most relevant
            <fgp-nodejs-link />
            distribution applicable for your O/S and CPU architecture. Technically speaking, the plugin relies on
            <fgp-java-system-properties-link>JVM system properties</fgp-java-system-properties-link> named
            <fgp-code>os.name</fgp-code> and <fgp-code>os.arch</fgp-code>. If the plugin gets values it does not expect
            from these system properties, the following error shall occur during the build:

            <fgp-block-quote>
                Execution failed for task ':installNode'.
                > org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException: This platform is not
                supported yet: Platform {jvmArch=&lt;os.name&gt;, osName=&lt;os.arch&gt;, environment=...}
            </fgp-block-quote>

            In this case, please open an issue in the project's <fgp-repo-link>issue tracker</fgp-repo-link>, and
            provide the entire error message above. As a workaround, consider using property
            <fgp-property-link name="nodeDistributionUrlPathPattern" /> and specify a hard-coded path to download the
            relevant distribution from the <fgp-nodejs-link /> distribution server.
        </template>
    </fgp-faq>
</template>

<script>
import Vue from "vue";
import fgpBlockQuote from "../block-quote";
import fgpCode from "../code";
import fgpFaq from "./faq";
import fgpJavaSystemPropertiesLink from "../link/java-system-properties-link";
import fgpNodejsLink from "../link/nodejs-link";

export default Vue.component("fgp-unsupported-platform-exception-faq", {
    components: {
        fgpBlockQuote,
        fgpCode,
        fgpFaq,
        fgpNodejsLink,
        fgpJavaSystemPropertiesLink
    }
});
</script>
