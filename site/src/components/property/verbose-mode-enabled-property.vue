<template>
    <FgpProperty name="verboseModeEnabled" type="boolean" default-value="false" :task-names="[]">
        <p>
            Whether the plugin shall log additional messages whatever Gradle's logging level is. Technically speaking,
            messages logged by the plugin with the
            <FgpCode>INFO</FgpCode> level are made visible. This property allows to track the plugin execution without
            activating Gradle's <FgpCode>INFO</FgpCode> or <FgpCode>DEBUG</FgpCode> levels, that may be too much verbose
            on a global point of view.
        </p>
    </FgpProperty>
</template>
