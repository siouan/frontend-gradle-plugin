<template>
    <fgp-property
        name="publishScript"
        type="java.lang.String"
        :required="false"
        example="run publish"
        :tasks="['publishFrontend']"
    >
        <p>Script called to publish the frontend artifacts.</p>
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpInfo from "../info";
import fgpProperty from "./property";

export default Vue.component("fgp-publish-script-property", {
    components: { fgpInfo, fgpProperty }
});
</script>
