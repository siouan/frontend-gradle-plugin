<template>
    <FgpGradleDocsLink
        :path="gradleDocsLinkPath"
        :title="gradleDocsLinkTitle"
        class="badge me-1"
        :class="chipClass"
        hover-style-disabled
        >{{ type }}</FgpGradleDocsLink
    >
</template>

<script setup lang="ts">
import { computed } from 'vue';

interface Props {
    readonly type: TaskPropertyTypeType;
}

const props = defineProps<Props>();

const gradleDocsLinkPath = computed(() => {
    switch (props.type) {
        case TaskPropertyType.REGULAR_FILE:
            return '/current/javadoc/org/gradle/api/file/RegularFileProperty.html';
        case TaskPropertyType.EXECUTABLE_TYPE:
        case TaskPropertyType.FILE:
        case TaskPropertyType.STRING:
            return '/current/javadoc/org/gradle/api/provider/Property.html';
        default:
            return null;
    }
});
const gradleDocsLinkTitle = computed(() => {
    switch (props.type) {
        case TaskPropertyType.EXECUTABLE_TYPE:
            return 'Provider of org.siouan.frontendgradleplugin.domain.ExecutableType instance (task is out-of-date if the value changes)';
        case TaskPropertyType.FILE:
            return 'Provider of java.io.File instance (task is out-of-date if the path changes)';
        case TaskPropertyType.REGULAR_FILE:
            return 'Provider of org.gradle.api.file.RegularFile instance (task is out-of-date if the content changes)';
        case TaskPropertyType.STRING:
            return 'Provider of java.lang.String instance (task is out-of-date if the value changes)';
        default:
            return null;
    }
});
const chipClass = computed(() => {
    switch (props.type) {
        case TaskPropertyType.EXECUTABLE_TYPE:
            return 'text-bg-dark';
        case TaskPropertyType.FILE:
        case TaskPropertyType.STRING:
            return 'text-bg-primary';
        case TaskPropertyType.REGULAR_FILE:
            return 'text-bg-danger';
        default:
            return null;
    }
});
</script>
