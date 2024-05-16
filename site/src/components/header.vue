<template>
    <header>
        <nav class="navbar navbar-expand-sm justify-content-between text-center">
            <FgpImageLink
                href="https://github.com/siouan"
                class="navbar-item"
                :title="$t('siouan.organizationTooltip')"
                src="siouan-icon.svg"
                alt="Siouan logo"
                :width="32"
                :height="32"
            />

            <button
                class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#fgp-navbar"
                aria-controls="fgp-navbar"
                aria-expanded="false"
                aria-label="Toggle navbar"
                @click="toggleMenuVisible()"
            >
                <span class="navbar-toggler-icon" />
            </button>

            <div id="fgp-navbar" class="collapse navbar-collapse justify-content-center" :class="{ show: menuVisible }">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <FgpSiteLink
                            :path="$config.public.paths.overview"
                            class="nav-link"
                            @click="hideMenu()"
                        >{{ $t('menu.overview') }}</FgpSiteLink>
                    </li>
                    <li class="nav-item">
                        <FgpSiteLink
                            :path="$config.public.paths.gettingStarted"
                            class="nav-link"
                            @click="hideMenu()"
                        >{{ $t('menu.installation') }}</FgpSiteLink>
                    </li>
                    <li class="nav-item">
                        <FgpSiteLink
                            :path="$config.public.paths.configuration"
                            class="nav-link"
                            @click="hideMenu()"
                        >{{ $t('menu.configuration') }}</FgpSiteLink>
                    </li>
                    <li class="nav-item">
                        <FgpSiteLink
                            :path="$config.public.paths.tasks"
                            class="nav-link"
                            @click="hideMenu()"
                        >{{ $t('menu.tasks') }}</FgpSiteLink>
                    </li>
                    <li class="nav-item">
                        <FgpSiteLink
                            :path="$config.public.paths.faqs"
                            class="nav-link"
                            @click="hideMenu()"
                        >{{ $t('menu.faq') }}</FgpSiteLink>
                    </li>
                </ul>
            </div>

            <form v-if="$config.public.i18nEnabled">
                <label>
                    <select class="custom-select" @change="selectLang($event)">
                        <option value="en" selected>{{ $t('lang.english') }}</option>
                        <option value="fr">{{ $t('lang.french') }}</option>
                    </select>
                </label>
            </form>
            <FgpLink
                href="https://github.com/siouan/frontend-gradle-plugin"
                class="navbar-item"
                :title="$t('siouan.projectTooltip')"
            ><i class="fab fa-github fa-2x text-dark" /></FgpLink>
        </nav>
    </header>
</template>

<script setup lang="ts">
const emit = defineEmits(['lang-change']);
// Actually, builtin toggling feature of Bootstrap's navbar component is not working because Bootstrap JS file may
// conflict with Vue when modifying the DOM. That's why toggling feature is implemented again here.
// See https://getbootstrap.com/docs/5.3/getting-started/javascript/#usage-with-javascript-frameworks
const menuVisible = ref(false);

function selectLang(event: Event): void {
    if (event.target instanceof HTMLSelectElement) {
        emit('lang-change', event.target.value);
    }
}

function toggleMenuVisible() {
    menuVisible.value = !menuVisible.value;
}

function hideMenu() {
    menuVisible.value = false;
}
</script>

<style scoped lang="scss">
@import '@/assets/scss/variables';

.nav-link {
    font-family: $headings-font-family;
    color: $navbar-link-color;
}
</style>
