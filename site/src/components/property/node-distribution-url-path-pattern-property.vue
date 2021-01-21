<template>
    <fgp-property
        name="nodeDistributionUrlPathPattern"
        type="java.lang.String"
        default-value="vVERSION/node-vVERSION-ARCH.TYPE"
        :tasks="['installNode']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending its value to the
            value of the <fgp-property-link name="nodeDistributionUrlRoot" /> property. This property may be set with a
            fixed trailing path part (e.g. <fgp-code>dist/node-v14.17.0-win-x64.zip</fgp-code>), or take advantage of
            the automatic distribution resolution in the plugin, using specific tokens in the pattern such as
            <fgp-code>VERSION</fgp-code>, <fgp-code>ARCH</fgp-code>, <fgp-code>TYPE</fgp-code>. Then, the exact URL used
            by default to download the distribution is
            <fgp-code>https://nodejs.org/dist/vVERSION/node-vVERSION-ARCH.TYPE</fgp-code>.
        </p>
        <div class="card mt-3">
            <div class="card-body">
                <h5 class="card-title">About URL pattern tokens</h5>
                <p class="card-text">
                    The <fgp-property-link name="nodeDistributionUrlRoot" /> property and this property offer a
                    convenient way to download distributions from a custom website - e.g. a website mirroring the
                    official <fgp-nodejs-link /> website, and still take advantage of the automatic resolution of the
                    exact URL, based on the current platform's architecture. The property may contain the following
                    tokens:
                </p>
                <ul class="card-text">
                    <li>
                        <fgp-code>VERSION</fgp-code>: automatically replaced with the version number set in the
                        <fgp-property-link name="nodeVersion" /> property.
                    </li>
                    <li>
                        <fgp-code>ARCH</fgp-code>: automatically replaced with the architecture ID matching the current
                        platform. For example, if the current O/S is MacOS 64 bits, the plugin replaces the token with
                        <fgp-code>darwin-x64</fgp-code>.
                    </li>
                    <li>
                        <fgp-code>TYPE</fgp-code>: automatically replaced with the type of the distribution file
                        expected for the current platform. For example, if the current O/S is Windows, the plugin
                        replaces the token with <fgp-code>zip</fgp-code>.
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

export default Vue.component("fgp-node-distribution-url-path-pattern-property", {
    components: { fgpCode, fgpProperty, fgpPropertyLink }
});
</script>
