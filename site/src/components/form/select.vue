<template>
    <select
        class="form-select"
        :class="selectClass"
        autocomplete="off"
        @change="$emit('change', $event)">
        <option
            v-for="internalOption in internalOptions"
            :key="internalOption.value"
            :value="internalOption.value"
            :selected="internalOption.selected">{{ internalOption.label }}</option>
    </select>
</template>

<script setup lang="ts">
interface Emits {
    (e: 'change', value: Event): void
}

interface Option {
    label: string;
    value?: any;
}

interface Props {
    options: Option[];
    size?: ComponentSizeType;
    values: any[];
}

const emit = defineEmits<Emits>();
const props = defineProps<Props>();

const selectClass = computed(() => {
    switch (props.size) {
    case ComponentSize.SMALL:
        return 'form-select-sm';
    case ComponentSize.LARGE:
        return 'form-select-lg';
    default:
        return null;
    }
});
const internalOptions = computed(() => {
    return props.options.map(option => ({
        label: option.label,
        selected: props.values.includes(option.value),
        value: option.value
    }));
});
</script>
