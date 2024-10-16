<template>
    <ul class="rounded bg-dark text-center py-3">
        <li v-for="(reference, index) in references" :key="reference.title" class="d-inline-flex mx-3"
        ><FgpImageLink
            v-if="reference.logo"
            :href="reference.url"
            :title="`${reference.title} - ${reference.description}`"
            :src="reference.logo"
            :height="reference.logoHeight"
        /><FgpLink v-else :href="reference.url">{{ reference.title }}</FgpLink
        ><!-- <FgpGithubLink :path="reference.repositoryPath"><i class="fab fa-github text-body" /></FgpGithubLink
        > <FgpImage
            :src="`https://img.shields.io/github/stars/${reference.repositoryPath}?style=badge&cacheSeconds=86400`"
        /> - {{ reference.description }}--></li>
    </ul>
</template>

<script setup lang="ts">
const references = [{
    description: 'Data Exchange Layer',
    logo: 'x-road.png',
    logoHeight: 64,
    repositoryPath: 'nordic-institute/X-Road',
    title: 'X-Road',
    url: 'https://x-road.global/'
}, {
    description: 'Kafka GUI for Apache Kafka',
    logo: 'akhq.svg',
    logoHeight: 64,
    repositoryPath: 'tchiotludo/akhq',
    title: 'AKHQ',
    url: 'https://akhq.io/'
}, {
    description: 'Event-driven declarative orchestration platform',
    logo: 'kestra.svg',
    logoHeight: 48,
    repositoryPath: 'kestra-io/kestra',
    title: 'Kestra',
    url: 'https://kestra.io/'
}, {
    description: 'Open Metadata and Governance',
    logo: 'egeria.png',
    logoHeight: 64,
    repositoryPath: 'odpi/egeria',
    title: 'Egeria',
    url: 'https://egeria-project.org/'
}, {
    description: 'Quick and easy generation of server packs of your favourite Minecraft modpacks',
    logo: 'serverpackcreator.png',
    logoHeight: 32,
    repositoryPath: 'Griefed/ServerPackCreator',
    title: 'ServerPackCreator',
    url: 'https://serverpackcreator.de/'
}];
</script>
