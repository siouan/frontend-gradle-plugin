<template>
    <ul class="navbar-nav">
        <li class="nav-item">
            <FgpSiteLink
                :path="$config.public.paths.overview"
                class="nav-link"
                @click="$emit('itemClick')"
            >{{ $t('menu.overview') }}</FgpSiteLink>
        </li>
        <li class="nav-item">
            <FgpSiteLink
                :path="$config.public.paths.gettingStarted"
                class="nav-link"
                @click="$emit('itemClick')"
            >{{ $t('menu.installation') }}</FgpSiteLink>
        </li>
        <li class="nav-item">
            <FgpSiteLink
                :path="$config.public.paths.configuration"
                class="nav-link"
                @click="$emit('itemClick')"
            >{{ $t('menu.configuration') }}</FgpSiteLink>
        </li>
        <li class="nav-item">
            <FgpSiteLink
                :path="$config.public.paths.tasks"
                class="nav-link"
                @click="$emit('itemClick')"
            >{{ $t('menu.tasks') }}</FgpSiteLink>
        </li>
        <li class="nav-item">
            <FgpSiteLink
                :path="$config.public.paths.faqs"
                class="nav-link"
                @click="$emit('itemClick')"
            >{{ $t('menu.faq') }}</FgpSiteLink>
        </li>
    </ul>
</template>

<script setup lang="ts">
interface Emits {
    (e: 'itemClick'): void
}

const emit = defineEmits<Emits>();
</script>
