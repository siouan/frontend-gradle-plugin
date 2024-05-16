<template>
    <FgpProperty
        name="retryInitialIntervalMs"
        type="java.lang.Integer"
        :required="true"
        default-value="1000"
        :task-names="['installNode']"
    >
        <p>
            Interval (ms) between the first and the second download attempt, if permitted with the
            <FgpPropertyLink name="maxDownloadAttempts" /> property.
        </p>
    </FgpProperty>
</template>
