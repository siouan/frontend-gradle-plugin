<template>
    <header>
        <nav class="navbar navbar-expand-md text-center debug">
            <div class="d-flex fgp-flex-1">
                <FgpImageLink
                    href="https://github.com/siouan"
                    :title="$t('siouan.organizationTooltip')"
                    src="siouan-icon.svg"
                    alt="Siouan logo"
                    :width="32"
                    :height="32"
                    class="d-none d-sm-inline me-1"
                />
                <FgpLink
                    href="https://github.com/siouan/frontend-gradle-plugin"
                    :title="$t('siouan.projectTooltip')"
                ><i class="fab fa-github fa-2x text-body" /></FgpLink>
            </div>
            <div class="d-flex">
                <button
                    class="navbar-toggler mx-auto"
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

                <FgpMenubar class="d-none d-md-flex justify-content-center" />
            </div>
            <form class="d-flex fgp-flex-1 justify-content-end">
                <label v-if="$config.public.i18nEnabled">
                    <select class="custom-select" @change="selectLang($event)">
                        <option value="en" selected>{{ $t('lang.english') }}</option>
                        <option value="fr">{{ $t('lang.french') }}</option>
                    </select>
                </label>
                <FgpReleaseSelector class="w-auto me-1" />
                <FgpThemeSelector />
            </form>
        </nav>
        <div id="fgp-navbar" class="collapse navbar-collapse text-center border-bottom mb-3" :class="{ show: menuVisible }">
            <FgpMenubar @item-click="hideMenu()" />
        </div>
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
@use '@/assets/scss/variables';

.nav-link {
    font-family: variables.$headings-font-family;
    color: var(--bs-tertiary-color);
}

.fgp-flex-1 {
    flex: 1;
}
</style>
