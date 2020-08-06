<template>
    <article class="mb-3 border-bottom">
        <header>
            <h4>
                <slot name="question" /> <fgp-site-link path="#app" class="small text-info">&uparrow;</fgp-site-link>
            </h4>
        </header>
        <section class="px-3">
            <slot name="answer" />
        </section>
    </article>
</template>

<script>
import Vue from "vue";
import fgpSiteLink from "../link/site-link";

export default Vue.component("fgp-faq", {
    components: { fgpSiteLink }
});
</script>
