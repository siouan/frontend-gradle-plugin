<template>
    <FgpProperty
        name="yarnDistributionUrlRoot"
        type="java.lang.String"
        default-value="https://github.com/yarnpkg/yarn/releases/download/"
        :task-names="['installYarn']"
    >
        <p>
            This property is used to build the exact URL to download the distribution, by appending the value of the
            <FgpCode>yarnDistributionUrlPathPattern</FgpCode> property to its value. By default, the plugin attempts to
            download the distribution from the <FgpYarnLink /> website.
        </p>
    </FgpProperty>
</template>
