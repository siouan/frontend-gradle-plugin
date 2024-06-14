<template>
    <FgpProperty
        name="retryMaxIntervalMs"
        type="java.lang.Integer"
        default-value="30000"
        :task-names="['installNode']"
    >
        <p>
            Maximum interval (ms) between each download attempts (see property
            <FgpPropertyLink name="maxDownloadAttempts" />).
        </p>
    </FgpProperty>
</template>
