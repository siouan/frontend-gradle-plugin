<template>
    <fgp-gradle-docs-link
        :path="gradleDocsLinkPath"
        class="fgp-badge"
        :class="badgeClass"
        :title="gradleDocsLinkTitle"
        hover-style-disabled
    >{{ type }}</fgp-gradle-docs-link>
</template>

<script>
import Vue from 'vue';
import fgpGradleDocsLink from '@/components/link/gradle-docs-link';

const TYPE_EXECUTABLE_TYPE = 'ET';
const TYPE_FILE = 'F';
const TYPE_STRING = 'S';
const TYPE_REGULAR_FILE = 'RF';

export default Vue.component('fgp-task-property-type', {
    components: { fgpGradleDocsLink },
    props: {
        type: {
            type: String,
            required: true,
            validator: type => [TYPE_EXECUTABLE_TYPE, TYPE_FILE, TYPE_REGULAR_FILE, TYPE_STRING].includes(type)
        }
    },
    computed: {
        gradleDocsLinkPath() {
            switch (this.type) {
            case TYPE_REGULAR_FILE:
                return '/current/javadoc/org/gradle/api/file/RegularFileProperty.html';
            case TYPE_EXECUTABLE_TYPE:
            case TYPE_FILE:
            case TYPE_STRING:
                return '/current/javadoc/org/gradle/api/provider/Property.html';
            default:
                return null;
            }
        },
        gradleDocsLinkTitle() {
            switch (this.type) {
            case TYPE_EXECUTABLE_TYPE:
                return 'Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance';
            case TYPE_FILE:
                return 'Provider of java.io.File instance';
            case TYPE_REGULAR_FILE:
                return 'Provider of org.gradle.api.file.RegularFile instance';
            case TYPE_STRING:
                return 'Provider of java.lang.String instance';
            default:
                return null;
            }
        },
        badgeClass() {
            switch (this.type) {
            case TYPE_EXECUTABLE_TYPE:
                return 'fgp-plugin-type';
            case TYPE_FILE:
            case TYPE_STRING:
                return 'fgp-java-type';
            case TYPE_REGULAR_FILE:
                return 'fgp-gradle-type';
            default:
                return null;
            }
        }
    }
});
</script>

<style scoped>
.fgp-badge {
    font-size: small;
    padding: 0 0.3rem;
    margin-right: 0.2rem;
    border-radius: 20%;
}

.fgp-gradle-type {
    color: white;
    background-color: red;
}

.fgp-java-type {
    color: white;
    background-color: royalblue;
}

.fgp-plugin-type {
    color: white;
    background-color: black;
}
</style>
