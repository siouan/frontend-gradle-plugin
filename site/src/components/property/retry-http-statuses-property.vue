<template>
    <FgpProperty
        name="retryHttpStatuses"
        type="java.util.Set<java.lang.Integer>"
        :required="true"
        default-value="[408, 429, 500, 502, 503, 504]"
        :task-names="['installNode']"
    >
        <p>
            This property identifies HTTP error responses that may trigger a new download attempt based on their status
            code.
        </p>
    </FgpProperty>
</template>
