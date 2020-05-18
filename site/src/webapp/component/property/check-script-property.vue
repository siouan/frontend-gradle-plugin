<template>
    <fgp-property
        name="checkScript"
        type="java.lang.String"
        :required="false"
        example="run check"
        :tasks="['checkFrontend']"
    >
        Script called to check the frontend application.
        <fgp-info>
            Take a look at this <fgp-site-link path="#script-settings">guide</fgp-site-link> about script settings.
        </fgp-info>
    </fgp-property>
</template>

<script>
import Vue from "vue";
import fgpProperty from "../layout/property";

export default Vue.component("fgp-check-script-property", {
    components: { fgpProperty }
});
</script>
