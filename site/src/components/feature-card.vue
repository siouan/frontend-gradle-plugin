<template>
    <article class="container-fluid border rounded my-4">
        <div class="row align-items-center bg-light">
            <div class="d-none d-md-inline-block col-sm-2 text-center">
                <i class="fa-4x mx-auto" :class="iconClass"></i>
            </div>
            <div class="col px-4 bg-white">
                <h5 class="mt-3">{{ title }}</h5>
                <p>
                    <slot></slot>
                </p>
            </div>
        </div>
    </article>
</template>

<script>
import Vue from 'vue';

export default Vue.component('fgp-feature-card', {
    props: {
        title: {
            type: String,
            required: true
        },
        iconClass: {
            type: String,
            required: true
        }
    }
});
</script>
