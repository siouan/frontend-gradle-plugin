<template>
    <FgpFaq>
        <template #question>
            How to configure the plugin so as it can download a
            <FgpNodejsLink /> distribution from a custom server?
        </template>
        <template #answer>
            Properties <FgpPropertyLink name="nodeDistributionUrlRoot" /> and
            <FgpPropertyLink name="nodeDistributionUrlPathPattern" /> exist specially for this use case.
        </template>
    </FgpFaq>
</template>
