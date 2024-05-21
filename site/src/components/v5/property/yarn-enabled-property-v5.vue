<template>
    <FgpProperty
        name="yarnEnabled"
        type="boolean"
        default-value="false"
        :task-names="[
            'installYarn',
            'installFrontend',
            'cleanFrontend',
            'assembleFrontend',
            'checkFrontend',
            'publishFrontend',
        ]"
    >
        <p>
            Whether <FgpCode>yarn</FgpCode> executable shall be used instead of <FgpCode>npm</FgpCode> executable when
            executing frontend tasks. Consequently, a <FgpYarnLink /> distribution is required, and the plugin will
            locate it depending on the value of the <FgpPropertyLink name="yarnDistributionProvided" /> property.
        </p>
    </FgpProperty>
</template>
