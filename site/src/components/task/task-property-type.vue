<template>
        <fgp-gradle-docs-link
            :path="gradleDocsLinkPath"
            :title="gradleDocsLinkTitle"
            class="badge mr-1"
            :class="chipClass"
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
                return 'Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)';
            case TYPE_FILE:
                return 'Provider of java.io.File instance (task is out-of-date if the path changes)';
            case TYPE_REGULAR_FILE:
                return 'Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)';
            case TYPE_STRING:
                return 'Provider of java.lang.String instance (task is out-of-date if the value changes)';
            default:
                return null;
            }
        },
        chipClass() {
            switch (this.type) {
            case TYPE_EXECUTABLE_TYPE:
                return 'badge-dark';
            case TYPE_FILE:
            case TYPE_STRING:
                return 'badge-primary';
            case TYPE_REGULAR_FILE:
                return 'badge-danger';
            default:
                return null;
            }
        }
    }
});
</script>
