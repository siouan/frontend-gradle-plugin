<template>
    <span :id="name" class="text-info">#</span>
</template>

<script>
import Vue from 'vue';

export default Vue.component('fgp-task-link-anchor', {
    props: {
        name: {
            type: String,
            required: true
        }
    }
});
</script>
