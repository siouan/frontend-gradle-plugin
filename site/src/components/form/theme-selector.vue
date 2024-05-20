<template>
    <div class="btn-group btn-group-sm" role="group" aria-label="Color scheme selector">
        <input
            type="radio"
            class="btn-check"
            name="themeSelector"
            id="light-theme"
            autocomplete="off"
            value="light"
            :checked="lightThemePrefered"
            @change="setPreferedThemeId($event)" />
      <label class="btn" :class="btnClass" for="light-theme"><i class="fa-regular fa-lightbulb"></i></label>

      <input
          type="radio"
          class="btn-check"
          name="themeSelector"
          id="system-theme"
          autocomplete="off"
          value="system"
          :checked="systemThemePrefered"
          @change="setPreferedThemeId($event)" />
      <label class="btn" :class="btnClass" for="system-theme"><i class="fa-solid fa-circle-half-stroke"></i></label>

      <input
          type="radio"
          class="btn-check"
          name="themeSelector"
          id="dark-theme"
          autocomplete="off"
          value="dark"
          :checked="darkThemePrefered"
          @change="setPreferedThemeId($event)" />
      <label class="btn" :class="btnClass" for="dark-theme"><i class="fa-solid fa-moon"></i></label>
    </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const mainStore = useMainStore();

const lightThemePrefered = computed(() => mainStore.preferedThemeId === PreferedThemeId.LIGHT);
const systemThemePrefered = computed(() => mainStore.preferedThemeId === PreferedThemeId.SYSTEM);
const darkThemePrefered = computed(() => mainStore.preferedThemeId === PreferedThemeId.DARK);
const btnClass = computed(() => mainStore.themeId === ThemeId.LIGHT ? 'btn-light' : 'btn-dark');

function setPreferedThemeId(event: any): void {
    mainStore.setPreferedThemeId(event.target.value);
}
</script>

<style scoped>
.form-switch .form-check-input {
    --bs-form-switch-bg: url('@/assets/image/light-dark.svg');
    //border-color: var(--bs-gray-400);
}

.form-switch .form-check-input:checked {
    --bs-form-switch-bg: url('@/assets/image/light-dark.svg');
    background-color: var(--bs-gray-500);
    border-color: unset;
}
</style>
