<template>
    <fgp-property
        name="assembleScript"
        type="java.lang.String"
        :required="false"
        example="run assemble"
        :tasks="['assembleFrontend', 'publishFrontend']"
    >
        Script called to build frontend artifacts.
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "../layout/property";

export default Vue.component("fgp-assemble-script-property", {
    components: { fgpProperty }
});
</script>
