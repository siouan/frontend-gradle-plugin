<template>
    <FgpProperty
        name="retryIntervalMultiplier"
        type="java.lang.Double"
        default-value="2.0"
        :task-names="['installNode']"
    >
        <p>Multiplier used to compute exponential intervals between download attempts.</p>
    </FgpProperty>
</template>
