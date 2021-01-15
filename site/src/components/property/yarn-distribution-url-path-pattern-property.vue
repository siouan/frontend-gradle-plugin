<template>
    <fgp-property
        name="yarnDistributionUrlPathPattern"
        type="java.lang.String"
        default-value="vVERSION/yarn-vVERSION.tar.gz"
        :tasks="['installYarn']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending its value to the
            value of the <fgp-property-link name="yarnDistributionUrlRoot" /> property. This property may be set with a
            fixed trailing path part (e.g. <fgp-code>dist/yarn-v1.22.10.tar.gz</fgp-code>), or take advantage of the
            automatic distribution resolution in the plugin, using specific token in the pattern such as
            <fgp-code>VERSION</fgp-code> (see the <fgp-task-link name="installYarn" /> task).
        </p>
        <p>
            Then, the exact URL used by default to download the distribution is
            <fgp-code>https://github.com/yarnpkg/yarn/releases/download/vVERSION/yarn-vVERSION.tar.gz</fgp-code>.
        </p>
        <div class="card my-3">
            <div class="card-body">
                <h5 class="card-title">About URL pattern token</h5>
                <p class="card-text">
                    The <fgp-property-link name="yarnDistributionUrlRoot" /> property and this property offer a
                    convenient way to download distributions from a custom website - e.g. a website mirroring the
                    official <fgp-yarn-link /> website, and still take advantage of the automatic resolution of the
                    exact URL. The property may contain the following token:
                </p>
                <ul class="card-text">
                    <li>
                        <fgp-code>VERSION</fgp-code>: automatically replaced with the version number set in the
                        <fgp-property-link name="yarnVersion" /> property.
                    </li>
                </ul>
            </div>
        </div>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpCode from "../code";
import fgpProperty from "./property";
import fgpPropertyLink from "../link/property-link";

export default Vue.component("fgp-yarn-distribution-url-path-pattern-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
